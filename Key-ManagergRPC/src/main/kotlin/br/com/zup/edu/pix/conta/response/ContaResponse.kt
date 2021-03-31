package br.com.zup.edu.pix.conta.response

import br.com.edu.pix.conta.Instituicao
import br.com.zup.edu.pix.conta.Conta
import br.com.zup.edu.pix.conta.TipoConta
import br.com.zup.edu.pix.conta.Titular

data class ContaResponse(
    val agencia: String,
    val numero: String,
    val tipo: TipoConta,
    val instituicao: InstituicaoResponse,
    val titular: TitularResponse
) {
    fun paraConta(): Conta {
        return Conta(
            agencia,
            numero,
            Instituicao(instituicao.nome, instituicao.ispb),
            Titular(titular.nome, titular.cpf), //titular.id,
            tipo
        )
    }
}