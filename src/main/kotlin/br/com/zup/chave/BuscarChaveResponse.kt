package br.com.zup.chave

data class BuscarChaveResponse(
    val nome: String,
    val agencia: String,
    val cpf: String,
    val createdAt: String,
    val nomeBanco: String,
    val numeroConta: String,
    val tipoDaChave: String,
    val tipoDaConta: String,
    val valorDaChave: String
)