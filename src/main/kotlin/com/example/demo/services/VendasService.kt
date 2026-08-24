package com.example.demo.services

import com.example.demo.entities.Vendas
import com.example.demo.repositories.ClienteRepository
import com.example.demo.repositories.VendasRepository
import org.springframework.stereotype.Service

@Service
class VendasService(
    private val repository: VendasRepository,
    private val clienteRepository: ClienteRepository
) {

    fun listarTodos(): List<Vendas> = repository.findAll()

    fun buscarPorId(id: Long): Vendas =
        repository.findById(id)
            .orElseThrow { NoSuchElementException("Venda com id $id não encontrada") }

    fun salvar(venda: Vendas): Vendas {
        validarClienteExiste(venda.clienteId)
        return repository.save(venda)
    }

    fun atualizar(id: Long, dados: Vendas): Vendas {
        val existente = buscarPorId(id)

        validarClienteExiste(dados.clienteId)

        existente.clienteId = dados.clienteId
        existente.dataVenda = dados.dataVenda
        existente.status = dados.status
        existente.valorTotal = dados.valorTotal

        return repository.save(existente)
    }

    fun deletar(id: Long) {
        if (!repository.existsById(id)) {
            throw NoSuchElementException("Venda com id $id não encontrada")
        }
        repository.deleteById(id)
    }

    private fun validarClienteExiste(clienteId: Long?) {
        if (clienteId == null || !clienteRepository.existsById(clienteId)) {
            throw NoSuchElementException("Cliente com id $clienteId não encontrado")
        }
    }
}