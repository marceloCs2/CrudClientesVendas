package com.example.demo.repositories

import com.example.demo.entities.Vendas
import org.springframework.data.jpa.repository.JpaRepository

interface VendasRepository : JpaRepository<Vendas, Long>