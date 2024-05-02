package com.example.teamcity.api.models.authSettings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthSettings {

    private Boolean allowGuest;
    private String guestUsername;
    private String welcomeText;
    private Boolean collapseLoginForm;
    private Boolean perProjectPermissions;
    private Boolean emailVerification;
    private AuthModules modules;

}
