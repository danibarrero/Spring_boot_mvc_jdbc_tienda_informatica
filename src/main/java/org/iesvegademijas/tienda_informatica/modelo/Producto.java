package org.iesvegademijas.tienda_informatica.modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Producto {

    private int codigo;
    private String nombre;
    private Double precio;
    //private Fabricante fabricante;
    private int id_fabricante;

}
