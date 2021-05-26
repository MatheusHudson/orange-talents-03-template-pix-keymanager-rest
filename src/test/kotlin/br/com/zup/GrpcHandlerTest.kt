package br.com.zup

import br.com.zup.compartilhado.handlers.GrpcHandler
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


@MicronautTest
class GrpcHandlerTest {

    val requestGenerica = HttpRequest.GET<Any>("/")

    @Test
    @DisplayName("deveRetornarNotFound")
    fun test1() {

        val exception = StatusRuntimeException(Status.NOT_FOUND.withDescription("Não encontrado"))

        val response = GrpcHandler().handle(requestGenerica, exception)
        assertEquals(HttpStatus.NOT_FOUND, response.status)
    }

    @Test
    @DisplayName("deveRetornarUmBadRequest")
    fun test2() {

        val exception = StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription("Argumentos invalido!"))

        val response = GrpcHandler().handle(requestGenerica, exception)
        assertEquals(HttpStatus.BAD_REQUEST, response.status)
    }


    @Test
    @DisplayName("deveRetornarUmUnprocessableEntity")
    fun test3() {

        val exception = StatusRuntimeException(Status.ALREADY_EXISTS.withDescription("Já existente"))

        val response = GrpcHandler().handle(requestGenerica, exception)
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.status)
    }

    @Test
    @DisplayName("deveRetornarUmServiceUnavailable")
    fun test4() {

        val exception = StatusRuntimeException(Status.UNAVAILABLE.withDescription("Indisponivel"))

        val response = GrpcHandler().handle(requestGenerica, exception)
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.status)
    }

    @Test
    @DisplayName("deveRetornarUmInternalServerErro")
    fun test5() {

        val exception = StatusRuntimeException(Status.UNIMPLEMENTED.withDescription("Desconhecido"))

        val response = GrpcHandler().handle(requestGenerica, exception)
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.status)
    }




}