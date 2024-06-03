package com.oneune.laboratory.work.store.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AuthorizationDto {
    String username;
    List<String> roles;
    String token;
}
