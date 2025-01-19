package org.iesvegademijas.tienda_informatica.controlador;

import org.iesvegademijas.tienda_informatica.modelo.Producto;
import org.iesvegademijas.tienda_informatica.servicio.FabricanteService;
import org.iesvegademijas.tienda_informatica.servicio.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private FabricanteService fabricanteService;

    @GetMapping("/productos")
    public String listar(Model model) {
        List<Producto> listAllPro = productoService.listAll();
        model.addAttribute("listarProductos", listAllPro);
        return "productos";
    }

    @GetMapping("/productos/{codigo}")
    public String detalle(Model model, @PathVariable Integer codigo) {
        Producto producto = productoService.one(codigo);
        model.addAttribute("producto", producto);
        return "detalle-productos";
    }

    @GetMapping("/productos/crear")
    public String crear(Model model) {
        Producto producto = new Producto();
        model.addAttribute("producto", producto);
        model.addAttribute("fabricante", fabricanteService.listAll());
        return "crear-productos";
    }

    @PostMapping("/productos/crear")
    public RedirectView submitCrear(@ModelAttribute("producto") Producto producto) {
        productoService.newProducto(producto);
        return new RedirectView("/productos");
    }

    @GetMapping("/productos/editar/{codigo}")
    public String editar(Model model, @PathVariable Integer codigo) {
        Producto producto = productoService.one(codigo);
        model.addAttribute("producto", producto);
        return "editar-productos";
    }

    @PostMapping("/productos/editar/{codigo}")
    public RedirectView submitEditar(@ModelAttribute("producto") Producto producto) {
        productoService.replaceProducto(producto);
        return new RedirectView("/productos");
    }

    @PostMapping("/productos/borrar/{codigo}")
    public RedirectView submitBorrar(@PathVariable Integer codigo) {
        productoService.deleteProducto(codigo);
        return new RedirectView("/productos");
    }
}
