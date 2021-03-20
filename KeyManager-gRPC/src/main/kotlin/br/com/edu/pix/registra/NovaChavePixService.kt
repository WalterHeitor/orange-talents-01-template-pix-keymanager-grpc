package br.com.edu.pix.registra

import br.com.edu.pix.remove.ChavePix
import br.com.edu.pix.remove.ChavePixRepository
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid
@Validated
@Singleton
class NovaChavePixService(@Inject val repository: ChavePixRepository,
                          @Inject val itauClient: ContasDeClientesNoItauClient) {

    private val LOGGER = LoggerFactory.getLogger(this.javaClass)

    @Transactional
    fun registra(@Valid novaChave: NovaChavePix): ChavePix {
        // 1. verifica se a chave ja esta no sistema
        if(repository.existsByChave(novaChave.chave))
            throw ChavePixExistenteException("Chave Pix '${novaChave.chave}' existente")

        // 2. busca dados da conta do ERP do ITAU
        val response = itauClient.buscaContaPorTipo(novaChave.clienteId!!, novaChave.tipoDeConta!!.name)
        val conta = response.body()?.toModel() ?: throw IllegalStateException("Cliente não encontrado no Itau")

        // 3. grava no banco os dados
        val chave = novaChave.toModel(conta)
        repository.save(chave)

        return chave

    }
}