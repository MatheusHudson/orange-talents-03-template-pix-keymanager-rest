package br.com.zup.chave

import br.com.zup.RegistrarChaveServiceGrpc
import br.com.zup.RegistroChaveResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import java.net.URI
import javax.validation.Valid


@Controller("/chaves")
@Validated
class RegistrarChaveController(val registrarGrpc: RegistrarChaveServiceGrpc.RegistrarChaveServiceBlockingStub) {

    @Post
    fun cadastrarChavePix(@Body @Valid requestCadastrarChave: RequestCadastrarChave): MutableHttpResponse<ResponseRegistrarChave>? {

        requestCadastrarChave.validaRequest()

        val requestGrpc = requestCadastrarChave.toRequestGrpc()

        val grpcResponse : RegistroChaveResponse = registrarGrpc.registrarChave(requestGrpc)

        val uri: URI = UriBuilder.of("chaves/${grpcResponse.chave}").build()

        val response = grpcResponse.toDtoResponse()


        return HttpResponse.created(response, uri)
    }

}

private fun RegistroChaveResponse.toDtoResponse(): ResponseRegistrarChave {

  return  ResponseRegistrarChave(pixId, chave, tipoChave)
}
