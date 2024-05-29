package com.oneune.laboratory.work.controllers;


import com.oneune.laboratory.work.services.business.UserService;
import com.oneune.laboratory.work.services.contracts.CRUDable;
import com.oneune.laboratory.work.store.dtos.UserDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserController implements CRUDable<UserDto> {

    UserService userService;

    @Override
    @PostMapping("${server.api.version.users}")
    public UserDto post(@RequestBody UserDto userDto) {
        return this.userService.post(userDto);
    }

    @Override
    @GetMapping("${server.api.version.users}/{userId}")
    public UserDto getById(@PathVariable Long userId) {
        return this.userService.getById(userId);
    }

    @Override
    @GetMapping("${server.api.version.users}/search")
    public List<UserDto> paginate(@RequestParam int page, @RequestParam int size) {
        return this.userService.paginate(page, size);
    }

    @Override
    @PutMapping("${server.api.version.users}/{userId}")
    public UserDto put(@PathVariable Long userId, @RequestBody UserDto userDto) {
        return this.userService.put(userId, userDto);
    }

    @Override
    @DeleteMapping("${server.api.version.users}/{userId}")
    public UserDto deleteById(@PathVariable Long userId) {
        return this.userService.deleteById(userId);
    }
}
