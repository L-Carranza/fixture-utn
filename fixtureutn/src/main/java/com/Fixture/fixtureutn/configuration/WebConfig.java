package com.Fixture.fixtureutn.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // Obtenemos la ruta del proyecto
        String projectPath = System.getProperty("user.dir");
        if (projectPath.contains("\\")) {
            projectPath = projectPath.replace("\\", "/");
        }

        registry.addResourceHandler("/img/**")
                // PRIORIDAD 1: Busca en la carpeta física (Para lo que subes nuevo)
                .addResourceLocations("file:///" + projectPath + "/src/main/resources/static/img/")
                // PRIORIDAD 2: Busca en el Classpath (Para lo que ya venía con el proyecto)
                .addResourceLocations("classpath:/static/img/");
    }
}