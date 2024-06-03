package org.pos.toolrentalapp.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.pos.toolrentalapp.entity.Tool;
import org.pos.toolrentalapp.entity.ToolType;
import org.pos.toolrentalapp.exception.ToolsException;
import org.pos.toolrentalapp.repository.ToolsRespository;
import org.pos.toolrentalapp.requestDto.ToolsRequest;
import org.pos.toolrentalapp.responseDto.ToolTypeResponse;
import org.pos.toolrentalapp.responseDto.ToolsResponse;
import org.pos.toolrentalapp.service.ToolTypeService;
import org.pos.toolrentalapp.service.ToolsService;
import org.pos.toolrentalapp.util.Charge;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ToolsServiceImpl implements ToolsService {


    private final ToolsRespository toolsRespository;
    private final ToolTypeService toolTypeService;

    public ToolsServiceImpl(ToolsRespository toolsRespository, ToolTypeService toolTypeService) {
        this.toolsRespository = toolsRespository;
        this.toolTypeService = toolTypeService;
    }

    @Override
    public ToolsResponse insert(ToolsRequest toolsRequest) throws ToolsException {
        ToolTypeResponse toolType = toolTypeService.findByToolTypeName(toolsRequest.getToolTypeName());

        Tool tool = mapToEntity(toolsRequest, toolType);
        return mapToDto(toolsRespository.save(tool));
    }

    @Override
    public List<ToolsResponse> getAllTools() {
        return toolsRespository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public ToolsResponse getToolById(Integer toolId) throws ToolsException {
        log.debug("Tool ID: {}", toolId);
        return mapToDto(toolsRespository.findById(toolId).orElseThrow(() -> new ToolsException("Tool Not Found for tool-id: " + toolId)));
    }

    @Override
    public String removeToolById(Integer toolId) {
        toolsRespository.deleteById(toolId);
        return "Tool Removed Successfully!";
    }


    private ToolsResponse mapToDto(Tool tool) {
        ToolTypeResponse toolTypeResponse = new ToolTypeResponse();
        ToolType toolType = tool.getToolType();
        toolTypeResponse.setToolTypeId(toolType.getToolTypeId());
        toolTypeResponse.setToolTypeName(toolType.getToolTypeName());
        toolTypeResponse.setDailyCharge(toolType.getDailyCharge());
        toolTypeResponse.setHolidayCharge(toolType.getHolidayCharge() ? Charge.Yes : Charge.No);
        toolTypeResponse.setWeekEndCharge(toolType.getWeekEndCharge() ? Charge.Yes : Charge.No);
        toolTypeResponse.setWeekDayCharge(toolType.getWeekDayCharge() ? Charge.Yes : Charge.No);


        ToolsResponse toolsResponse = new ToolsResponse();
        toolsResponse.setToolCode(tool.getToolCode());
        toolsResponse.setId(tool.getId());
        toolsResponse.setBrand(tool.getBrand());
        toolsResponse.setToolType(toolTypeResponse);

        return toolsResponse;
    }

    private Tool mapToEntity(ToolsRequest toolsRequest, ToolTypeResponse toolTypeResponse) {

        ToolType toolType = new ToolType();
        toolType.setToolTypeName(toolTypeResponse.getToolTypeName());
        toolType.setDailyCharge(toolTypeResponse.getDailyCharge());
        toolType.setHolidayCharge(toolTypeResponse.getHolidayCharge().equals(Charge.Yes));
        toolType.setWeekEndCharge(toolTypeResponse.getWeekEndCharge().equals(Charge.Yes));
        toolType.setWeekDayCharge(toolTypeResponse.getWeekDayCharge().equals(Charge.Yes));

        return new Tool(toolsRequest.getId(), toolsRequest.getToolCode(), toolsRequest.getBrand(), toolType);
    }

}
