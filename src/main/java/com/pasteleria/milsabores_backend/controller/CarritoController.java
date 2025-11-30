package com.pasteleria.milsabores_backend.controller;

import com.pasteleria.milsabores_backend.model.Carrito;
import com.pasteleria.milsabores_backend.repository.CarritoRepository;
import com.pasteleria.milsabores_backend.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/carrito")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
public class CarritoController {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    // Obtener todos los items del carrito de un usuario
    @GetMapping("/{usuarioEmail}")
    public List<Carrito> getCarrito(@PathVariable String usuarioEmail) {
        return carritoRepository.findByUsuarioEmail(usuarioEmail);
    }

    // Agregar item al carrito
    @PostMapping("/{usuarioEmail}")
    public Carrito agregarAlCarrito(@PathVariable String usuarioEmail, @RequestBody Map<String, Object> request) {
        Long productoId = Long.valueOf(request.get("productoId").toString());
        Integer cantidad = Integer.valueOf(request.get("cantidad").toString());
        String mensajePersonalizado = (String) request.get("mensajePersonalizado");

        // Verificar si ya existe el item en el carrito
        List<Carrito> itemsExistentes = carritoRepository.findByUsuarioEmail(usuarioEmail);
        Carrito itemExistente = itemsExistentes.stream()
                .filter(item -> item.getProducto().getId().equals(productoId))
                .findFirst()
                .orElse(null);

        if (itemExistente != null) {
            // Actualizar cantidad si ya existe
            itemExistente.setCantidad(itemExistente.getCantidad() + cantidad);
            if (mensajePersonalizado != null) {
                itemExistente.setMensajePersonalizado(mensajePersonalizado);
            }
            return carritoRepository.save(itemExistente);
        } else {
            // Crear nuevo item
            Carrito nuevoItem = new Carrito(
                usuarioEmail,
                productoRepository.findById(productoId).orElseThrow(),
                cantidad,
                mensajePersonalizado
            );
            return carritoRepository.save(nuevoItem);
        }
    }

    // Actualizar cantidad y mensaje de un item
@PutMapping("/{usuarioEmail}/{productoId}")
public Carrito actualizarItem(
        @PathVariable String usuarioEmail,
        @PathVariable Long productoId,
        @RequestBody Map<String, Object> request) {
    
    Integer nuevaCantidad = Integer.valueOf(request.get("cantidad").toString());
    String mensajePersonalizado = (String) request.get("mensajePersonalizado");
    
    Carrito item = carritoRepository.findByUsuarioEmail(usuarioEmail).stream()
            .filter(i -> i.getProducto().getId().equals(productoId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Item no encontrado en carrito"));

    if (nuevaCantidad <= 0) {
        carritoRepository.delete(item);
        return null;
    } else {
        item.setCantidad(nuevaCantidad);
        if (mensajePersonalizado != null) {
            item.setMensajePersonalizado(mensajePersonalizado);
        }
        return carritoRepository.save(item);
    }
}

    // Eliminar item del carrito
    @DeleteMapping("/{usuarioEmail}/{productoId}")
@Transactional
public void eliminarDelCarrito(@PathVariable String usuarioEmail, @PathVariable Long productoId) {
    carritoRepository.deleteByUsuarioEmailAndProductoId(usuarioEmail, productoId);
}

@DeleteMapping("/{usuarioEmail}")
@Transactional
public void vaciarCarrito(@PathVariable String usuarioEmail) {
    carritoRepository.deleteByUsuarioEmail(usuarioEmail);
}
}