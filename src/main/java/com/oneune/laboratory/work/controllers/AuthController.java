package com.oneune.laboratory.work.controllers;

import com.oneune.laboratory.work.services.business.AuthService;
import com.oneune.laboratory.work.store.dtos.AuthorizationDto;
import com.oneune.laboratory.work.store.dtos.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "Endpoints for authentication actions")
@RestController
@RequestMapping("auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthController {

    AuthService authService;

    @Operation(
            summary = "Endpoint to register new user",
            description = "After registration user will authenticate with JWT authorization. Has possibility to add ADMIN role with request param boolean flag 'addAdminRole'"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(schema = @Schema(implementation = AuthorizationDto.class), mediaType = "application/json")
                    }),
            @ApiResponse(
                    responseCode = "500",
                    description = "User with written down username already exists or something else"
            )
    })
    @PostMapping("${server.api.version.auth}/sign-in")
    public AuthorizationDto register(@RequestBody UserDto user,
                                     @RequestParam(required = false, defaultValue = "false") boolean addAdminRole) {
        return this.authService.register(user, addAdminRole);
    }

    @Operation(
            summary = "Endpoint to authenticate existing user",
            description = "Using JWT authorization"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(schema = @Schema(implementation = AuthorizationDto.class), mediaType = "application/json")
                    }),
            @ApiResponse(
                    responseCode = "500",
                    description = "Bad credentials or something else"
            )
    })
    @PostMapping("${server.api.version.auth}/log-in")
    public AuthorizationDto authenticate(@RequestBody UserDto user) {
        return this.authService.authenticate(user);
    }
}
