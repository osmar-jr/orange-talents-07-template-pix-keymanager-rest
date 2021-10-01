package br.com.zup.osmarjunior.service

import br.com.zup.osmarjunior.ConsultaChavePixResponse
import br.com.zup.osmarjunior.KeyManagerConsultaServiceGrpc
import br.com.zup.osmarjunior.KeyManagerGrpcServiceGrpc
import br.com.zup.osmarjunior.utils.toConsultaChaveGrpcRequest
import br.com.zup.osmarjunior.controller.requests.NovoPixRequest
import br.com.zup.osmarjunior.model.ChavePix
import br.com.zup.osmarjunior.utils.toChavePix
import io.micronaut.validation.Validated
import jakarta.inject.Inject
import jakarta.inject.Singleton
import javax.validation.Valid

@Validated
@Singleton
class RegistraPixService(
    @Inject
    val grpcRegistraClient: KeyManagerGrpcServiceGrpc.KeyManagerGrpcServiceBlockingStub,

    @Inject
    val grpcConsultaClient: KeyManagerConsultaServiceGrpc.KeyManagerConsultaServiceBlockingStub
) {

    fun registra(@Valid novoPixRequest: NovoPixRequest): ChavePix {

        val pixGrpcResponse = grpcRegistraClient.cadastraChavePix(novoPixRequest.toChavePixGrpcRequest())
        val pixCriadoGrpcResponse: ConsultaChavePixResponse = grpcConsultaClient.consultar(pixGrpcResponse.toConsultaChaveGrpcRequest())

        return pixCriadoGrpcResponse.toChavePix()
    }

}
