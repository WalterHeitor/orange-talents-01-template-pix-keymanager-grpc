package br.com.zup.edu.pix.client

import br.com.zup.edu.pix.client.request.DeletePixKeyRequest
import br.com.zup.edu.pix.client.response.CreatePixKeyResponse
import br.com.zup.edu.pix.client.response.DeletePixKeyResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.http.client.annotation.Client

@Client("\${bcb.pix.url}")
interface ClientBCB {

    @Post("/api/v1/pix/keys",
        produces = [MediaType.APPLICATION_XML],
        consumes = [MediaType.APPLICATION_XML])
    fun create(@Body request: CreatePixKeyRequest)
            : HttpResponse<CreatePixKeyResponse>

    @Delete("/api/v1/pix/keys/{key}",
        produces = [MediaType.APPLICATION_XML],
        consumes = [MediaType.APPLICATION_XML])
//    @Consumes(MediaType.APPLICATION_XML)
//    @Produces(MediaType.APPLICATION_XML)
    fun delete(@PathVariable key: String, @Body request: DeletePixKeyRequest)
            : HttpResponse<DeletePixKeyResponse>
}

