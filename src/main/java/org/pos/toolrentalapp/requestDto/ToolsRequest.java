package org.pos.toolrentalapp.requestDto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolsRequest {
    private Integer id;
    private String toolCode;
    @JsonProperty("toolType")
    private String toolTypeName;
    private String brand;
}
