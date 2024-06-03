package org.pos.toolrentalapp.requestDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.pos.toolrentalapp.util.Charge;

@Setter
@Getter
public class ToolTypeRequest {
    private Integer toolTypeId;
    @JsonProperty("toolType")
    private String toolTypeName;
    private Double dailyCharge;
    private Charge weekDayCharge;
    private Charge weekEndCharge;
    private Charge holidayCharge;
}