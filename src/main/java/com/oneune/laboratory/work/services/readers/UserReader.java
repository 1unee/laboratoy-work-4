package com.oneune.laboratory.work.services.readers;

import com.oneune.laboratory.work.repositories.UserRepository;
import com.oneune.laboratory.work.store.dtos.UserDto;
import com.oneune.laboratory.work.store.entities.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserReader {

    UserRepository userRepository;
    ModelMapper modelMapper;

    public UserDto findUserById(Long userId) {
        UserEntity foundUserEntity = this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id %s not found...".formatted(userId)));
        return this.modelMapper.map(foundUserEntity, UserDto.class);
    }

}
