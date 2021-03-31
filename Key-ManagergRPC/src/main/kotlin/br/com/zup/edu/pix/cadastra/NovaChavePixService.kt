package br.com.zup.edu.pix.cadastra

import br.com.zup.edu.pix.client.ClientBCB
import br.com.zup.edu.pix.client.ClientITAU
import br.com.zup.edu.pix.client.CreatePixKeyRequest
import br.com.zup.edu.pix.client.response.CreatePixKeyResponse
import br.com.zup.edu.pix.conta.response.ContaResponse
import br.com.zup.edu.pix.exceptions.ChavePixExistenteException
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Singleton
class NovaChavePixService(
    @Inject val repository: ChavePixReposytory,
    @Inject val itauClient: ClientITAU,
    @Inject val bcbCliente: ClientBCB
) {
    @Transactional
    fun cadastra(@Valid novaChave: NovaChavePix?): ChavePix {      //   da request passa para entidade para salvar

        val logger = LoggerFactory.getLogger(this::class.java)
        // 1. Verifica se a chave esta no sistema

        if (repository.existsByChave(novaChave!!.chave)) {
            throw ChavePixExistenteException("Chave Pix '${novaChave.chave}' existente")
        }

        // 2. busca dados da conta do ERP do ITAU
        val response: ContaResponse = itauClient
            .buscaContaPorTipo(novaChave.clienteId!!, novaChave.tipoDeConta!!.name)
        logger.info("response --> ${response}")
        // val conta = response.body()?.toModel() ?: throw IllegalStateException("Cliente não encontrado no Itau")

        val conta = response.paraConta()
        // 3. grava no banco os dados
        val chave = novaChave.toModel(conta)
        logger.info("salvando a chave $chave !")
        repository.save(chave)

        // 4 . registra chave no bcb
        val pixKeyRequest = CreatePixKeyRequest.paraCreatePixKeyRequest(chave)
        logger.info("transformando chave pix na bcb ${pixKeyRequest}")

        val pixKeyResponse: HttpResponse<CreatePixKeyResponse> = bcbCliente.create(pixKeyRequest).also {
            logger.info("Registro no bcb realizado com status ${it.status.name} " +
                    "e com a chave ${pixKeyRequest}")
        }
        if (pixKeyResponse.status != HttpStatus.CREATED) {
            throw IllegalStateException("Erro ao registrar ao registrar chave no " +
                    "Banco Central do Brasil")
        }

        chave.atualiza(pixKeyResponse.body()!!.key)
        chave.criadoEm = pixKeyResponse.body().createdAt
        logger.info("valor key ${pixKeyResponse.body()!!.key}")
        repository.update(chave)
        logger.info("Atualizando chave $chave")

        return chave


    }
}
