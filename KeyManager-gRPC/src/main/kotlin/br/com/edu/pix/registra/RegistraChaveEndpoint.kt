package br.com.edu.pix.registra

import br.com.edu.KeyManagerRegistraGRPCServiceGrpc
import br.com.edu.RegistraChavePixRequest
import br.com.edu.RegistraChavePixResponse
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegistraChaveEndpoint (@Inject private val service: NovaChavePixService,) : KeyManagerRegistraGRPCServiceGrpc.KeyManagerRegistraGRPCServiceImplBase() {

    override fun registra(
        request: RegistraChavePixRequest,
        responseObserver: StreamObserver<RegistraChavePixResponse>?
    ) {
        val novaChave =  request?.toModel()
        val chaveCriada = service.registra(novaChave)

        responseObserver?.onNext(RegistraChavePixResponse.newBuilder()
            .setClienteId(chaveCriada.clienteId.toString())
            .setPixId(chaveCriada.id.toString())
            .build())
        responseObserver?.onCompleted()
    }
}