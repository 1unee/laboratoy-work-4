package com.oneune.laboratory.work.services.business;

import com.oneune.laboratory.work.repositories.UserRepository;
import com.oneune.laboratory.work.services.contracts.CRUDable;
import com.oneune.laboratory.work.services.readers.RoleReader;
import com.oneune.laboratory.work.services.readers.UserReader;
import com.oneune.laboratory.work.store.dtos.UserDto;
import com.oneune.laboratory.work.store.entities.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.List;

import static com.oneune.laboratory.work.store.enums.RoleEnum.ADMIN;
import static com.oneune.laboratory.work.store.enums.RoleEnum.USER;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService implements CRUDable<UserDto> {

    private final static Type USER_LIST_DTOS_REFERENCE = new ParameterizedTypeReference<List<UserDto>>(){}.getType();

    UserRepository userRepository;
    UserReader userReader;
    ModelMapper modelMapper;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    RoleReader roleReader;

    @Override
    @Transactional
    public UserDto post(UserDto userDto) {
        UserEntity userEntity = UserEntity.create(
                userDto.getUsername(),
                this.bCryptPasswordEncoder.encode(userDto.getPassword()),
                this.roleReader.findByNames(USER)
        );
        this.userRepository.saveAndFlush(userEntity);  // тут понятно, что сохранять и мб флашить контекст не обязательно (это сделает хибер), но для лучшей читаемости предпочитаю явно это делать
        return this.modelMapper.map(userEntity, UserDto.class);
    }

    @Transactional
    public UserDto post(UserDto userDto, boolean addAdminRole) {
        UserEntity userEntity = UserEntity.create(
                userDto.getUsername(),
                this.bCryptPasswordEncoder.encode(userDto.getPassword()),
                addAdminRole ? this.roleReader.findByNames(USER, ADMIN) : this.roleReader.findByNames(USER)
        );
        this.userRepository.saveAndFlush(userEntity);
        return this.modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getById(Long userId) {
        return this.userReader.findUserById(userId);
    }

    @Override
    public List<UserDto> search(int page, int size) {
        Page<UserEntity> userEntities = this.userRepository.findAll(PageRequest.of(page, size));
        return this.modelMapper.map(userEntities.getContent(), USER_LIST_DTOS_REFERENCE);
    }

    @Override
    @Transactional
    public UserDto put(Long userId, UserDto userDto) {
        UserEntity userEntity = this.modelMapper.map(userDto, UserEntity.class);
        UserEntity updatedUserEntity = this.userRepository.saveAndFlush(userEntity);
        return this.modelMapper.map(updatedUserEntity, UserDto.class);
    }

    @Override
    @Transactional
    public UserDto deleteById(Long userId) {
        UserDto foundUserDto = this.userReader.findUserById(userId);
        this.userRepository.deleteById(userId);
        this.userRepository.flush();
        return foundUserDto;
    }
}
