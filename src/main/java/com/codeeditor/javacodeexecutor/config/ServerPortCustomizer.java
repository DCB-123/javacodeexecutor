package com.codeeditor.javacodeexecutor.config;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.stereotype.Component;

@Component
public class ServerPortCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        String port = System.getenv("PORT");
        if (port != null) {
            factory.setPort(Integer.parseInt(port));
        } else {
            factory.setPort(8081); // fallback for local dev
        }
    }
}