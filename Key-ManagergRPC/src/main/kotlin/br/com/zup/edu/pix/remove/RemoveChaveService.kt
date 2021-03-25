package br.com.zup.edu.pix.remove

import br.com.zup.edu.pix.cadastra.ChavePixReposytory
import br.com.zup.edu.pix.exceptions.ChavePixNaoEncontradaException
import io.micronaut.validation.Validated
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
@Validated // habilita o transactional
@Singleton
class RemoveChaveService(@Inject val repository: ChavePixReposytory, ) {

    @Transactional
    fun remove(
        clienteId: String?,
        pixId: String?,
    ) {

        var id: Long? = pixId?.toLong()

        var cliId = UUID.fromString(clienteId)

        val chave = repository.findByIdAndClienteId(id, cliId)
            .orElseThrow { ChavePixNaoEncontradaException("Chave Pix ${id} com cliente" +
                    " ${clienteId} não encontrada") }
        repository.deleteById(chave.id!!)
    }

}
