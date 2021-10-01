package br.com.zup.osmarjunior.controller.requests

import br.com.zup.osmarjunior.model.ChavePix
import br.com.zup.osmarjunior.model.enums.TipoChavePix
import com.fasterxml.jackson.annotation.JsonFormat
import io.micronaut.core.annotation.Introspected
import java.time.LocalDateTime
import java.util.*
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Introspected
data class ChaveDetalheReponse(
    @field:NotNull
    val clienteId: UUID,

    @field:NotBlank
    val chave: String,

    @field:Enumerated(EnumType.STRING)
    val tipoDeChave: TipoChavePix,

    @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    val criadoEm: LocalDateTime
) {

    constructor(@Valid chavePix: ChavePix) : this(
        clienteId = chavePix.clienteIdGrpc,
        chave = chavePix.chave,
        tipoDeChave = chavePix.tipoChave,
        criadoEm = chavePix.criadoEm
    )
}