package br.com.zup.edu.pix.client

import br.com.zup.edu.pix.client.response.CreatePixKeyResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client

@Client("\${bcb.pix.url}")
interface ClientBCB {

    @Post("/api/v1/pix/keys",
    produces = [MediaType.APPLICATION_XML],
    consumes = [MediaType.APPLICATION_XML])
    fun create(@Body request: CreatePixKeyRequest): HttpResponse<CreatePixKeyResponse>
}

