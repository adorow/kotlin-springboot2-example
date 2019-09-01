package com.github.adorow.sample.api.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@ConfigurationProperties(prefix = "app.properties")
@Component
class ApplicationProperties {

    lateinit var aProp: String

}

// a sealed class can only be extended on the file that it is defined
sealed class GitProperties {
    abstract val buildVersion: String
}

// this Bean will be loaded by Spring if git.properties is present
@Configuration
@ConditionalOnResource(resources = ["classpath:git.properties"])
@PropertySource("classpath:git.properties")
class GitPropertiesFromFile : GitProperties() {

    @Value("\${git.build.version:unversioned}")
    override lateinit var buildVersion: String

}

// this Bean will be loaded by Spring if the bean GitPropertiesFromFile is missing (which is caused by a missing git.properties file)
@Configuration
@ConditionalOnMissingBean(GitPropertiesFromFile::class)
class MissingGitProperties : GitProperties() {

    override val buildVersion: String = "missing"

}