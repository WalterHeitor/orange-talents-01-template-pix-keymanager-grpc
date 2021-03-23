package br.com.zup.edu.pix.remove

import br.com.edu.KeyManagerRemoveGRPCServiceGrpc
import br.com.edu.RemoveChavePixRequest
import br.com.edu.RemoveChavePixResponse
import io.grpc.stub.StreamObserver
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton
//@ErrorHandler
@Singleton
class RemoveChaveEndpoint(@Inject val service: RemoveChaveService): KeyManagerRemoveGRPCServiceGrpc
                                                            .KeyManagerRemoveGRPCServiceImplBase(){
    val logger = LoggerFactory.getLogger(this::class.java)

    override fun remove(request: RemoveChavePixRequest?,
                        responseObserver: StreamObserver<RemoveChavePixResponse>?) {

        service.remove(clienteId = request?.clienteId, pixId = request?.pixId)

        responseObserver?.onNext(RemoveChavePixResponse.newBuilder()
            .setClienteId(request?.clienteId)
            .setPixId(request?.pixId)
            .build()
        )
        responseObserver?.onCompleted()

    }
}