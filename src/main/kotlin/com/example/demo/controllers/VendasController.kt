package com.example.demo.controllers

import com.example.demo.entities.Vendas
import com.example.demo.services.VendasService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/vendas")
class VendasController(private val service: VendasService) {

    @GetMapping
    fun listarTodos(): ResponseEntity<List<Vendas>> =
        ResponseEntity.ok(service.listarTodos())

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): ResponseEntity<Vendas> =
        ResponseEntity.ok(service.buscarPorId(id))

    @PostMapping
    fun criar(@Valid @RequestBody venda: Vendas): ResponseEntity<Vendas> {
        val salvo = service.salvar(venda)
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo)
    }

    @PutMapping("/{id}")
    fun atualizar(@PathVariable id: Long, @Valid @RequestBody venda: Vendas): ResponseEntity<Vendas> =
        ResponseEntity.ok(service.atualizar(id, venda))

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: Long): ResponseEntity<Void> {
        service.deletar(id)
        return ResponseEntity.noContent().build()
    }
}