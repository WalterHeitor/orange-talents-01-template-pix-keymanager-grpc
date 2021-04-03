package br.com.zup.edu.pix.consulta

import br.com.edu.CarregaChavePixResponse
import br.com.edu.TipoDeChave
import br.com.edu.TipoDeConta
import com.google.protobuf.Timestamp
import java.time.ZoneId

class CarregaChavePixResponseConverter {
    fun converter(chaveInfo: ChavePixResponse) : CarregaChavePixResponse{

        return CarregaChavePixResponse
            .newBuilder()
            .setClienteId(chaveInfo.idCliente?.toString() ?: "")    //protobuf usa "" como default value para String
            .setPixId(chaveInfo.idPix?.toString() ?: "")

            .setChave(CarregaChavePixResponse.ChavePix
                .newBuilder()
                .setTipo(TipoDeChave.valueOf(chaveInfo.tipo.name))
                .setChave(chaveInfo.chave)

                .setConta(CarregaChavePixResponse.ChavePix.ContaInfo
                    .newBuilder()
                    .setTipo(TipoDeConta.valueOf(chaveInfo.conta.tipo!!.name))
                    .setInstituicao(chaveInfo.conta.instituicao!!.nome)
                    .setNomeDoTitular(chaveInfo.conta.titular!!.nome)
                    .setCpfDoTitular(chaveInfo.conta.titular!!.cpf)
                    .setAgencia(chaveInfo.conta.agencia)
                    .setNumeroDaConta(chaveInfo.conta.numero)
                    .build()
                )
                .setCriadaEm(chaveInfo.registradaEm.let {
                    val createAt = it!!.atZone(ZoneId.of("UTC")).toInstant()
                    Timestamp.newBuilder()
                        .setSeconds(createAt.epochSecond)
                        .setNanos(createAt.nano)
                        .build()
                })
            )
            .build()


    }
}