package com.pasteleria.milsabores_backend.repository;

import com.pasteleria.milsabores_backend.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    List<Carrito> findByUsuarioEmail(String usuarioEmail);
    
    @Modifying
    @Query("DELETE FROM Carrito c WHERE c.usuarioEmail = :usuarioEmail")
    void deleteByUsuarioEmail(@Param("usuarioEmail") String usuarioEmail);
    
    @Modifying
    @Query("DELETE FROM Carrito c WHERE c.usuarioEmail = :usuarioEmail AND c.producto.id = :productoId")
    void deleteByUsuarioEmailAndProductoId(@Param("usuarioEmail") String usuarioEmail, @Param("productoId") Long productoId);
}