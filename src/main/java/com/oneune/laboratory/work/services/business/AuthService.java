package com.oneune.laboratory.work.services.business;

import com.oneune.laboratory.work.services.readers.UserReader;
import com.oneune.laboratory.work.services.utils.JwtAuthorizationUtil;
import com.oneune.laboratory.work.store.dtos.AuthorizationDto;
import com.oneune.laboratory.work.store.dtos.UserDto;
import com.oneune.laboratory.work.store.exceptions.BusinessLogicException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Log4j2
public class AuthService {

    AuthenticationManager authenticationManager;
    UserReader userReader;
    JwtAuthorizationUtil jwtAuthorizationUtil;
    UserService userService;

    public AuthorizationDto register(UserDto payload) {
        try {
            UserDto user = this.userReader.findUserDtoByUsername(payload.getUsername());
            throw new BusinessLogicException(String.format("User '%s' already exists!", user.getUsername()));
        } catch (JpaObjectRetrievalFailureException | EntityNotFoundException e) {
            UserDto createdUser = this.userService.post(payload);
            createdUser.setPassword(payload.getPassword());
            return this.authenticate(createdUser);
        } catch (Exception e) {
            String authenticationErrorMessage = "Authentication error";
            log.error("Main message: {}, exception: {}", authenticationErrorMessage, e);
            throw new BadCredentialsException(authenticationErrorMessage, e);
        }
    }

    public AuthorizationDto authenticate(UserDto userPayloadAuthentication) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userPayloadAuthentication.getUsername(), userPayloadAuthentication.getPassword()
            );
            this.authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            String badCredentialsMessage = "Incorrect username or password";
            log.error("Main message: {}, exception: {}", badCredentialsMessage, e);
            throw new BadCredentialsException(badCredentialsMessage, e);
        } catch (Exception e) {
            String authenticationErrorMessage = "Authentication error";
            log.error("Main message: {}, exception: {}", authenticationErrorMessage, e);
            throw new BadCredentialsException(authenticationErrorMessage, e);
        }

        UserDetails userDetails = this.userReader.loadUserByUsername(userPayloadAuthentication.getUsername());

        return AuthorizationDto.builder()
                .username(userDetails.getUsername())
                .roles(userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .token(this.jwtAuthorizationUtil.generateToken(userDetails))
                .build();
    }
}
