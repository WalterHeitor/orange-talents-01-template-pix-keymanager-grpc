package br.com.zup.edu.pix.cadastra


import br.com.edu.TipoDeChave
import br.com.edu.TipoDeConta
import br.com.zup.edu.pix.conta.Conta
import java.util.*
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class ChavePix(
    @field:NotNull
    @Column(nullable = false)
    val clienteId: UUID,
    @field:NotNull
    @Column(nullable = false)
    val tipo: TipoDeChave,
    @field:NotBlank
    @Column(unique = true, nullable = false)
    var chave: String,
    @field:NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val tipoDeConta: TipoDeConta,
    @field:Valid
    @Embedded
    val conta: Conta
) {
}