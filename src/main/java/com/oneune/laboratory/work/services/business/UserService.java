package com.oneune.laboratory.work.services.business;

import com.oneune.laboratory.work.repositories.UserRepository;
import com.oneune.laboratory.work.services.contracts.CRUDable;
import com.oneune.laboratory.work.store.dtos.UserDto;
import com.oneune.laboratory.work.store.entities.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.List;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService implements CRUDable<UserDto> {
    static Type USER_LIST_DTOS_REFERENCE = new ParameterizedTypeReference<List<UserDto>>(){}.getType();

    UserRepository userRepository;
    ModelMapper modelMapper;

    @Override
    @Transactional
    public UserDto post(UserDto userDto) {
        UserEntity userEntity = this.modelMapper.map(userDto, UserEntity.class);
        UserEntity createdUserEntity = this.userRepository.saveAndFlush(userEntity);// тут понятно, что сохранять и мб флашить контекст не обязательно (это сделает хибер), но для лучшей читаемости предпочитаю явно это делать
        UserDto createdUserDto = this.modelMapper.map(createdUserEntity, UserDto.class);
        return createdUserDto;
    }

    @Override
    public UserDto getById(Long userId) {
        UserEntity foundUserEntity = this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id %s not found...".formatted(userId)));
        UserDto foundUserDto = this.modelMapper.map(foundUserEntity, UserDto.class);
        return foundUserDto;
    }

    @Override
    public List<UserDto> paginate(int page, int size) {
        Page<UserEntity> userEntities = this.userRepository.findAll(PageRequest.of(page, size));
        List<UserDto> userDtos = this.modelMapper.map(userEntities.get().toList(), USER_LIST_DTOS_REFERENCE);
        return userDtos;
    }

    @Override
    @Transactional
    public UserDto put(Long userId, UserDto userDto) {
        UserEntity userEntity = this.modelMapper.map(userDto, UserEntity.class);
        UserEntity updatedUserEntity = this.userRepository.saveAndFlush(userEntity);
        UserDto updatedUserDto = this.modelMapper.map(updatedUserEntity, UserDto.class);
        return updatedUserDto;
    }

    @Override
    @Transactional
    public UserDto deleteById(Long userId) {
        UserEntity foundUserEntity = this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id %s not found...".formatted(userId)));
        UserDto foundUserDto = this.modelMapper.map(foundUserEntity, UserDto.class);
        this.userRepository.deleteById(userId);
        this.userRepository.flush();
        return foundUserDto;
    }
}
