package com.example.demo.entities


import jakarta.persistence.*
import jakarta.validation.constraints.*
import java.math.BigDecimal
import java.time.LocalDate


@Entity
@Table(name = "clientes")
class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @field:NotBlank(message = "Nome é obrigatório")
    @Column(length = 120)
    var nome: String? = null

    @field:NotBlank(message = "CPF/CNPJ é obrigatório")
    @field:Pattern(regexp = "\\d{11}|\\d{14}", message = "CPF/CNPJ deve conter 11 (CPF) ou 14 (CNPJ) dígitos")
    @Column(name = "cpf_cnpj", length = 20)
    var cpfCnpj: String? = null

    @field:NotBlank(message = "Tipo é obrigatório")
    @Column(length = 2)
    var tipo: String? = null

    @field:Email(message = "E-mail Inválido")
    @field:NotBlank(message = "E-mail é obrigatório")
    @Column(length = 120)
    var email: String? = null

    @Column(length = 60)
    var cidade: String? = null

    @field:NotBlank(message = "UF é obrigatória")
    @Column(length = 2)
    open var uf: String? = null

    @Column(name = "limite_credito", precision = 12, scale = 2)
    open var limiteCredito: BigDecimal? = null

    @Column(name = "ativo")
    open var ativo: Boolean? = true

    @Column(name = "data_cadastro")
    open var dataCadastro: LocalDate? = null
}


//// VALIDAÇÕES - Poderia fazer com o init do próprio kotlin
// {
//    init {
//        require(!nome.isNullOrBlank()) { "Nome é obrigatório" }
//        require(!cpfCnpj.isNullOrBlank()) { "CPF/CNPJ é obrigatório" }
//        require(cpfCnpj!!.matches(Regex("\\d{11}|\\d{14}"))) {
//            "CPF/CNPJ deve conter 11 (CPF) ou 14 (CNPJ) dígitos numéricos"
//        }
//        require(!tipo.isNullOrBlank()) { "Tipo é obrigatório" }
//        require(!email.isNullOrBlank() && email!!.contains("@")) { "E-mail inválido" }
//        require(!cidade.isNullOrBlank()) { "Cidade é obrigatória" }
//    }
//}