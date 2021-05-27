package br.com.zup.chave

import br.com.zup.*
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Post
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import java.net.URI
import javax.validation.Valid


@Controller("/chaves/deletar")
@Validated
class DeletarChaveController(val deletarGrpc: DeletarChaveServiceGrpc.DeletarChaveServiceBlockingStub) {

    @Delete
    fun deletarChavePix(deletarChaveRequest : DeletaChaveRequest): HttpResponse<Any> {

        val request = deletarChaveRequest.toDeletaRequest()

        val grpcResponse = deletarGrpc.deletarChave(request)

       val response = DeletaChaveResponse(grpcResponse.chave, grpcResponse.participant)

        return HttpResponse.ok(response)
    }

}

