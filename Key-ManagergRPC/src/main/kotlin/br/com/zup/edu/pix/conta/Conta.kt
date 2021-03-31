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
    @field:ManyToOne(cascade = [CascadeType.ALL])
    var instituicao: Instituicao?,
//    @field:ManyToOne(cascade = [CascadeType.ALL])
    @field:Embedded
    var titular: Titular?,
    @field:Enumerated(EnumType.STRING)
    var tipo: TipoConta?
        ){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object{
        const val ISBP = "60701190"
    }

}
