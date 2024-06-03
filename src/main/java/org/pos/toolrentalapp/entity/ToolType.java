package org.pos.toolrentalapp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class ToolType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer toolTypeId;
    @JsonProperty("toolType")
    @Column(unique = true)
    private String toolTypeName;
    private Double dailyCharge;
    private Boolean weekDayCharge;
    private Boolean weekEndCharge;
    private Boolean holidayCharge;
}