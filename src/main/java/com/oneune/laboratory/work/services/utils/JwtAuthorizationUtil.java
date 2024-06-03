package com.oneune.laboratory.work.services.utils;

import com.oneune.laboratory.work.configs.properties.JwtAuthorizationProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
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

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Log4j2
public class JwtAuthorizationUtil {

    public final static String ROLES_CLAIM = "roles";

    JwtAuthorizationProperties jwtAuthorizationProperties;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = Map.of(
                ROLES_CLAIM, userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()
        );

        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + this.jwtAuthorizationProperties.getLifetime().toMillis());

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(issuedDate)
                .expiration(expiredDate)
                .signWith(this.getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        final byte[] keyBytes = this.jwtAuthorizationProperties.getSecret().getBytes(UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims getAllClaimsFromToken(String jwtToken) {
        return Jwts.parser()
                .verifyWith(this.getSigningKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }

    public String getUsername(String jwtToken) {
        return this.getAllClaimsFromToken(jwtToken).getSubject();
    }

    public List<SimpleGrantedAuthority> getSimpleGrantedAuthorities(String jwtToken) {
        List<String> roles = this.getAllClaimsFromToken(jwtToken).get(ROLES_CLAIM, List.class);
        return RoleUtil.getSimpleGrantedAuthoritiesByString(roles);
    }
}
