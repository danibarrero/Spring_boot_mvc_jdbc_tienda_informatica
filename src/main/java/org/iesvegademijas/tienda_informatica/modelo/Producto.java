package org.iesvegademijas.tienda_informatica.modelo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto {
    private int codigo;
    private String nombre;
    private Double precio;
    private int id_fabricante;
}
