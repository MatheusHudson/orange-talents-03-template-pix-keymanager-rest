package br.com.zup.chave

import br.com.zup.DeletarChaveRequest

data class DeletaChaveRequest(val identificadorCliente: String, val tipoChave: String, val valorDaChave: String, val participant: String) {

    fun toDeletaRequest(): DeletarChaveRequest? {
        return DeletarChaveRequest.newBuilder()
            .setIdentificadorCliente(identificadorCliente)
            .setTipoChave(tipoChave)
            .setValorDaChave(valorDaChave)
            .setParticipant(participant)
            .build()
    }

}