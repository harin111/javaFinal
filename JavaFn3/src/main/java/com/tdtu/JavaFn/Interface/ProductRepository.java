package com.tdtu.JavaFn.Interface;

import org.springframework.data.repository.CrudRepository;
import com.tdtu.JavaFn.Classes.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    List<Product> findByProductNameContainingOrBarcode(String name, String barcode);
}
