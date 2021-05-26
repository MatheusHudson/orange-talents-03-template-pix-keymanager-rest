package br.com.zup

import br.com.zup.chave.RequestCadastrarChave
import br.com.zup.chave.ResponseRegistrarChave
import br.com.zup.chave.enum.TipoDaChave
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import java.lang.IllegalArgumentException
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@MicronautTest
class RegistrarChaveControllerTest {


    @field:Inject
    lateinit var registrarStub: RegistrarChaveServiceGrpc.RegistrarChaveServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    @DisplayName("deveCadastarUmaChavePix")
    fun test1() {

        val response = RegistroChaveResponse.newBuilder()
            .setPixId(UUID.randomUUID().toString())
            .setChave("12345678901")
            .setTipoChave("CPF")
            .build()


        Mockito.`when`(registrarStub.registrarChave(Mockito.any())).thenReturn(response)

        val requestRequisicao =
            RequestCadastrarChave("c56dfef4-7901-44fb-84e2-a2cefb157890", "CHAVEALEATORIA", "", "CONTA_CORRENTE")


        val request = HttpRequest.POST("/chaves", requestRequisicao)
        val responseRequisicao  = client.toBlocking().exchange(request,ResponseRegistrarChave::class.java)

        assertEquals(HttpStatus.CREATED, responseRequisicao.status)
        assertTrue(responseRequisicao.headers.contains("Location"))
    }


    @Test
    @DisplayName("naoDeveCadastarUmaChavePixTipoChaveInvalido")
    fun test2() {


        val requestRequisicao =
            RequestCadastrarChave("c56dfef4-7901-44fb-84e2-a2cefb157890", "CHAVEALEATORI", "", "CONTA_CORRENTE")


        val request = HttpRequest.POST("/chaves", requestRequisicao)

      val exception =  assertThrows<HttpClientResponseException>
       {
         client.toBlocking().exchange(request,ResponseRegistrarChave::class.java)
       }

        with(exception) {
            assertEquals("Dados invalidos! Valores aceitos: ${TipoDaChave.todasChavesString}", exception.message)
        }
    }

    @Test
    @DisplayName("naoDeveCadastarUmaChavePixTipoDeContaInvalido")
    fun test3() {



        val requestRequisicao =
            RequestCadastrarChave("c56dfef4-7901-44fb-84e2-a2cefb157890", "CHAVEALEATORIA", "", "CONTA_CORRENT")


        val request = HttpRequest.POST("/chaves", requestRequisicao)

      val exception =  assertThrows<HttpClientResponseException>
       {
         client.toBlocking().exchange(request,ResponseRegistrarChave::class.java)
       }

        with(exception) {
            assertEquals("Dados invalidos! Valores aceitos: ${
                TipoDeConta.values().map { tipoDeConta -> tipoDeConta.name }
            }".substringBeforeLast(", UNRECOGNIZED") + "]", exception.message)
        }
    }

    @Singleton
    @Replaces(bean = RegistrarChaveServiceGrpc.RegistrarChaveServiceBlockingStub::class)
    fun stubMock() = Mockito.mock(RegistrarChaveServiceGrpc.RegistrarChaveServiceBlockingStub::class.java)

}