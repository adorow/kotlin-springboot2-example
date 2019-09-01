package com.github.adorow.sample.api.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfiguration : WebMvcConfigurer {

    override fun addViewControllers(registry: ViewControllerRegistry) {
        // land directly to the Swagger page
        registry.addRedirectViewController("/", "/swagger-ui.html")

        registry.addRedirectViewController("/v1/api-docs", "/swagger-ui.html")
    }

}