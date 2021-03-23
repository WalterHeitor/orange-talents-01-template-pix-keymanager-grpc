package br.com.zup.edu.pix.cadastra

import br.com.zup.edu.pix.client.ClientITAU
import br.com.zup.edu.pix.conta.response.ContaResponse
import br.com.zup.edu.pix.exceptions.ChavePixExistenteException
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Singleton
class NovaChavePixService(
    @Inject val repository: ChavePixReposytory,
    @Inject val itauClient: ClientITAU,
) {
    @Transactional
    fun cadastra(@Valid novaChave: NovaChavePix?): ChavePix {      //   da request passa para entidade para salvar
        // 1. Verifica se a chave esta no sistema

        if (repository.existsByChave(novaChave!!.chave)){
            throw ChavePixExistenteException("Chave Pix '${novaChave?.chave}' existente")
        }

        // 2. busca dados da conta do ERP do ITAU
        val response: ContaResponse = itauClient
            .buscaContaPorTipo(novaChave!!.clienteId!!, novaChave.tipoDeConta!!.name)

       // val conta = response.body()?.toModel() ?: throw IllegalStateException("Cliente não encontrado no Itau")

        val conta = response.paraConta()
        // 3. grava no banco os dados
        val chave = novaChave.toModel(conta)
        repository.save(chave)
        return chave


    }
}
