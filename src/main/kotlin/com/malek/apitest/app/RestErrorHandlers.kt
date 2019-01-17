package com.malek.apitest.app

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ControllerAdvice
class ElementNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(ElementNotFound::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun notFoundHandler(elementNotFound: ElementNotFound): Error {
        return com.malek.apitest.app.Error(HttpStatus.NOT_FOUND, elementNotFound.message.toString())
    }

}

data class ElementNotFound(val id: Long) : RuntimeException("could not find element with id $id")
data class Error(var status: HttpStatus, var message: String)