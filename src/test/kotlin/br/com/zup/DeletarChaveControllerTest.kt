package br.com.zup

import br.com.zup.chave.DeletaChaveRequest
import br.com.zup.chave.DeletaChaveResponse
import br.com.zup.chave.RequestCadastrarChave
import br.com.zup.chave.ResponseRegistrarChave
import br.com.zup.chave.enum.TipoDaChave
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@MicronautTest
class DeletarChaveControllerTest {


    @field:Inject
    lateinit var deletarStub: DeletarChaveServiceGrpc.DeletarChaveServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    @DisplayName("deveriaRemoverUmaChave")
    fun test1() {

        val response = DeletarChaveResponse.newBuilder()
            .setParticipant("60701190")
            .setChave("eb9c0f7b-8a72-4e5c-979d-25fcda25d700")
            .build()


        Mockito.`when`(deletarStub.deletarChave(Mockito.any())).thenReturn(response)


        val request = HttpRequest.DELETE<Any>(
            "/chaves/deletar", DeletaChaveRequest(
                "eb9c0f7b-8a72-4e5c-979d-25fcda25d700", "CHAVEALEATORIA",
                "eb9c0f7b-8a72-4e5c-979d-25fcda25d700", "601054"
            )
        )
        val responseRequisicao = client.toBlocking().exchange(request, DeletaChaveResponse::class.java)


        assertEquals(HttpStatus.OK, responseRequisicao.status)
    }



    @Singleton
    @Replaces(bean = DeletarChaveServiceGrpc.DeletarChaveServiceBlockingStub::class)
    fun stubMockDeletar() = Mockito.mock(DeletarChaveServiceGrpc.DeletarChaveServiceBlockingStub::class.java)

}