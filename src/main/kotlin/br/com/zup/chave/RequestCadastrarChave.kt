package br.com.zup.chave

import br.com.zup.RegistrarChaveRequest
import br.com.zup.TipoDeConta
import br.com.zup.chave.enum.TipoDaChave
import br.com.zup.compartilhado.exception.ApiException
import io.micronaut.core.annotation.Introspected
import io.micronaut.http.HttpResponse
import javax.validation.constraints.NotBlank

@Introspected
data class RequestCadastrarChave(
    @field:NotBlank val idCliente: String,
    @field:NotBlank(message = "Valor do campo não pode ser nulo!") val tipoDeChave: String,
    val valorDaChave: String?,
    @field:NotBlank val tipoDeConta: String
) {
    fun toRequestGrpc(): RegistrarChaveRequest {
        return RegistrarChaveRequest.newBuilder()
            .setTipoConta(tipoDeConta)
            .setValorChave(valorDaChave ?: "")
            .setIdCliente(idCliente)
            .setTipoChave(tipoDeChave)
            .build()
    }

    fun validaRequest() {
        try {
            TipoDaChave.valueOf(tipoDeChave)
        } catch (e: IllegalArgumentException) {
            throw ApiException("Dados invalidos! Valores aceitos: ${TipoDaChave.todasChavesString}",
            HttpResponse.badRequest())
        }

        try {
            TipoDeConta.valueOf(tipoDeConta)
        } catch (e: IllegalArgumentException) {
            throw ApiException("Dados invalidos! Valores aceitos: ${
                TipoDeConta.values().map { tipoDeConta -> tipoDeConta.name }
            }".substringBeforeLast(", UNRECOGNIZED") + "]",
                HttpResponse.badRequest())
        }

       TipoDaChave.valueOf(tipoDeChave).valida(valorDaChave ?: "").run {
           if(!first) {
               throw ApiException(second, HttpResponse.badRequest())
           }
       }
    }
}