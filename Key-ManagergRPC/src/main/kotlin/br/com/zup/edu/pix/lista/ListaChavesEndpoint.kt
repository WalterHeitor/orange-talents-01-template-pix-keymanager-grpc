package br.com.zup.edu.pix.lista

import br.com.edu.KeyManagerListaGRPCServiceGrpc
import br.com.edu.ListaChavePixRequest
import br.com.edu.ListaChavePixResponse
import br.com.edu.TipoDeChave
import br.com.zup.edu.pix.cadastra.ChavePixReposytory
import com.google.protobuf.Timestamp
import io.grpc.stub.StreamObserver
import org.slf4j.LoggerFactory
import java.time.ZoneId
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListaChavesEndpoint (@Inject private val reposytory: ChavePixReposytory) : KeyManagerListaGRPCServiceGrpc
            .KeyManagerListaGRPCServiceImplBase(){
    val logger = LoggerFactory.getLogger(this::class.java)

    override fun lista(request: ListaChavePixRequest?,
                       responseObserver: StreamObserver<ListaChavePixResponse>?) {

        if (request!!.clienteId.isNullOrBlank())
            throw IllegalArgumentException("Cliente Id nao pode ser vazio ou nulo")

        val clienteId: UUID = UUID.fromString(request.clienteId)
        val chaves = reposytory.findAllByClienteId(clienteId)
            .map {
                ListaChavePixResponse.ChavePix.newBuilder()
                    .setPixId(it.id.toString())
                    .setTipo(TipoDeChave.valueOf(it.tipo.name))
                    .setChave(it.chave)
                    .setCriadaEm(it.criadoEm.let { it ->
                        val createAt = it!!.atZone(ZoneId.of("UTC")).toInstant()
                        Timestamp.newBuilder()
                            .setSeconds(createAt.epochSecond)
                            .setNanos(createAt.nano)
                            .build()
                    })
                    .build()
            }
        responseObserver!!.onNext(ListaChavePixResponse.newBuilder()
            .setClienteId(clienteId.toString())
            .addAllChaves(chaves)
            .build())
        responseObserver.onCompleted()
    }

}