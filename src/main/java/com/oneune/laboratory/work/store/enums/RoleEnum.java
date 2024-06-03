package com.oneune.laboratory.work.store.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Getter
public enum RoleEnum {
    ROLE_GUEST,
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_DEVELOPER,
    ROLE_TESTER,
    ROLE_SYSTEM_ANALYST,
    ROLE_TEAM_LEAD
}
