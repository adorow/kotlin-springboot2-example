package com.github.adorow.sample.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors.regex
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger.web.UiConfiguration
import springfox.documentation.swagger.web.UiConfigurationBuilder
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.time.LocalDate

@Configuration
@EnableSwagger2
class SwaggerConfig(
        private val gitProps: GitProperties
) {

    @Bean
    fun uiConfiguration(): UiConfiguration {
        return UiConfigurationBuilder.builder()
                .displayRequestDuration(true)
                .validatorUrl("")
                .build()
    }

    @Bean
    fun inboundCostPerFsApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .groupName("my-api")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(regex("/v1/my-api/.*"))
                .build()
                .directModelSubstitute(LocalDate::class.java, String::class.java)
                .useDefaultResponseMessages(true)
                .apiInfo(apiInfo())
    }

    @Bean
    fun troubleshooting(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .groupName("actuator")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(regex("/actuator($|/.*)"))
                .build()
                .useDefaultResponseMessages(true)
                .apiInfo(apiInfo())
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfo(
                "My API",
                "My API + some extra add-ons from Spring Boot actuator",
                gitProps.buildVersion,
                null,
                Contact("Developer", null, "developer@company.com"),
                null, null,
                emptyList())
    }

}