package br.com.zup.edu.pix.handlers


import br.com.zup.edu.pix.exceptions.ChavePixExistenteException
import io.grpc.Status
import javax.inject.Singleton

//@Singleton
class ChavePixExistenteExceptionHandler: ExceptionHandler<ChavePixExistenteException> {

    override fun handle(e: ChavePixExistenteException): ExceptionHandler.StatusWithDetails{
        return ExceptionHandler.StatusWithDetails(Status.ALREADY_EXISTS
            .withDescription(e.message)
            .withCause(e))
    }

    override fun supports(e: Exception): Boolean {
        return e is ChavePixExistenteException
    }
}
