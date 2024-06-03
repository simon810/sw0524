package org.pos.toolrentalapp.service;

import org.pos.toolrentalapp.requestDto.ToolsRequest;
import org.pos.toolrentalapp.responseDto.ToolsResponse;
import org.pos.toolrentalapp.exception.ToolsException;

import java.util.List;

public interface ToolsService {
    ToolsResponse insert(ToolsRequest toolsRequest) throws ToolsException;
    List<ToolsResponse> getAllTools();
    ToolsResponse getToolById(Integer toolId) throws ToolsException;
    String removeToolById(Integer toolId);
}
