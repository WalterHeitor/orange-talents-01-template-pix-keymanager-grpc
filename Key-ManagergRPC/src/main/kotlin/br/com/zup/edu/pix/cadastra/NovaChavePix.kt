package br.com.zup.edu.pix.cadastra



import br.com.edu.TipoDeChave
import br.com.edu.TipoDeConta
import br.com.zup.edu.pix.client.BankAccount
import br.com.zup.edu.pix.client.CreatePixKeyRequest
import br.com.zup.edu.pix.client.KeyType
import br.com.zup.edu.pix.conta.Conta
import br.com.zup.edu.pix.validacao.TipoDaChave

import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size
//@ValidPixKey
@Introspected
data class NovaChavePix(
//    @ValidUUID
    @field:NotBlank
    val clienteId: String?,
    @field:NotNull
    val tipo: TipoDaChave?,
    @field:Size(max = 77)
    val chave: String?,
    @field:NotNull
    var tipoDeConta: TipoDeConta?,
) {
    fun toModel(conta: Conta): ChavePix {
        return ChavePix(
            clienteId = UUID.fromString(this.clienteId),
            tipo = TipoDeChave.valueOf(this.tipo!!.name),
            chave = if (this.tipo == TipoDaChave.ALEATORIA) UUID.randomUUID().toString() else this.chave!!,
            tipoDeConta = TipoDeConta.valueOf(this.tipoDeConta!!.name),
            conta = conta
        )
    }
//    fun paraCreatePixKeyRequest(conta: Conta): CreatePixKeyRequest {
//        return CreatePixKeyRequest(
//            keyType = KeyType.getKeyType(tipo!!),
//            key = chave!!,
//            bankAccount = BankAccount()
//
//        )
//    }
}