package com.codecool.volunti.controller;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class LoginRedirectController
        extends SimpleUrlAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {

    public LoginRedirectController() {
        super();
        setUseReferer(true);
    }
}
