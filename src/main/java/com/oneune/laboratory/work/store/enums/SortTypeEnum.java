package com.oneune.laboratory.work.store.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SortTypeEnum {

    ASC("By growing"),
    DESC("By downgrading");

    private final String explanation;
}
