package com.oneune.laboratory.work.store.entities;

import com.oneune.laboratory.work.store.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "role")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class RoleEntity extends AbstractEntity {
    @Enumerated(EnumType.STRING)
    RoleEnum name;
}
