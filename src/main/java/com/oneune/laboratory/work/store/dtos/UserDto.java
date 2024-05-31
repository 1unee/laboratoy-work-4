package com.oneune.laboratory.work.store.dtos;

import com.oneune.laboratory.work.store.enums.RoleEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class UserDto extends AbstractDto {
    String firstName;
    String lastName;
    RoleEnum role;
}
