package org.pos.toolrentalapp.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.pos.toolrentalapp.entity.ToolType;
import org.pos.toolrentalapp.exception.ToolsException;
import org.pos.toolrentalapp.requestDto.ToolTypeRequest;
import org.pos.toolrentalapp.responseDto.ToolTypeResponse;
import org.pos.toolrentalapp.service.ToolTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/tool-type")
@Tag(name = "ToolType")
public class ToolTypeController {

    private final ToolTypeService toolTypeService;

    public ToolTypeController(ToolTypeService toolTypeService) {
        this.toolTypeService = toolTypeService;
    }

    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Add a new tool type",
            description = "Add a new tool type with the specified details",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Tool type request",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ToolTypeRequest.class),
                            examples = @ExampleObject(
                                    name = "example",
                                    value = "{ \"dailyCharge\": 2.12, \"weekDayCharge\": \"Yes\", \"weekEndCharge\": \"Yes\", \"holidayCharge\": \"No\", \"toolType\": \"ToolTypeTest\" }"
                            )
                    )
            )
    )

    public ToolTypeResponse insert(@RequestBody ToolTypeRequest toolType) {
        return toolTypeService.insert(toolType);
    }

    @GetMapping("/{toolType}")
    @ResponseStatus(HttpStatus.CREATED)
    public ToolTypeResponse getToolType(@PathVariable String toolType) throws ToolsException {
        return toolTypeService.findByToolTypeName(toolType);
    }
}
