package br.com.zup.edu.pix.client.response

import br.com.zup.edu.pix.client.BankAccount
import br.com.zup.edu.pix.client.KeyType
import br.com.zup.edu.pix.client.Owner
import java.time.LocalDateTime

data class PixKeyDetalhesResponse(
    val keyTipe: KeyType,
    val key: String,
    val bankAccount: BankAccount,
    val owner: Owner,
    val createdAt: LocalDateTime
) {

}