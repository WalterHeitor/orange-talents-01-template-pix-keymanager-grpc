package br.com.edu.pix.conta

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
class Instituicao(
    @field:NotBlank
    val nome: String,
    @field:NotBlank
    val ispb: String,

) {
    @Id
    @GeneratedValue
    var id: Long? = null

}
