package br.com.zup.compartilhado.handlers

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.context.annotation.Requirements
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Produces
import io.micronaut.http.hateoas.JsonError
import io.micronaut.http.server.exceptions.ExceptionHandler
import org.slf4j.LoggerFactory
import javax.inject.Singleton


@Produces
@Singleton
@Requirements(
    Requires(classes = [StatusRuntimeException::class, ExceptionHandler::class])
)
class GrpcHandler : ExceptionHandler<StatusRuntimeException, HttpResponse<Any>> {

    override fun handle(request: HttpRequest<*>?, exception: StatusRuntimeException): HttpResponse<Any> {
        val statusCode = exception.status.code
        val statusDescription = exception.status.description ?: ""

        val logger = LoggerFactory.getLogger(this::class.java)

        val (httpStatus, message) = when (statusCode) {
            Status.NOT_FOUND.code -> Pair(HttpStatus.NOT_FOUND, statusDescription)
            Status.INVALID_ARGUMENT.code -> Pair(HttpStatus.BAD_REQUEST, "Dados da requisição invalidos!")
            Status.ALREADY_EXISTS.code -> Pair(HttpStatus.UNPROCESSABLE_ENTITY, statusDescription)
            Status.UNAVAILABLE.code -> Pair(HttpStatus.SERVICE_UNAVAILABLE, statusDescription)
            else -> {
                logger.error("Erro inesperado '${exception.javaClass.name}'  ao processar requisição", exception)
                Pair(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possivel completar a solicitação!")
            }
        }

        return HttpResponse.status<JsonError>(httpStatus).body(JsonError(message))
    }

}