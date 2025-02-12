package br.com.zup.edu.pix.client

import br.com.zup.edu.pix.conta.response.ContaResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client

@Client("\${itau.contas.url}")
interface ClientITAU {
    @Get("/api/v1/clientes/{clienteId}/contas")
    fun buscaContaPorTipo(@PathVariable clienteId: String, @QueryValue tipo: String): ContaResponse
}