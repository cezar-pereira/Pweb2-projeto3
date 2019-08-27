package com.example.minhaloja.repositorios;

import com.example.minhaloja.modelo.Cliente;

import org.springframework.data.repository.CrudRepository;

public interface RepositorioCliente extends CrudRepository<Cliente, Long>{

    
}