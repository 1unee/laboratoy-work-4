package com.oneune.laboratory.work.configs.security.filters;

import com.oneune.laboratory.work.services.utils.JwtAuthorizationUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Log4j2
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    JwtAuthorizationUtil jwtAuthorizationUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        if (Objects.nonNull(authHeader) && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
            try {
                username = this.jwtAuthorizationUtil.getUsername(jwtToken);
            } catch (ExpiredJwtException e) {
                String authorizationTimeIsOutMessage = "Authorization time is out";
                log.info(authorizationTimeIsOutMessage);
                throw new AuthenticationServiceException(authorizationTimeIsOutMessage);
            } catch (SignatureException e) {
                String secretInNotCorrectMessage = "Secret in not correct";
                log.error(secretInNotCorrectMessage);
                throw new AuthenticationServiceException(secretInNotCorrectMessage);
            }
        }

        if (Objects.nonNull(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    username, null, this.jwtAuthorizationUtil.getSimpleGrantedAuthorities(jwtToken)
            );
            SecurityContextHolder.getContext().setAuthentication(token);
        }

        filterChain.doFilter(request, response);
    }
}
