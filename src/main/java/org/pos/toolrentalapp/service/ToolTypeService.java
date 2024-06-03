package org.pos.toolrentalapp.service;

import org.pos.toolrentalapp.entity.ToolType;
import org.pos.toolrentalapp.requestDto.ToolTypeRequest;
import org.pos.toolrentalapp.responseDto.ToolTypeResponse;
import org.pos.toolrentalapp.util.ToolTypeName;
import org.pos.toolrentalapp.exception.ToolsException;

public interface ToolTypeService {
    ToolTypeResponse insert(ToolTypeRequest toolTypeRequest);
    ToolTypeResponse findByToolTypeName(String toolType) throws ToolsException;
}
