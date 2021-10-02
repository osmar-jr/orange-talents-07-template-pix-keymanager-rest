package br.com.zup.osmarjunior.grpcclients

import br.com.zup.osmarjunior.KeyManagerConsultaServiceGrpc
import br.com.zup.osmarjunior.KeyManagerGrpcServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import jakarta.inject.Singleton

@Factory
class GrpcClientFactory(@GrpcChannel("kmgrpc") val channel: ManagedChannel) {

    @Singleton
    fun registraPixBlockingStub() = KeyManagerGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun consultaPixBlockingStub() = KeyManagerConsultaServiceGrpc.newBlockingStub(channel)
}