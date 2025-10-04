package com.hieuhoang.springrest.product;

import com.hieuhoang.springrest.common.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api/products")
public class ProductController {


    private final ProductRepository repo;


    public ProductController(ProductRepository repo) { this.repo = repo; }


    @GetMapping
    public List<Product> list() { return repo.findAll(); }


    @GetMapping("/{id}")
    public Product get(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Product " + id + " not found"));
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Product create(@Valid @RequestBody ProductRequest req) {
        Product p = new Product(req.getName(), req.getPrice(), req.getDescription());
        return repo.save(p);
    }


    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @Valid @RequestBody ProductRequest req) {
        Product p = repo.findById(id).orElseThrow(() -> new NotFoundException("Product " + id + " not found"));
        p.setName(req.getName());
        p.setPrice(req.getPrice());
        p.setDescription(req.getDescription());
        return repo.save(p);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!repo.existsById(id)) throw new NotFoundException("Product " + id + " not found");
        repo.deleteById(id);
    }
}