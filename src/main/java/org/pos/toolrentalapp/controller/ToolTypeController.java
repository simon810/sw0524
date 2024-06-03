package org.pos.toolrentalapp.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
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
    public ToolTypeResponse insert(@RequestBody ToolTypeRequest toolType) {
        return toolTypeService.insert(toolType);
    }

    @GetMapping("/{toolType}")
    @ResponseStatus(HttpStatus.CREATED)
    public ToolTypeResponse getToolType(@PathVariable String toolType) throws ToolsException {
        return toolTypeService.findByToolTypeName(toolType);
    }
}
