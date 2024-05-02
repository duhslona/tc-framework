package com.example.teamcity.api.models.authSettings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Property {

    private String name;
    private String value;
    private Boolean inherited;
    private Type type;
}
