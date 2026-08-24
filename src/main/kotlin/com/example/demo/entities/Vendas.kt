package com.example.demo.entities

import jakarta.persistence.*
import jakarta.validation.constraints.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "vendas")
open class Vendas(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null,

    @Column(name = "cliente_id")
    open var clienteId: Long? = null,

    @field:NotNull(message = "Data da venda é obrigatória")
    @Column(name = "data_venda")
    open var dataVenda: LocalDateTime? = null,

    @field:NotBlank(message = "Status é obrigatório")
    @Column(length = 15)
    open var status: String? = null,

    @field:NotNull(message = "Valor total é obrigatório")
    @Column(name = "valor_total", precision = 12, scale = 2)
    open var valorTotal: BigDecimal? = null
)