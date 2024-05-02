package com.example.teamcity.api.models.authSettings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Properties {

    private List<Property> property;
    private Integer count;
    private String href;
}
