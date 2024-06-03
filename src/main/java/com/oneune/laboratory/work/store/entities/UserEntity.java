package com.oneune.laboratory.work.store.entities;

import com.oneune.laboratory.work.services.contracts.Identifiable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "`user`") // sql ругается на служебное слово
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class UserEntity extends AbstractEntity implements UserDetails {

    @OneToOne
    @JoinColumn(name = "personal_id")
    PersonalEntity personal;

    String username;
    String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role_link",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    List<RoleEntity> roles = new ArrayList<>();

    public static UserEntity create(String username, String password, List<RoleEntity> roles) {
        if (Identifiable.areAllEntitiesPosted(roles)) {
            UserEntity userEntity = new UserEntity();
            userEntity.username = username;
            userEntity.password = password;
            userEntity.roles.addAll(roles);
            return userEntity;
        } else {
            String allNonPostedRoles = Identifiable.findNonPostedEntities(roles).stream()
                    .map(role -> role.getName().name())
                    .collect(Collectors.joining(", "));
            throw new UnsupportedOperationException(
                    "Roles <%s> are not exists (ids not defined)".formatted(allNonPostedRoles)
            );
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
