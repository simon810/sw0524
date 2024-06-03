package org.pos.toolrentalapp.service.impl;

import org.pos.toolrentalapp.entity.ToolType;
import org.pos.toolrentalapp.exception.ToolsException;
import org.pos.toolrentalapp.repository.ToolTypeRepository;
import org.pos.toolrentalapp.requestDto.ToolTypeRequest;
import org.pos.toolrentalapp.responseDto.ToolTypeResponse;
import org.pos.toolrentalapp.service.ToolTypeService;
import org.pos.toolrentalapp.util.Charge;
import org.springframework.stereotype.Service;

@Service
public class ToolTypeServiceImpl implements ToolTypeService {

    private final ToolTypeRepository toolTypeRepository;

    public ToolTypeServiceImpl(ToolTypeRepository toolTypeRepository) {
        this.toolTypeRepository = toolTypeRepository;
    }

    @Override
    public ToolTypeResponse insert(ToolTypeRequest toolTypeRequest) {
        var toolType = mapToEntity(toolTypeRequest);
        return mapToDto(toolTypeRepository.save(toolType));
    }

    //Candidate for cache
    @Override
    public ToolTypeResponse findByToolTypeName(String toolTypeName) throws ToolsException {
        var toolType = toolTypeRepository.findToolTypeByToolTypeName(toolTypeName).orElseThrow(() -> new ToolsException("Tool Type Name not Existed!!"));
        return mapToDto(toolType);
    }

    private ToolTypeResponse mapToDto(ToolType toolType) {
        ToolTypeResponse toolTypeResponse = new ToolTypeResponse();
        toolTypeResponse.setToolTypeId(toolType.getToolTypeId());
        toolTypeResponse.setToolTypeName(toolType.getToolTypeName());
        toolTypeResponse.setDailyCharge(toolType.getDailyCharge());
        toolTypeResponse.setHolidayCharge(toolType.getHolidayCharge() ? Charge.Yes : Charge.No);
        toolTypeResponse.setWeekEndCharge(toolType.getWeekEndCharge() ? Charge.Yes : Charge.No);
        toolTypeResponse.setWeekDayCharge(toolType.getWeekDayCharge() ? Charge.Yes : Charge.No);

        return toolTypeResponse;
    }

    private ToolType mapToEntity(ToolTypeRequest toolTypeRequest) {
        ToolType toolType = new ToolType();
        toolType.setToolTypeName(toolTypeRequest.getToolTypeName());
        toolType.setDailyCharge(toolTypeRequest.getDailyCharge());
        toolType.setHolidayCharge(toolTypeRequest.getHolidayCharge().equals(Charge.Yes));
        toolType.setWeekEndCharge(toolTypeRequest.getWeekEndCharge().equals(Charge.Yes));
        toolType.setWeekDayCharge(toolTypeRequest.getWeekDayCharge().equals(Charge.Yes));

        return toolType;
    }
}
