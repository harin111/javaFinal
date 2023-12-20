package com.tdtu.JavaFn.Interface;

import org.springframework.data.repository.CrudRepository;
import com.tdtu.JavaFn.Classes.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
}
