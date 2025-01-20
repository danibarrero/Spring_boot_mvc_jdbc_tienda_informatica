package org.iesvegademijas.tienda_informatica.dao;

import org.iesvegademijas.tienda_informatica.modelo.Fabricante;
import org.iesvegademijas.tienda_informatica.modelo.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductoDAOImpl implements ProductoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    FabricanteDAOImpl fabricanteDAO;

    /**
     * Inserta en base de datos el nuevo producto, actualizando el id en el bean producto.
     */
    @Override
    public void create(Producto producto) {
        Optional<Fabricante> optionalFab = fabricanteDAO.find(producto.getFabricante().getCodigo());

        if (optionalFab.isPresent()) {
            jdbcTemplate.update("INSERT INTO producto (nombre, precio, id_fabricante) VALUES (? ,? ,?)",
                    producto.getNombre(), producto.getPrecio(), producto.getFabricante().getCodigo());
        } else {
            throw new RuntimeException("El fabricante con ID " + producto.getFabricante().getCodigo() + " no existe.");
        }
    }


    /**
     * Devuelve lista con todos loa productos.
     */
    @Override
    public List<Producto> getAll() {

        List<Producto> listPro = jdbcTemplate.query(
                "SELECT * FROM producto as p INNER JOIN fabricante as f on p.id_fabricante = f.codigo",
                (rs, rowNum) -> new Producto(rs.getInt("p.codigo")
                                            ,rs.getString("p.nombre")
                                            ,rs.getDouble("p.precio")
                                            ,new Fabricante(rs.getInt("f.codigo"), rs.getString("f.nombre")))
        );

        return listPro;

    }

    /**
     * Devuelve Optional de productos con el ID dado.
     */
    @Override
    public Optional<Producto> find(int id) {

        Producto p = jdbcTemplate.queryForObject(
                "SELECT * FROM producto as p INNER JOIN fabricante as f on p.id_fabricante = f.codigo WHERE p.codigo = ?",
                (rs, rowNum) -> new Producto(rs.getInt("p.codigo")
                        ,rs.getString("p.nombre")
                        ,rs.getDouble("p.precio")
                        ,new Fabricante(rs.getInt("f.codigo"), rs.getString("f.nombre"))
                ), id
        );

        if (p != null) return Optional.of(p);
        else return Optional.empty();

    }

    /**
     * Actualiza producto con campos del bean producto según ID del mismo.
     */
    public void update(Producto producto) {
        String sql = "UPDATE producto SET nombre = ?, precio = ?, id_fabricante = ? WHERE codigo = ?";
        int rows = jdbcTemplate.update(sql, producto.getNombre(),
                                            producto.getPrecio(),
                                            producto.getCodigo(),
                                            producto.getFabricante().getCodigo());

        if (rows == 0) {
            throw new RuntimeException("No se encontró el producto con código " + producto.getCodigo() + " para actualizar.");
        }
    }

    /**
     * Borra fabricante con ID proporcionado.
     */
    @Override
    public void delete(int id) {
        int rows = jdbcTemplate.update("DELETE FROM producto WHERE codigo = ?", id);

        if (rows == 0) System.out.println("Update de fabricante con 0 registros actualizados.");

    }
}
