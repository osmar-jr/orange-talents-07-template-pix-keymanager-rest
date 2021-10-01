package br.com.zup.osmarjunior.controller.requests

import br.com.zup.osmarjunior.ChavePixRequest
import br.com.zup.osmarjunior.TipoDeChave
import br.com.zup.osmarjunior.TipoDeConta
import br.com.zup.osmarjunior.annotations.ValidPixKey
import br.com.zup.osmarjunior.model.enums.TipoChavePix
import br.com.zup.osmarjunior.model.enums.TipoConta
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.constraints.NotNull

@ValidPixKey
@Introspected
data class NovoPixRequest(

    @field:NotNull
    @field:Enumerated(EnumType.STRING)
    val tipoDeChave: TipoChavePix,

    val chave: String?,

    @field:NotNull
    @field:Enumerated(EnumType.STRING)
    val tipoDeConta: TipoConta
) {
    fun toChavePixGrpcRequest(identificadorCliente: UUID): ChavePixRequest? {
        return ChavePixRequest
            .newBuilder()
            .setIdentificadorCliente(identificadorCliente.toString())
            .setTipoDeChave(tipoDeChave.grpcTipoChave ?: TipoDeChave.UNKNOWN_KEY)
            .setChave(this.chave ?: UUID.randomUUID().toString())
            .setTipoDeConta(tipoDeConta.grpcTipoDeConta ?: TipoDeConta.UNKNOWN_CONTA)
            .build()
    }
}