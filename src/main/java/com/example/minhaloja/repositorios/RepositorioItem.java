package com.example.minhaloja.repositorios;

import com.example.minhaloja.modelo.Item;

import org.springframework.data.repository.CrudRepository;

public interface RepositorioItem extends CrudRepository<Item, Long>{
      
}