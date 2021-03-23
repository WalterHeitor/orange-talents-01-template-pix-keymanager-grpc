package br.com.zup.edu.pix.conta

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
@Entity
class Titular(

    @Id
    val id: String?,
    val nome: String?,
    val cpf: String?
) {

}