package com.oneune.laboratory.work.services.utils;

import com.oneune.laboratory.work.store.dtos.RoleDto;
import com.oneune.laboratory.work.store.entities.RoleEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public class RoleUtil {

    public static List<SimpleGrantedAuthority> getSimpleGrantedAuthoritiesByString(Collection<String> roles) {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    public static List<SimpleGrantedAuthority> getSimpleGrantedAuthoritiesByDto(Collection<RoleDto> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .toList();
    }

    public static List<SimpleGrantedAuthority> getSimpleGrantedAuthoritiesByEntity(Collection<RoleEntity> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .toList();
    }
}
