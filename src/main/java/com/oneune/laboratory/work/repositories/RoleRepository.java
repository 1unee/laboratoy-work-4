package com.oneune.laboratory.work.repositories;

import com.oneune.laboratory.work.store.entities.RoleEntity;
import com.oneune.laboratory.work.store.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(RoleEnum name);
    List<RoleEntity> findByNameIn(List<RoleEnum> names);
}
