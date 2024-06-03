package com.oneune.laboratory.work.controllers;

import com.oneune.laboratory.work.services.business.AuthService;
import com.oneune.laboratory.work.store.dtos.AuthorizationDto;
import com.oneune.laboratory.work.store.dtos.UserDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthController {

    AuthService authService;

    @PostMapping("${server.api.version.auth}/sign-in")
    public AuthorizationDto register(@RequestBody UserDto user) {
        return this.authService.register(user);
    }

    @PostMapping("${server.api.version.auth}/log-in")
    public AuthorizationDto authenticate(@RequestBody UserDto user) {
        return this.authService.authenticate(user);
    }
}
