package br.com.zup.edu.pix.cadastra


import br.com.edu.TipoDeChave
import br.com.edu.TipoDeConta
import br.com.zup.edu.pix.conta.Conta
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
@Entity
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
    @field:ManyToOne(cascade = [CascadeType.ALL])
    val conta: Conta
) {
    @Id
    @GeneratedValue
    var id: Long? = null

    var criadoEm: LocalDateTime? = null



}