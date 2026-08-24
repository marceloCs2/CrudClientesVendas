package com.example.demo.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

data class ErroResponse(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val status: Int,
    val erro: String,
    val mensagem: String?,
    val detalhes : Map<String, String>? = null
)

@RestControllerAdvice
class GlobalException {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    open fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<ErroResponse> {
        val detalhes = ex.bindingResult.fieldErrors.associate { it.field to (it.defaultMessage ?: "Valor inválido") }

        val erro = ErroResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            erro = "Erro de validação",
            mensagem = "Um ou mais campos estão inválidos",
            detalhes = detalhes
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro)
    }
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNaoEncontrado(ex: NoSuchElementException): ResponseEntity<ErroResponse> {
        val erro = ErroResponse(
            status = HttpStatus.NOT_FOUND.value(),
            erro = "Não encontrado",
            mensagem = ex.message
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro)
    }

    @ExceptionHandler(IllegalStateException::class)
    open fun handleRegraDeNegocio(ex: IllegalStateException): ResponseEntity<ErroResponse> {
        val erro = ErroResponse(
            status = HttpStatus.CONFLICT.value(),
            erro = "Conflito na regra de negócio",
            mensagem = ex.message
        )
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro)
    }
}