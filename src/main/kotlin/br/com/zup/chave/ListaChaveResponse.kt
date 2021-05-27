package br.com.zup.chave

import br.com.zup.BuscarListaChaveRequestResponse

data class ListaChaveResponse(val clienteId: String,val chaveRequestLista: MutableSet<ChaveRequest> ) {
}

data class  ChaveRequestResponse(val clienteId: String)

class ChaveRequest(
    chavePix: BuscarListaChaveRequestResponse.ChavePix
) {
    val pixId: String = chavePix.pixId
    val identificadorCliente: String = chavePix.identificadorCliente
    val tipoDaChave: String = chavePix.tipoDaChave
    val valorDaChave: String = chavePix.valorDaChave
    val tipoDeConta: String = chavePix.tipoDeConta
    val createdAt: String = chavePix.createdAt
}