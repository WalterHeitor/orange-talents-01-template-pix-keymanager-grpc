package br.com.zup.edu.pix.consulta

import br.com.edu.TipoDeChave
import br.com.zup.edu.pix.cadastra.ChavePix
import br.com.zup.edu.pix.conta.Conta
import br.com.zup.edu.pix.validacao.TipoDaChave
import java.time.LocalDateTime
import java.util.*

data class ChavePixResponse(
    val idCliente: UUID? = null,
    val idPix: Long? = null,
    val tipo: br.com.edu.TipoDeChave,
    val chave: String?,
    val conta: Conta,
    val registradaEm: LocalDateTime?

        ){
    companion object{
        fun para(chave: ChavePix) : ChavePixResponse{
            return ChavePixResponse(
                idCliente = chave.clienteId,
                idPix = chave.id,
                tipo = chave.tipo,
                chave = chave.chave,
                conta = chave.conta,
                registradaEm = chave.criadoEm
            )
        }


    }

}
