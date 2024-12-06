package principal;


import logica.Inventario;
import logica.MenuInventario;
import logica.Producto;

import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Inventario inventario = new Inventario();
        MenuInventario menu = new MenuInventario(inventario);

        menu.mostrarMenu();


    }
}