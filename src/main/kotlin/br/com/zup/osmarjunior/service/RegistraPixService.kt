package br.com.zup.osmarjunior.service

import br.com.zup.osmarjunior.KeyManagerConsultaServiceGrpc
import br.com.zup.osmarjunior.KeyManagerGrpcServiceGrpc
import br.com.zup.osmarjunior.controller.requests.NovoPixRequest
import br.com.zup.osmarjunior.model.ChavePix
import br.com.zup.osmarjunior.utils.toChavePix
import br.com.zup.osmarjunior.utils.toConsultaChaveGrpcRequest
import io.micronaut.validation.Validated
import jakarta.inject.Inject
import jakarta.inject.Singleton
import java.util.*
import javax.validation.Valid
import javax.validation.constraints.NotNull

@Validated
@Singleton
class RegistraPixService(
    @Inject
    val grpcRegistraClient: KeyManagerGrpcServiceGrpc.KeyManagerGrpcServiceBlockingStub,

    @Inject
    val grpcConsultaClient: KeyManagerConsultaServiceGrpc.KeyManagerConsultaServiceBlockingStub
) {

    fun registra(@Valid novoPixRequest: NovoPixRequest, @NotNull clienteId: UUID): ChavePix {

        val pixGrpcResponse = grpcRegistraClient
            .cadastraChavePix(novoPixRequest.toChavePixGrpcRequest(clienteId))

        val pixCriadoGrpcResponse = grpcConsultaClient
            .consultar(pixGrpcResponse.toConsultaChaveGrpcRequest())

        return pixCriadoGrpcResponse.toChavePix()
    }

}
