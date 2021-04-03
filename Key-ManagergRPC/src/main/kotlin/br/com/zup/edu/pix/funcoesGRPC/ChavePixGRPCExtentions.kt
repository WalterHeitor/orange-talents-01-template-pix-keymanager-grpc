package br.com.zup.edu.pix.funcoesGRPC

import br.com.edu.CarregaChavePixRequest
import br.com.edu.RegistraChavePixRequest
import br.com.edu.TipoDeChave


import br.com.edu.TipoDeConta
import br.com.zup.edu.pix.cadastra.NovaChavePix
import br.com.zup.edu.pix.consulta.Filtro
import br.com.zup.edu.pix.validacao.TipoDaChave
//import io.micronaut.validation.validator.Validator
import javax.validation.ConstraintViolationException
import javax.validation.Validator


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

fun CarregaChavePixRequest.toModel(validator: Validator) : Filtro {
    val filfro = when(filtroCase){
        CarregaChavePixRequest.FiltroCase.PIXID -> pixId.let {
            Filtro.PorPixId(clienteId = it.clienteId, pixId = it.pixId.toLong())
        }
        CarregaChavePixRequest.FiltroCase.CHAVE -> Filtro.PorChave(chave)
        CarregaChavePixRequest.FiltroCase.FILTRO_NOT_SET -> Filtro.Invalido()
    }
    val violations = validator.validate(filfro)
    if (violations.isNotEmpty()){ //nao esta vazia
        throw ConstraintViolationException(violations)
    }
    return filfro
}