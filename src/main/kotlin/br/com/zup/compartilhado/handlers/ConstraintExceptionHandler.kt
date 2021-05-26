package br.com.zup.compartilhado.handlers

import io.micronaut.context.annotation.Replaces
import io.micronaut.context.annotation.Requirements
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import io.micronaut.http.server.exceptions.response.ErrorContext
import io.micronaut.http.server.exceptions.response.ErrorResponseProcessor
import javax.inject.Singleton
import javax.validation.ConstraintViolationException

@Produces
@Replaces(bean=io.micronaut.validation.exceptions.ConstraintExceptionHandler::class)
@Singleton
@Requirements(
    Requires(classes = [ConstraintViolationException::class, ExceptionHandler::class])
)
class ConstraintExceptionHandler(private val errorResponseProcessor: ErrorResponseProcessor<Any>) :
    ExceptionHandler<ConstraintViolationException, HttpResponse<*>> {

    override fun handle(request: HttpRequest<*>, exception: ConstraintViolationException): HttpResponse<*> {
        return errorResponseProcessor.processResponse(
            ErrorContext.builder(request)
                .cause(exception)
                .errorMessage(exception.message!!.substringAfterLast("."))
                .build(), HttpResponse.badRequest<Any>())
    }
}