package br.com.zup.osmarjunior.controller.requests

import br.com.zup.osmarjunior.ChavePixRequest
import br.com.zup.osmarjunior.TipoDeChave
import br.com.zup.osmarjunior.TipoDeConta
import br.com.zup.osmarjunior.annotations.ValidPixKey
import br.com.zup.osmarjunior.annotations.ValidUUID
import br.com.zup.osmarjunior.model.enums.TipoChavePix
import br.com.zup.osmarjunior.model.enums.TipoConta
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@ValidPixKey
@Introspected
data class NovoPixRequest(

    @field:NotBlank
    @field:ValidUUID
    val identificadorCliente: String,

    @field:NotNull
    @field:Enumerated(EnumType.STRING)
    val tipoDeChave: TipoChavePix,

    val chave: String?,

    @field:NotNull
    @field:Enumerated(EnumType.STRING)
    val tipoDeConta: TipoConta
) {
    fun toChavePixGrpcRequest(): ChavePixRequest? {
        return ChavePixRequest
            .newBuilder()
            .setIdentificadorCliente(this.identificadorCliente)
            .setTipoDeChave(TipoDeChave.valueOf(this.tipoDeChave.name))
            .setChave(this.chave ?: UUID.randomUUID().toString())
            .setTipoDeConta(TipoDeConta.valueOf(this.tipoDeConta.name))
            .build()
    }
}