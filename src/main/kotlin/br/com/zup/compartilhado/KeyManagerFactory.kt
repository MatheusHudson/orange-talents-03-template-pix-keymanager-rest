package br.com.zup.compartilhado

import br.com.zup.BuscarChavePixGrpc
import br.com.zup.BuscarListaDeChavePixGrpc
import br.com.zup.DeletarChaveServiceGrpc
import br.com.zup.RegistrarChaveServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class KeyManagerFactory(@GrpcChannel ("keyManager") val channel: ManagedChannel) {

    @Singleton
    fun registrarChave() = RegistrarChaveServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun deletarChave() = DeletarChaveServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun buscarChave() = BuscarChavePixGrpc.newBlockingStub(channel)

    @Singleton
    fun buscarListaChaves() = BuscarListaDeChavePixGrpc.newBlockingStub(channel)


}