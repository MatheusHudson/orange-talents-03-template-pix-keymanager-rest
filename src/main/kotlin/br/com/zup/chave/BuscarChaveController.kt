package br.com.zup.chave

import br.com.zup.*
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import java.net.URI
import javax.validation.Valid


@Controller("/chaves/{clientId}")
class BuscarChaveController(val buscarGrpc: BuscarChavePixGrpc.BuscarChavePixBlockingStub,
val listarChaveGrpc: BuscarListaDeChavePixGrpc.BuscarListaDeChavePixBlockingStub) {

    @Get("/{key}")
    fun buscarChavePix(@PathVariable key: String): HttpResponse<Any> {


        val grpcResponse = buscarGrpc.buscarUmaChavePix(BuscarChavePixRequest.newBuilder().setChavePix(key).build())

        val response = grpcResponse.toBuscarResponse()

        return HttpResponse.ok(response)
    }

    @Get
    fun listarChavePix(@PathVariable clientId:String): HttpResponse<Any> {

        val request = BuscarListaChaveRequest.newBuilder().setCodigoInternoCliente(clientId).build()

        val grpcResponse = listarChaveGrpc.buscarListaDeChave(request)

        val response = grpcResponse.listaChaveResponse()

        return HttpResponse.ok(response)
    }

}

private fun BuscarListaChaveRequestResponse.listaChaveResponse(): ListaChaveResponse {
    val listaChaveResponse =  ListaChaveResponse(this.clienteId, mutableSetOf())

    this.listaChaveList.map {chavePix ->

        listaChaveResponse.chaveRequestLista.add(ChaveRequest(chavePix))
    }

    return listaChaveResponse
}

private fun BuscarChavePixResponse.toBuscarResponse(): BuscarChaveResponse {

    return BuscarChaveResponse(nome, agencia, cpf, createdAt, nomeBanco, numeroConta, tipoDaChave, tipoDaConta, valorDaChave)
}

