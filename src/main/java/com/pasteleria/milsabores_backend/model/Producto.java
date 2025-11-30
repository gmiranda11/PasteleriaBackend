package com.pasteleria.milsabores_backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "productos")
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(nullable = false, length = 50)
    private String categoria;
    
    @Column(nullable = false)
    private Double precio;
    
    @Column(length = 500)
    private String descripcion;
    
    private String imagen;
    
    private Boolean personalizable = false;
    
    // Constructores
    public Producto() {}
    
    public Producto(String nombre, String categoria, Double precio, String descripcion, String imagen, Boolean personalizable) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.personalizable = personalizable;
    }
    
    // Getters y Setters (te ayudo a generarlos)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }
    
    public Boolean getPersonalizable() { return personalizable; }
    public void setPersonalizable(Boolean personalizable) { this.personalizable = personalizable; }
}