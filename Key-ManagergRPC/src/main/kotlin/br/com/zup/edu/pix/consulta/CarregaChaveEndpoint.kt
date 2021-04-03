package br.com.zup.edu.pix.consulta

import br.com.edu.CarregaChavePixRequest
import br.com.edu.CarregaChavePixResponse
import br.com.edu.KeyManagerCarregaGRPCServiceGrpc
import br.com.zup.edu.pix.cadastra.ChavePixReposytory
import br.com.zup.edu.pix.client.ClientBCB
import br.com.zup.edu.pix.funcoesGRPC.toModel
import io.grpc.stub.StreamObserver
import io.micronaut.validation.validator.Validator

import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton
//import javax.validation.Validator

@Singleton
class CarregaChaveEndpoint (
    @Inject val reposytory: ChavePixReposytory,
    @Inject val clientBCB: ClientBCB,
    @Inject val validator: Validator,  // javax.validation.Validador
        ) : KeyManagerCarregaGRPCServiceGrpc.KeyManagerCarregaGRPCServiceImplBase(){

    val logger = LoggerFactory.getLogger(this::class.java)

    override fun carrega(request: CarregaChavePixRequest?, responseObserver: StreamObserver<CarregaChavePixResponse>?) {

        logger.info("carregando $request ")
        val filtro: Filtro = request!!.toModel(validator)
        val chaveInfo = filtro.filtra(reposytory = reposytory , clientBCB = clientBCB)

        responseObserver?.onNext(CarregaChavePixResponseConverter().converter(chaveInfo))
        responseObserver?.onCompleted()
    }
}