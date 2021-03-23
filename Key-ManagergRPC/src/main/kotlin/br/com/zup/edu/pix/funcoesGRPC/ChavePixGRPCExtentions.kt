package br.com.zup.edu.pix.funcoesGRPC

import br.com.edu.RegistraChavePixRequest
import br.com.edu.TipoDeChave


import br.com.edu.TipoDeConta
import br.com.zup.edu.pix.cadastra.NovaChavePix
import br.com.zup.edu.pix.validacao.TipoDaChave


fun RegistraChavePixRequest.paraChavePix(): NovaChavePix {
    return NovaChavePix(
        clienteId = clienteId,
        tipo = when (tipoDeChave) {
                                  TipoDeChave.UNKNOWN_TIPO_CHAVE -> null
            else -> TipoDaChave.valueOf(tipoDeChave.name)
        },
        chave = chave,
        tipoDeConta = when (tipoDeConta) {
            TipoDeConta.UNKNOWN_TIPO_CONTA -> null
            else -> TipoDeConta.valueOf(tipoDeConta.name)
        }
    )
}