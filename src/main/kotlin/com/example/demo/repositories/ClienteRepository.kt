package com.example.demo.repositories

import com.example.demo.entities.Cliente
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ClienteRepository : JpaRepository<Cliente, Long> {
    fun existsByCpfCnpj(cpfCnpj: String): Boolean

    @Query(
        value = """
                    select c.* from clientes c where not exists( select v.status  from vendas v where c.id = v.cliente_id );
        """,
        nativeQuery = true
    )
    fun listarClientesSemCompra(): List<Cliente>
}