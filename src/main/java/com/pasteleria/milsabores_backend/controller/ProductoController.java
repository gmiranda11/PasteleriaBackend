package com.pasteleria.milsabores_backend.controller;

import com.pasteleria.milsabores_backend.model.Producto;
import com.pasteleria.milsabores_backend.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "http://localhost:3000") // Para conectar con React después
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    // GET: Obtener todos los productos
    @GetMapping
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    // GET: Obtener producto por ID
    @GetMapping("/{id}")
    public Optional<Producto> getProductoById(@PathVariable Long id) {
        return productoRepository.findById(id);
    }

    // GET: Obtener productos por categoría
    @GetMapping("/categoria/{categoria}")
    public List<Producto> getProductosByCategoria(@PathVariable String categoria) {
        return productoRepository.findByCategoria(categoria);
    }

    // POST: Crear nuevo producto
    @PostMapping
    public Producto createProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    // PUT: Actualizar producto existente
    @PutMapping("/{id}")
    public Producto updateProducto(@PathVariable Long id, @RequestBody Producto productoDetails) {
        return productoRepository.findById(id)
            .map(producto -> {
                producto.setNombre(productoDetails.getNombre());
                producto.setCategoria(productoDetails.getCategoria());
                producto.setPrecio(productoDetails.getPrecio());
                producto.setDescripcion(productoDetails.getDescripcion());
                producto.setImagen(productoDetails.getImagen());
                producto.setPersonalizable(productoDetails.getPersonalizable());
                return productoRepository.save(producto);
            })
            .orElse(null); // En una aplicación real, manejaríamos este caso mejor
    }

    // DELETE: Eliminar producto
    @DeleteMapping("/{id}")
    public void deleteProducto(@PathVariable Long id) {
        productoRepository.deleteById(id);
    }
}