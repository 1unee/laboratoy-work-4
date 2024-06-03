package com.oneune.laboratory.work.store.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Getter
public enum RoleEnum {
    USER,
    DEVELOPER,
    TESTER,
    SYSTEM_ANALYST,
    TEAM_LEAD,
    ADMIN
}
