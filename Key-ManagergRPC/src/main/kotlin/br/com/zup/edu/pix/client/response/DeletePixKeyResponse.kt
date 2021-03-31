package br.com.zup.edu.pix.client.response

import java.time.LocalDateTime

data class DeletePixKeyResponse(
    val key: String,
    val participant: String,
 //   val deleteAt: LocalDateTime,
    val deletedAt: LocalDateTime = LocalDateTime.now()
) {

}
