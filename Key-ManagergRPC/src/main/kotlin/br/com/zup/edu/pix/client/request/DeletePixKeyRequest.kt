package br.com.zup.edu.pix.client.request

import br.com.zup.edu.pix.conta.Conta

data class DeletePixKeyRequest(
    val key: String,
    val participant: String = Conta.ISBP,
) {

}
