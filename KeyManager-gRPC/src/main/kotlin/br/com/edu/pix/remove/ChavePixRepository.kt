package br.com.edu.pix.remove

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.*

@Repository
interface ChavePixRepository : JpaRepository<ChavePix, UUID>{

    abstract fun existsByChave(chave: String?): Boolean

}
