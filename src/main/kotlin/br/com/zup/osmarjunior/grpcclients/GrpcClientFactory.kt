package br.com.zup.osmarjunior.grpcclients

import br.com.zup.osmarjunior.KeyManagerConsultaServiceGrpc
import br.com.zup.osmarjunior.KeyManagerGrpcServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import jakarta.inject.Singleton

@Factory
class GrpcClientFactory {

    @Singleton
    fun registraPixBlockingStub(@GrpcChannel("kmgrpc") channel: ManagedChannel): KeyManagerGrpcServiceGrpc.KeyManagerGrpcServiceBlockingStub? {
        return KeyManagerGrpcServiceGrpc.newBlockingStub(channel)
    }

    @Singleton
    fun consultaPixBlockingStub(@GrpcChannel("kmgrpc") channel: ManagedChannel): KeyManagerConsultaServiceGrpc.KeyManagerConsultaServiceBlockingStub? {
        return KeyManagerConsultaServiceGrpc.newBlockingStub(channel)
    }
}