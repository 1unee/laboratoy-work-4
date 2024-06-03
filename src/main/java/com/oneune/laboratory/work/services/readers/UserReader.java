package com.oneune.laboratory.work.services.readers;

import com.oneune.laboratory.work.repositories.UserRepository;
import com.oneune.laboratory.work.services.utils.RoleUtil;
import com.oneune.laboratory.work.store.dtos.UserDto;
import com.oneune.laboratory.work.store.entities.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserReader implements UserDetailsService {

    UserRepository userRepository;
    ModelMapper modelMapper;

    public UserDto findUserById(Long userId) {
        UserEntity foundUserEntity = this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id %s not found...".formatted(userId)));
        return this.modelMapper.map(foundUserEntity, UserDto.class);
    }

    private UserEntity findUserByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User %s not found...".formatted(username)));
    }

    public UserDto findUserDtoByUsername(String username) {
        UserEntity userEntity = this.findUserByUsername(username);
        return this.modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = this.findUserByUsername(username);
        return new User(
                user.getUsername(), user.getPassword(), RoleUtil.getSimpleGrantedAuthoritiesByEntity(user.getRoles())
        );
    }
}
