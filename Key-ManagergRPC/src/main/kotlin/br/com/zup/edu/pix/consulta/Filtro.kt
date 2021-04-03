package br.com.zup.edu.pix.consulta

import br.com.zup.edu.pix.cadastra.ChavePixReposytory
import br.com.zup.edu.pix.client.ClientBCB
import br.com.zup.edu.pix.exceptions.ChavePixExistenteException
import br.com.zup.edu.pix.exceptions.ChavePixNaoEncontradaException
import io.micronaut.core.annotation.Introspected
import io.micronaut.http.HttpStatus
import org.slf4j.LoggerFactory
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Introspected
sealed class Filtro {

    /**
     * Deve retornar a chave encontrada ao lançar um exeçao de erro chave nao encontrada.
     */
    abstract fun filtra(reposytory: ChavePixReposytory, clientBCB: ClientBCB) : ChavePixResponse

    @Introspected
    data class PorPixId(
        val clienteId: String,
        val pixId: Long,
    ) : Filtro(){
        fun pixIdLong() = pixId
        fun clienteIdUuid() = UUID.fromString(clienteId)
        override fun filtra(reposytory: ChavePixReposytory, clientBCB: ClientBCB): ChavePixResponse {
            return reposytory.findById(pixIdLong())
                .map { ChavePixResponse.para(it) }
                .orElseThrow { ChavePixNaoEncontradaException("Chave Pix Não Encontrada") }
        }
    }

    @Introspected
    data class PorChave(
        @field:NotBlank @Size(max = 77) val chave: String
    ) : Filtro(){

        val logger = LoggerFactory.getLogger(this::class.java)

        override fun filtra(reposytory: ChavePixReposytory, clientBCB: ClientBCB): ChavePixResponse {
            val chavePix: ChavePixResponse =  reposytory.findByChave(chave)
                .map { ChavePixResponse.para(it) }
                .orElseGet {
                    logger.info("Chave $chave não encontrada iniciando busca no bcb")
                    val resposta = clientBCB.consultar(chave)
                    when (resposta.status){
                        HttpStatus.OK -> resposta.body().toModel()
                        else -> throw ChavePixExistenteException("Chave pix = $chave nao encontrada")
                    }
                }
            return chavePix
        }

    }

    @Introspected
    class Invalido() : Filtro(){
        override fun filtra(reposytory: ChavePixReposytory, clientBCB: ClientBCB): ChavePixResponse {
            TODO("Not yet implemented")
        }

    }
}
