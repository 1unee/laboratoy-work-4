package com.oneune.laboratory.work.store.entities;

import com.oneune.laboratory.work.store.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "\"user\"") // sql ругается на служебное слово
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data // нет связей, поэтому спокойно юзаем без страха рекурсии с вызовом equals/hashcode
public class UserEntity extends AbstractEntity {
    String firstName;
    String lastName;
    @Enumerated(EnumType.STRING)
    RoleEnum role;
}
