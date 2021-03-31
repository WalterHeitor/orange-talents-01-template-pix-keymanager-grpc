package br.com.zup.edu.pix.conta

import javax.persistence.Embeddable
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
//@Entity
@Embeddable
class Titular(

//    @Id
//    val id: String?,
    val nome: String?,
    val cpf: String?
) {

}