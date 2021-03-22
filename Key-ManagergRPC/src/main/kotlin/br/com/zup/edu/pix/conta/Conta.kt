package br.com.zup.edu.pix.conta

import br.com.edu.pix.conta.Instituicao
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
class Conta (
    @field:NotBlank
    @Column
    var agencia : String?,
    @field:NotBlank
    @Column
    var numero : String?,
    @field:ManyToOne
    var instituicao: Instituicao?,
    @field:ManyToOne
    var titular: Titular,
    @field:Enumerated(EnumType.STRING)
    var tipo: TipoConta?
        ){

    @Id
    @GeneratedValue
    var id: Long? = null

}
