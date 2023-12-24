package com.tdtu.JavaFn.Controller;

import com.itextpdf.styledxmlparser.jsoup.Jsoup;
import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import com.lowagie.text.pdf.BaseFont;
import com.tdtu.JavaFn.Classes.CustomerInfo;
import com.tdtu.JavaFn.Classes.Product;
import com.tdtu.JavaFn.Interface.ProductRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.thymeleaf.spring6.SpringTemplateEngine;


import java.io.*;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.*;

@Controller
@SessionAttributes("cart")
public class CartController {
    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;
    private final ProductRepository productRepository;

    public CartController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        // Retrieve cart items from the session
        List<Product> cart = (List<Product>) model.getAttribute("cart");

        // If the cart is null, initialize it
        if (cart == null) {
            cart = new ArrayList<>();
        }

        // Calculate overall total and add it to the model
        double overallTotal = calculateOverallTotal(cart);
        model.addAttribute("cart", cart);
        model.addAttribute("overallTotal", overallTotal);

        return "cart/view";
    }


    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("productId") int productId, Model model) {
        // Retrieve the product from the product repository
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            // Retrieve cart items from the session
            List<Product> cart = (List<Product>) model.getAttribute("cart");

            // If the cart is null, initialize it
            if (cart == null) {
                cart = new ArrayList<>();
            }

            // Check if the product is already in the cart
            Product productToAdd = optionalProduct.get();
            boolean productExists = false;
            for (Product cartProduct : cart) {
                if (cartProduct.getId() == productId) {
                    // Product exists in the cart, increment quantity
                    cartProduct.setQuantity(cartProduct.getQuantity() + 1);
                    productExists = true;
                    break;
                }
            }

            // If the product is not in the cart, add it
            if (!productExists) {
                productToAdd.setQuantity(1);
                cart.add(productToAdd);
            }

            // Update the cart in the session
            model.addAttribute("cart", cart);
        }

        return "redirect:/products/search";
    }


    @PostMapping("/cart/delete")
    public String removeFromCart(@RequestParam("productId") int productId, Model model) {
        // Retrieve cart items from the session
        List<Product> cart = (List<Product>) model.getAttribute("cart");

        if (cart != null) {
            // Remove the product with the specified ID from the cart
            cart.removeIf(product -> product.getId() == productId);

            // Update the cart in the session
            model.addAttribute("cart", cart);
        }

        return "redirect:/cart";
    }

    @PostMapping("/cart/updateQuantity")
    public String updateQuantity(
            @RequestParam("productId") int productId,
            @RequestParam("quantity") int quantity,
            Model model) {
        // Retrieve cart items from the session
        List<Product> cart = (List<Product>) model.getAttribute("cart");

        if (cart != null) {
            // Update the quantity of the product with the specified ID in the cart
            for (Product product : cart) {
                if (product.getId() == productId) {
                    product.setQuantity(quantity);
                    break;
                }
            }

            // Update the cart in the session
            model.addAttribute("cart", cart);
        }

        return "redirect:/cart";
    }

    private double calculateOverallTotal(List<Product> cart) {
        double overallTotal = 0.0;
        for (Product product : cart) {
            overallTotal += product.calculateTotalPrice();
        }
        return overallTotal;
    }

    @GetMapping("/cart/checkout")
    public String showCheckoutForm(Model model) {
        model.addAttribute("customerInfo", new CustomerInfo());
        return "cart/checkout";
    }

    @PostMapping("/cart/checkout/processCheckout")
    public String processCheckout(Model model) {
        List<Product> cart = (List<Product>) model.getAttribute("cart");

        // Create a map to store product names and quantities
        Map<String, Object> productDetails = new HashMap<>();

        // Process the checkout and calculate totalAmount
        double totalAmount = calculateTotalAmount(cart, productDetails); // You need to implement this

        // Render the Thymeleaf template
        // Add totalAmount and productDetails to the model for Thymeleaf to use in the invoice template
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("productDetails", productDetails);

        return "checkout/invoice";
    }

    private double calculateTotalAmount(List<Product> cart, Map<String, Object> productDetails) {
        double totalAmount = 0.0;

        for (Product product : cart) {
            double productTotalPrice = product.calculateTotalPrice();
            totalAmount += productTotalPrice;

            // Store product details in the map
            Map<String, Object> details = new HashMap<>();
            details.put("quantity", product.getQuantity());
            details.put("price", product.getRetailPrice());
            details.put("total", productTotalPrice);

            productDetails.put(product.getProductName(), details);
        }

        return totalAmount;
    }

    @GetMapping("/cart/checkout/generateInvoice")
    public void generateInvoice(HttpServletResponse response, Model model) throws IOException {
        // Prepare the Thymeleaf context
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(model.asMap());

        // Process the Thymeleaf template
        String htmlContent = thymeleafTemplateEngine.process("checkout/invoice", thymeleafContext);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            // Use Flying Saucer to convert HTML to PDF
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(baos);

            // Set the response content type
            response.setContentType("application/pdf");

            File htmlFile = new File("file:///D:/Games/JavaFn/JavaFn/src/main/resources/templates/checkout/invoice.html");
            Document doc = Jsoup.parse(htmlFile, "UTF-8");
            try (OutputStream os = new FileOutputStream("file:///D:/Games/JavaFn/JavaFn/src/main/resources/templates/checkout/invoice.pdf"))
            {
                ITextRenderer renderer1 = new ITextRenderer();
                SharedContext context = renderer1.getSharedContext();
                context.setPrint(true);
                context.setInteractive(false);
                String baseURL = FileSystems.getDefault().getPath("checkout").toUri().toURL().toString();
                renderer1.setDocumentFromString(doc.html(), baseURL);
                renderer1.layout();
                renderer1.createPDF(os);
                System.out.println("done");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
