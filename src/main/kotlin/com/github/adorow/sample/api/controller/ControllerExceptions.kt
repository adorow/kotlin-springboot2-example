package com.github.adorow.sample.api.controller

class NotFoundException(message: String, cause: Throwable? = null): RuntimeException(message, cause)