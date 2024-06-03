package com.oneune.laboratory.work.services.readers;

import com.oneune.laboratory.work.repositories.RoleRepository;
import com.oneune.laboratory.work.store.entities.RoleEntity;
import com.oneune.laboratory.work.store.enums.RoleEnum;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RoleReader {

    RoleRepository roleRepository;

    public RoleEntity findByName(RoleEnum role) {
        return this.roleRepository.findByName(role)
                .orElseThrow(() -> new EntityNotFoundException("Role with name %s not found".formatted(role.name())));
    }

    public List<RoleEntity> findByNames(RoleEnum ... roles) {
        return this.roleRepository.findByNameIn(Arrays.stream(roles).toList());
    }
}
