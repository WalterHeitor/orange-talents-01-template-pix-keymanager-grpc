package br.com.edu.pix.registra

import br.com.edu.RegistraChavePixRequest
import br.com.edu.TipoDeChave
import br.com.edu.TipoDeConta


fun RegistraChavePixRequest.toModel(): NovaChavePix{
    return NovaChavePix(
        clienteId = clienteId,
        tipo = when (tipoDeChave) {
            TipoDeChave.UNKNOWN_TIPO_CHAVE -> null
            else -> TipoDeChave.valueOf(tipoDeChave.name)
        },
        chave = chave,
        tipoDeConta = when (tipoDeConta) {
            TipoDeConta.UNKNOWN_TIPO_CONTA -> null
            else -> TipoDeConta.valueOf(tipoDeConta.name)
        }
    )
}