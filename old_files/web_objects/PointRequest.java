package ru.itmo.web_objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointRequest {
    private Double x;
    private Double y;
    private Double r;
}
