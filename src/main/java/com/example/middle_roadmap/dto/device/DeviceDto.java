package com.example.middle_roadmap.dto.device;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceDto {
    private Long id;
    private String type;
    private String name;
    private int quantity;
    private Long price;
}
