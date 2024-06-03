package com.oneune.laboratory.work.services.utils;

import com.oneune.laboratory.work.configs.properties.JwtAuthorizationProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Log4j2
public class JwtAuthorizationUtil {

    JwtAuthorizationProperties jwtAuthorizationProperties;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // TODO: refactor dates (may be use new dependency for jwt)
    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>(Map.of(
                "roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()
        ));

        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + this.jwtAuthorizationProperties.getLifetime().toMillis());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, this.jwtAuthorizationProperties.getSecret())
                .compact();
    }

    public Claims getAllClaimsFromToken(String jwtToken) {
        return Jwts.parser()
                .setSigningKey(this.jwtAuthorizationProperties.getSecret())
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    public String getUsername(String jwtToken) {
        return this.getAllClaimsFromToken(jwtToken).getSubject();
    }

    public List<String> getRoles(String jwtToken) {
        return (List<String>) this.getAllClaimsFromToken(jwtToken).get("roles", List.class);
    }

    public List<SimpleGrantedAuthority> getSimpleGrantedAuthorities(String jwtToken) {
        List<String> roles = this.getRoles(jwtToken);
        return RoleUtil.getSimpleGrantedAuthoritiesByString(roles);
    }
}
