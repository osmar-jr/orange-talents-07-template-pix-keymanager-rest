package br.com.zup.osmarjunior.model

import br.com.zup.osmarjunior.model.enums.TipoChavePix
import br.com.zup.osmarjunior.model.enums.TipoConta
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(
    uniqueConstraints = [UniqueConstraint(
        name = "uk_chave_pix",
        columnNames = ["chave"]
    )]
)
class ChavePix(

    @field:NotBlank
    @Column(nullable = false)
    val clienteIdGrpc: UUID,

    @field:NotBlank
    @Column(nullable = false)
    val pixIdGrpc: UUID,

    @field:NotNull
    @field:Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val tipoChave: TipoChavePix,

    @field:NotBlank
    @Column(unique = true, nullable = false)
    val chave: String,

    @field:NotNull
    @field:Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val tipoConta: TipoConta,

    @field:Valid
    @Embedded
    val conta: ContaAssociada,

    @field:NotNull
    @Column(nullable = false, updatable = false)
    val criadoEm: LocalDateTime
) {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    var id: UUID? = null
}
