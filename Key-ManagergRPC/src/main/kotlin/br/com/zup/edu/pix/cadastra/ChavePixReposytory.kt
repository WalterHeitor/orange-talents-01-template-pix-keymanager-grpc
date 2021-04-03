package br.com.zup.edu.pix.cadastra


import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.*

@Repository
interface ChavePixReposytory: JpaRepository<ChavePix, Long>  {
    abstract fun existsByChave(novaChave: String?): Boolean

    /**
     * sempre temos que colocar os tipos dos campos de acordo com as tabelas e nao de acordo com
     * o que vem de um request por exemplo;
     */
    abstract fun findByIdAndClienteId(id: Long?, clienteId: UUID?): Optional<ChavePix>

    abstract fun findByChave(chave: String): Optional<ChavePix>

    abstract fun findAllByClienteId(clienteId: UUID?): List<ChavePix>

}