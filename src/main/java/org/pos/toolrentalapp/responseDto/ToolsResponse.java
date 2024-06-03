package org.pos.toolrentalapp.responseDto;

import lombok.Data;
import org.pos.toolrentalapp.entity.ToolType;


@Data
//@Builder
public class ToolsResponse {
    private Integer id;
    private String toolCode;;
    private ToolTypeResponse toolType;
    private String brand;
}
