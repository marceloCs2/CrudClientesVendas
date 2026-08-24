package com.example.demo.controllers

import com.example.demo.entities.Cliente
import com.example.demo.services.ClienteService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/clientes")
class ClienteController(private val service: ClienteService) {

    @GetMapping
    fun listarTodos(): ResponseEntity<List<Cliente>> = ResponseEntity.ok(service.listarTodos())

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long) : ResponseEntity<Cliente> = ResponseEntity.ok(service.buscarPorId(id))

    @PostMapping
    fun criar(@Valid @RequestBody cliente: Cliente): ResponseEntity<Cliente>{
        val salvo = service.salvar(cliente)
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(salvo))
    }

    @PutMapping("/{id}")
    fun atualizar(@PathVariable id: Long, @Valid @RequestBody cliente: Cliente): ResponseEntity<Cliente> = ResponseEntity.ok(service.atualizar(id, cliente))

    @DeleteMapping("/{id}")
    fun deletar (@PathVariable id: Long) : ResponseEntity<Void>{
        service.deletar(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/sem-compra")
    fun listarClientesSemCompra(): ResponseEntity<List<Cliente>> = ResponseEntity.ok(service.listarClientesSemCompra())
}