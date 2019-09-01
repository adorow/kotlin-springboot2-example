package com.github.adorow.sample.api

import com.github.adorow.sample.api.KotlinBoot2Example.Companion.log
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.ApplicationPidFileWriter
import org.springframework.core.env.Environment
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters

@SpringBootApplication
@EntityScan(basePackageClasses = [KotlinBoot2Example::class, Jsr310JpaConverters::class])
class KotlinBoot2Example {

    companion object {
        val log: Logger = LoggerFactory.getLogger(KotlinBoot2Example::class.java)
    }

}

fun main(args: Array<String>) {
    val app = SpringApplication(KotlinBoot2Example::class.java)
    app.addListeners(ApplicationPidFileWriter())
    app.setBanner { env, _, _ -> printBanner(env) }
    app.run(*args)
}

private fun printBanner(env: Environment) {
    log.info("# =============================================== #")
    log.info("artifact.name=" + env.getProperty("info.build.artifact"))
    log.info("artifact.version=" + env.getProperty("info.build.version"))
    log.info("timestamp=" + env.getProperty("info.build.timestamp"))
    log.info("# =============================================== #")
}
