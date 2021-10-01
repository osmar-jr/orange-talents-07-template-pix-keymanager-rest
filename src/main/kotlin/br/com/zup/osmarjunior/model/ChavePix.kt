package br.com.zup.osmarjunior.model

import br.com.zup.osmarjunior.model.enums.TipoChavePix
import br.com.zup.osmarjunior.model.enums.TipoConta
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class ChavePix(

    @field:NotBlank
    val clienteIdGrpc: UUID,

    @field:NotBlank
    val pixIdGrpc: UUID,

    @field:NotNull
    @field:Enumerated(EnumType.STRING)
    val tipoChave: TipoChavePix,

    @field:NotBlank
    val chave: String,

    @field:NotNull
    @field:Enumerated(EnumType.STRING)
    val tipoConta: TipoConta,

    @field:Valid
    @Embedded
    val conta: ContaAssociada,

    @field:NotNull
    @Column(nullable = false, updatable = false)
    val criadoEm: LocalDateTime
)
