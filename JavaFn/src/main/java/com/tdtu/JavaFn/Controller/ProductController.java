package com.tdtu.JavaFn.Controller;

import com.tdtu.JavaFn.Classes.Product;
import com.tdtu.JavaFn.Interface.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    public String viewProductList(Model model) {
        Iterable<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "products/list";
    }

    @GetMapping("/products/add")
    public String showAddProductForm(Model model) {
        Product product = new Product();
        product.setCreationDate(LocalDate.now()); // Set the default creation date to today
        model.addAttribute("product", product);
        return "products/add";
    }


    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute Product product) {
        // You may want to add validation and error handling here
        productRepository.save(product);
        return "redirect:/products";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        productRepository.deleteById(id);
        return "redirect:/products";
    }
}


