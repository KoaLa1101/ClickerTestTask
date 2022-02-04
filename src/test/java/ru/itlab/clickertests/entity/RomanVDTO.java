package ru.itlab.clickertests.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RomanVDTO {
    private Integer code;
    private String value;
    private String owner;
}
