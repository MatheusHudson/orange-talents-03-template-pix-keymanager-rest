package br.com.zup.compartilhado.handlers

import br.com.zup.compartilhado.exception.ApiException
import io.micronaut.context.annotation.Requirements
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import io.micronaut.http.server.exceptions.response.ErrorContext
import io.micronaut.http.server.exceptions.response.ErrorResponseProcessor
import javax.inject.Singleton

@Produces
@Singleton
@Requirements(
    Requires(classes = [ApiException::class, ExceptionHandler::class])
)
class ApiExceptionHandler(private val errorResponseProcessor: ErrorResponseProcessor<Any>) :
    ExceptionHandler<ApiException, HttpResponse<*>> {

    override fun handle(request: HttpRequest<*>, exception: ApiException): HttpResponse<*> {

        return errorResponseProcessor.processResponse(
            ErrorContext.builder(request)
                .errorMessage(exception.message)
                .cause(exception)
                .build(), exception.status)
    }
}