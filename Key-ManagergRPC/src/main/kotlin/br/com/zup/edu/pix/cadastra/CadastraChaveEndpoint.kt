package br.com.zup.edu.pix.cadastra

import br.com.edu.KeyManagerRegistraGRPCServiceGrpc
import br.com.edu.RegistraChavePixRequest
import br.com.edu.RegistraChavePixResponse
import br.com.zup.edu.pix.funcoesGRPC.paraChavePix
import io.grpc.stub.StreamObserver
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CadastraChaveEndpoint(@Inject val service: NovaChavePixService):
    KeyManagerRegistraGRPCServiceGrpc.KeyManagerRegistraGRPCServiceImplBase() {

    val logger = LoggerFactory.getLogger(this::class.java)

    override fun registra(
        request: RegistraChavePixRequest?,
        responseObserver: StreamObserver<RegistraChavePixResponse>?
    ) {
        logger.info("----Dados da request ${request?.clienteId}----")
        println("---dados request ${request}---")
        val novaChave: NovaChavePix? = request?.paraChavePix()
        val chaveCriada = service.cadastra(novaChave)

        val response = RegistraChavePixResponse.newBuilder()
            .setPixId(chaveCriada.id.toString())
            .setClienteId(chaveCriada.clienteId.toString())
            .build()

        responseObserver!!.onNext(response)
        responseObserver.onCompleted()
    }
}