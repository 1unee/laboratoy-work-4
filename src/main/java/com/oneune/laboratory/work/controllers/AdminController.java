package com.oneune.laboratory.work.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "Admin", description = "Endpoints for admin actions")
@RestController
@RequestMapping("admin")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AdminController {

    @Operation(
            summary = "Test endpoint",
            description = "Was added to check available getting greeting only with ADMIN role"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {@Content(schema = @Schema(implementation = Map.class), mediaType = "application/json")}
            )
    })
    @GetMapping("${server.api.version.admin}/test")
    public Map<CharSequence, CharSequence> test() {
        return Map.of("greeting", "Hello world from Admin mapping!");
    }
}
