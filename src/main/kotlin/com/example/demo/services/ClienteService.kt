package com.example.demo.services

import com.example.demo.entities.Cliente
import com.example.demo.repositories.ClienteRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ClienteService(private val repository: ClienteRepository) {

    fun listarTodos(): List<Cliente> = repository.findAll()

    fun buscarPorId(id: Long): Cliente =
        repository.findById(id).orElseThrow { NoSuchElementException("Cliente com id $id não encontrado") }

    fun salvar(cliente: Cliente): Cliente {
        if (repository.existsByCpfCnpj(cliente.cpfCnpj!!)) {
            throw IllegalStateException("Já existe um cliente com o CPF/CNPJ ${cliente.cpfCnpj}")
        }
        if (cliente.dataCadastro == null) {
            cliente.dataCadastro = LocalDate.now()
        }
        if (cliente.ativo == null) {
            cliente.ativo = true
        }
        return repository.save(cliente)
    }
    fun atualizar(id: Long, dados: Cliente): Cliente {
        val existente = buscarPorId(id)
        if (existente.cpfCnpj != dados.cpfCnpj && repository.existsByCpfCnpj(dados.cpfCnpj!!)) {
            throw IllegalStateException("Já existe um cliente com o CPF/CNPJ ${dados.cpfCnpj}")
        }
        existente.nome = dados.nome
        existente.cpfCnpj = dados.cpfCnpj
        existente.tipo = dados.tipo
        existente.email = dados.email
        existente.cidade = dados.cidade
        existente.uf = dados.uf
        existente.limiteCredito = dados.limiteCredito
        existente.ativo = dados.ativo

        return repository.save(existente)
    }
    fun deletar(id: Long) {
        if (!repository.existsById(id)) {
            throw NoSuchElementException("Cliente com id $id não encontrado")
        }
        repository.deleteById(id)
    }
    fun listarClientesSemCompra(): List<Cliente> = repository.listarClientesSemCompra()
}