package br.com.zup

import br.com.zup.chave.*
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
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@MicronautTest
class BuscarChaveControllerTest {


    @field:Inject
    lateinit var buscarStub: BuscarChavePixGrpc.BuscarChavePixBlockingStub

    @field:Inject
    lateinit var buscarListaStub: BuscarListaDeChavePixGrpc.BuscarListaDeChavePixBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    @DisplayName("deveriaEncontrarUmaChave")
    fun test1() {

        val response = BuscarChavePixResponse.newBuilder()
            .setNome("Rafael")
            .setAgencia("0001")
            .setCpf("12345678901")
            .setCreatedAt(LocalDateTime.now().toString())
            .setNomeBanco("ITAU")
            .setNumeroConta("456145-5")
            .setTipoDaChave("CPF")
            .setTipoDaConta("CONTA_CORRENTE")
            .setValorDaChave("12345678901")
            .build()

        Mockito.`when`(buscarStub.buscarUmaChavePix(Mockito.any())).thenReturn(response)


        val request = HttpRequest.GET<Any>("/chaves/12345678901")
        val responseRequisicao = client.toBlocking().exchange(request, BuscarChaveResponse::class.java)


        assertEquals(HttpStatus.OK, responseRequisicao.status)
    }

    @Test
    @DisplayName("deveriaDevolverUmaListaDeChave")
    fun test2() {

        val response = BuscarListaChaveRequestResponse.newBuilder()
            .setClienteId("c56dfef4-7901-44fb-84e2-a2cefb157890")
            .build()

        Mockito.`when`(buscarListaStub.buscarListaDeChave(Mockito.any())).thenReturn(response)


        val request = HttpRequest.GET<Any>("/chaves/12345678901")
        val responseRequisicao = client.toBlocking().exchange(request, ChaveRequestResponse::class.java)


        assertEquals(HttpStatus.OK, responseRequisicao.status)
    }


    @Singleton
    @Replaces(bean = BuscarChavePixGrpc.BuscarChavePixBlockingStub::class)
    fun stubMockBuscar() = Mockito.mock(BuscarChavePixGrpc.BuscarChavePixBlockingStub::class.java)

    @Singleton
    @Replaces(bean = BuscarListaDeChavePixGrpc.BuscarListaDeChavePixBlockingStub::class)
    fun stubMockBuscarLista() = Mockito.mock(BuscarListaDeChavePixGrpc.BuscarListaDeChavePixBlockingStub::class.java)

}