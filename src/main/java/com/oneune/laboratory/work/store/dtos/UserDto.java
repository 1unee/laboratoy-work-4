package com.oneune.laboratory.work.store.dtos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class UserDto extends AbstractDto {
    PersonalDto personal;
    String username;
    String password;
    List<RoleDto> roles;
}
