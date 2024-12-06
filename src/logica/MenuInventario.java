package logica;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Map;

public class MenuInventario {

    private Scanner scanner;
    private Inventario inventario;

    public MenuInventario(Inventario inventario) {
        this.inventario = inventario;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion=0;

        do {
            try {
                System.out.println("-------------------------------------------");
                System.out.println("Menú Inventario");
                System.out.println("1. Agregar producto");//OK
                System.out.println("2. Actualizar producto");//OK
                System.out.println("3. Eliminar producto");//OK
                System.out.println("4. Buscar producto");//OK
                System.out.println("5. Calcular precio de producto");//OK
                System.out.println("6. Cantidad de productos por categoría");//OK
                System.out.println("7. Reporte Inventario");//OK
                System.out.println("8. Salir");
                System.out.println("-------------------------------------------");
                System.out.print("Seleccione una opción: ");
                opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        agregarProducto();
                        break;

                    case 2:
                        actualizarProducto();
                        break;

                    case 3:
                        eliminarProducto();
                        break;

                    case 4:
                        buscarProducto();
                        break;

                    case 5:
                        calcularPrecioProducto();
                        break;

                    case 6:
                        mostrarCantidadPorCategoria();
                        break;

                    case 7:
                        generarReporte();
                        break;

                    case 8:
                        System.out.println("Saliendo...");
                        break;

                    default:
                        System.out.println("Opción inválida.Por favor ingresa un número del menú");
                }
            }catch (InputMismatchException e){
                System.out.println("Por favor, ingrese un número");
                scanner.nextLine();
            }

        } while (opcion != 8);
    }

    private void agregarProducto() {
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nombre Producto: ");
        String nombre = scanner.nextLine();
        System.out.print("Categoría: ");
        String categoria = scanner.nextLine();
        System.out.print("Precio: ");
        double precio = scanner.nextDouble();
        System.out.print("Cantidad: ");
        int cantidadDisponible = scanner.nextInt();
        inventario.agregarProducto(new Producto(id, nombre, categoria, precio, cantidadDisponible));
    }

    private void actualizarProducto() {
        System.out.print("Ingrese el ID del producto a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Actualizar nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Actualizar categoría: ");
        String categoria = scanner.nextLine();
        System.out.print("Actualizar precio: ");
        double precio = scanner.nextDouble();
        System.out.print("Actualizar cantidad disponible: ");
        int cantidadDisponible = scanner.nextInt();
        inventario.actualizarProducto(id, nombre, categoria, precio, cantidadDisponible);
    }

    private void eliminarProducto() {
        System.out.print("Ingrese el ID del producto a eliminar: ");
        int id = scanner.nextInt();
        inventario.eliminarProducto(id);
    }

    private void buscarProducto() {
        inventario.buscarProducto();
    }

    private void calcularPrecioProducto() {
        inventario.precioProducto();
    }

    private void mostrarCantidadPorCategoria() {
        Map<String, Integer> cantidadPorCategoria = inventario.calcularCantidadCategoria();
        System.out.println("Cantidad de productos por categoría:");
        for (Map.Entry<String, Integer> entry : cantidadPorCategoria.entrySet()) {
            System.out.println("Categoría: " + entry.getKey() + ", Cantidad: " + entry.getValue());
        }
    }

    private void generarReporte() {
        inventario.generarReporte();
    }

}
