package br.com.zup.osmarjunior.utils

import br.com.zup.osmarjunior.ChavePixResponse
import br.com.zup.osmarjunior.ConsultaChavePixRequest
import br.com.zup.osmarjunior.ConsultaChavePixResponse
import br.com.zup.osmarjunior.PixId
import br.com.zup.osmarjunior.model.ChavePix
import br.com.zup.osmarjunior.model.ContaAssociada
import br.com.zup.osmarjunior.model.enums.TipoChavePix
import br.com.zup.osmarjunior.model.enums.TipoConta
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

fun ChavePixResponse.toConsultaChaveGrpcRequest(): ConsultaChavePixRequest? {
    return ConsultaChavePixRequest
        .newBuilder()
        .setPixId(
            PixId.newBuilder()
                .setPixId(this.pixId)
                .setClienteId(this.clientId)
                .build()
        )
        .build()
}

fun ConsultaChavePixResponse.toChavePix(): ChavePix {
    return ChavePix(
        clienteIdGrpc = UUID.fromString(this.clientId!!),
        pixIdGrpc = UUID.fromString(this.pixId!!),
        tipoChave = TipoChavePix.valueOf(this.chave.tipo.name),
        chave = this.chave.chave!!,
        tipoConta = TipoConta.valueOf(this.chave.conta.tipo.name),
        conta = ContaAssociada(
            instituicao = this.chave.conta.instituicao!!,
            nomeDoTitular = this.chave.conta.nomeDoTitular!!,
            cpfDoTitular = this.chave.conta.cpfDoTiTular!!,
            agencia = this.chave.conta.agencia!!,
            numeroDaConta = this.chave.conta.numeroDaConta!!
        ),
        criadoEm = LocalDateTime.ofEpochSecond(
            this.criadoEm.seconds,
            this.criadoEm.nanos,
            ZoneOffset.UTC
        )
    )
}