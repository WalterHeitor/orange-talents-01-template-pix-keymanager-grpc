package br.com.edu.pix.conta

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
class Instituicao(
    @field:NotBlank
    val nome: String?,
    @field:NotBlank
    val ispb: String,

) {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)    /* nao foi acceito na insttuicao (strategy = GenerationType.IDENTITY)*/
    var id: Long? = null

}
