package br.com.zup.edu.pix.remove

import br.com.zup.edu.pix.cadastra.ChavePix
import br.com.zup.edu.pix.cadastra.ChavePixReposytory
import br.com.zup.edu.pix.client.ClientBCB
import br.com.zup.edu.pix.client.request.DeletePixKeyRequest
import br.com.zup.edu.pix.exceptions.ChavePixNaoEncontradaException
import io.micronaut.http.HttpStatus
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
@Validated // habilita o transactional
@Singleton
class RemoveChaveService(@Inject val repository: ChavePixReposytory,
                         @Inject val clientBCB: ClientBCB) {

    val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun remove(
        clienteId: String?,
        pixId: String?,
    ) {

        var id: Long? = pixId?.toLong()

        var cliId = UUID.fromString(clienteId)

        val chave: ChavePix? = repository.findByIdAndClienteId(id, cliId)
            .orElseThrow { ChavePixNaoEncontradaException("Chave Pix ${id} com cliente" +
                    " ${clienteId} não encontrada ") }
        val deletePixKeyRequest = DeletePixKeyRequest(chave!!.chave)
        val deletePixKeyResponse = clientBCB.delete(key = chave!!.chave, request = deletePixKeyRequest).also {
            logger.info("Tentativa de remoção da chave pix ${chave!!.chave} do banco " +
                    "central do brasil com status ${it.status.name} e com " +
                    "request ${deletePixKeyRequest}")
        }
        if (deletePixKeyResponse.status != HttpStatus.OK){
            throw IllegalStateException("Erro ao deletar a chave pix do banco central. chave ${chave?.chave} " +
                    "e request ${deletePixKeyRequest}")
        }
        repository.deleteById(chave?.id!!)
    }

}
