package org.pos.toolrentalapp.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.pos.toolrentalapp.exception.ToolsException;
import org.pos.toolrentalapp.requestDto.ToolsRequest;
import org.pos.toolrentalapp.responseDto.ToolsResponse;
import org.pos.toolrentalapp.service.ToolsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tools")
@Tag(name = "Tools")
public class ToolsController {

    private final ToolsService toolsService;

    @Autowired
    public ToolsController(ToolsService toolsService) {
        this.toolsService = toolsService;
    }


    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Add a new tool",
            description = "Add a new tool with the specified details",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Tool request",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ToolsRequest.class),
                            examples = @ExampleObject(
                                    name = "example",
                                    value = "{ \"toolCode\": \"TestCode\", \"brand\": \"TestBrand\", \"toolType\": \"Jackhammer\" }"
                            )
                    )
            )
    )
    public ToolsResponse insertTool(@RequestBody ToolsRequest toolsRequest) throws ToolsException {
        return toolsService.insert(toolsRequest);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ToolsResponse> getTools() {
        return toolsService.getAllTools();
    }

    @GetMapping("/tool")
    @ResponseStatus(HttpStatus.OK)
    public ToolsResponse getTool(@RequestParam("tool-id") Integer toolId) throws ToolsException {
        return toolsService.getToolById(toolId);
    }

    @DeleteMapping("/tool")
    @ResponseStatus(HttpStatus.OK)
    public String removeTool(@RequestParam("tool-id") Integer toolId) {
        return toolsService.removeToolById(toolId);
    }
}
