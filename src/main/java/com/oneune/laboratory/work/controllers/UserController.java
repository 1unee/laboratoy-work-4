package com.oneune.laboratory.work.controllers;

import com.oneune.laboratory.work.services.business.UserService;
import com.oneune.laboratory.work.services.contracts.CRUDable;
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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User", description = "Endpoints for user entities actions")
@RestController
@RequestMapping("users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserController implements CRUDable<UserDto> {

    UserService userService;

    @Operation(
            summary = "Endpoint to create new user",
            description = "Using before JWT authorization"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(schema = @Schema(implementation = UserDto.class), mediaType = "application/json")
                    }),
            @ApiResponse(
                    responseCode = "500",
                    description = "User with written down username already exists or something else"
            )
    })
    @Override
    @PostMapping("${server.api.version.users}")
    public UserDto post(@RequestBody UserDto userDto) {
        return this.userService.post(userDto);
    }

    @Operation(
            summary = "Endpoint to get existing user by him id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(schema = @Schema(implementation = UserDto.class), mediaType = "application/json")
                    })
    })
    @Override
    @GetMapping("${server.api.version.users}/{userId}")
    public UserDto getById(@PathVariable Long userId) {
        return this.userService.getById(userId);
    }

    @Operation(
            summary = "Endpoint to search existing users with pagination"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(schema = @Schema(implementation = List.class), mediaType = "application/json")
                    })
    })
    @Override
    @GetMapping("${server.api.version.users}/search")
    public List<UserDto> search(@RequestParam int page, @RequestParam int size) {
        return this.userService.search(page, size);
    }

    @Operation(
            summary = "Endpoint to edit existing user by him id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(schema = @Schema(implementation = UserDto.class), mediaType = "application/json")
                    })
    })
    @Override
    @PutMapping("${server.api.version.users}/{userId}")
    public UserDto put(@PathVariable Long userId, @RequestBody UserDto userDto) {
        return this.userService.put(userId, userDto);
    }

    @Operation(
            summary = "Endpoint to delete existing user by him id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(schema = @Schema(implementation = UserDto.class), mediaType = "application/json")
                    })
    })
    @Override
    @DeleteMapping("${server.api.version.users}/{userId}")
    public UserDto deleteById(@PathVariable Long userId) {
        return this.userService.deleteById(userId);
    }

    @Operation(
            summary = "Endpoint to get all existing users",
            description = "Without optimized algorithms (for example, pagination or etc)"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(schema = @Schema(implementation = List.class), mediaType = "application/json")
                    })
    })
    @GetMapping("${server.api.version.users}")
    @Cacheable(value = "users", cacheManager = "userCacheManager")
    public List<UserDto> getAll(@RequestParam(required = false, defaultValue = "${server.repository.max-amount-on-all-getting}") Integer amount) {
        return this.userService.search(0, amount);
    }
}
