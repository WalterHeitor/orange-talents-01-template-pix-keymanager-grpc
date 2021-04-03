package br.com.zup.edu.pix.client.response


import br.com.edu.TipoDeChave
import br.com.edu.pix.conta.Instituicao
import br.com.zup.edu.pix.client.BankAccount
import br.com.zup.edu.pix.client.KeyType
import br.com.zup.edu.pix.client.Owner
import br.com.zup.edu.pix.consulta.ChavePixResponse
import br.com.zup.edu.pix.conta.Conta
import br.com.zup.edu.pix.conta.TipoConta
import br.com.zup.edu.pix.conta.Titular
import br.com.zup.edu.pix.instituicoes.Instituicoes
import br.com.zup.edu.pix.validacao.TipoDaChave
import java.time.LocalDateTime

data class PixKeyDetalhesResponse(
    val keyTipe: KeyType,
    val key: String,
    val bankAccount: BankAccount,
    val owner: Owner,
    val createdAt: LocalDateTime
) {
    fun toModel(): ChavePixResponse? {
        return ChavePixResponse(
            chave = key,
            conta = Conta(
                agencia = bankAccount.branch,
                numero = bankAccount.accountNumber,
                instituicao = Instituicoes.INSTITUICOES[bankAccount.participant]?.let {
                    Instituicao(nome = Instituicoes.INSTITUICOES.get(bankAccount.participant),
                        ispb = it)
                },

                titular = Titular(
                    nome = owner.name,
                    cpf = owner.taxIdNumber,
                ),
                tipo = bankAccount.let { TipoConta.valueOf(it.accountType.name) },
            ),
            registradaEm = createdAt,
            tipo = keyTipe.let { TipoDeChave.valueOf(it.name) },
        )
    }



}