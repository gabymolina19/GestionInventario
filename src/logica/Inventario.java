package logica;

import java.io.*;
import java.util.*;

public class Inventario {
    private List<Producto> productos;
    String ruta = "src/resource/productos.txt";

    public Inventario(){
        this.productos = new ArrayList<>();
    }


    //String ruta = "D:/Fundamentos de programacion/Archivos/productos.txt";
    //String ruta = "src/resource/productos.txt";//con esta ruta dinamica, hay veces generar lios por el src, el punto es como la raiz, me interesa del punto en adelante


    public void leerArchivo() throws IOException {
        productos.clear(); // Limpia la lista antes de leer el archivo nuevamente.
        try (FileReader fr = new FileReader(ruta)) {
            BufferedReader br = new BufferedReader(fr);
            String linea;

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                String[] datos = linea.split(",");

                int idProducto = Integer.parseInt(datos[0]);
                String nombreProducto = datos[1];
                String categoria = datos[2];
                double precio = Double.parseDouble(datos[3]);
                int cantidadDisponible = Integer.parseInt(datos[4]);

                productos.add(new Producto(idProducto, nombreProducto, categoria, precio, cantidadDisponible));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al leer el archivo: " + e.getMessage(), e);
        }
    }

    public void agregarProducto(Producto producto){

        /*try {
            leerArchivo(); // para pasarle la info del archivo a la lista
        } catch (IOException e) {
            System.out.println("Error al cargar los productos: " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        int id = producto.getIdProducto();
        boolean idValido=false;

        while (!idValido){
            //verifica si el id ya existe
            final int idTemp=id;
            boolean idExiste = productos.stream().anyMatch(p -> p.getIdProducto() == idTemp);

            if(idExiste){
                System.out.println("El ID: "+id+", ya existe.Por favor,ingrese otro ID");
                id = scanner.nextInt();
            }else {
                idValido = true;//ID no existe salga del ciclo y agreguelo
            }
        }

        //Asigna el ID
        producto.setIdProducto(id);*/

        productos.add(producto);//agrega en memoria (a la lista)

        try(FileWriter fw = new FileWriter(ruta,true);//con el true agrego nuevos datos y no remplaza
        BufferedWriter bw = new BufferedWriter(fw)){//escribe linea en el archivo
            bw.write(producto.getIdProducto() + "," +
                    producto.getNombreProducto() + "," +
                    producto.getCategoria()+ ","  +
                    producto.getPrecio() + "," +
                    producto.getCantidadDisponible()+"\n");
            System.out.println("Producto agregado al archivo.");
        } catch (IOException e) {
            System.out.println("Error al agregar en el archivo: " + e.getMessage());
        }
    }

    public void actualizarProducto(int idProducto, String nombreProducto, String categoria, double precio, int cantidadDisponible){

        try {
            leerArchivo(); // para pasarle la info del archivo a la lista
        } catch (IOException e) {
            System.out.println("Error al cargar los productos: " + e.getMessage());
        }

        boolean buscarId = false;

        for(Producto p:productos){//implemento un foreach para que busque el id en todas las lineas
            if((p.getIdProducto())==idProducto){//si los ID's son iguales
                p.setNombreProducto(nombreProducto);
                p.setCategoria(categoria);
                p.setPrecio(precio);
                p.setCantidadDisponible(cantidadDisponible);
                buscarId=true;//lo encontró
                break;
            }
        }

        if(buscarId){
            escribirArchivo();
            System.out.println("Producto:" +idProducto+",actualizado correctamente....");
        }else{
            System.out.println("Producto:"+ idProducto+" ,no encontrado");
        }

    }

    public void eliminarProducto(int idProducto){
        try {
            leerArchivo(); // para pasarle la info del archivo a la lista
        } catch (IOException e) {
            System.out.println("Error al cargar los productos: " + e.getMessage());
            return;//sino se puede cargar los productos se sale del metodo
        }

        boolean buscarId = false;

        //recorre la lista y busca por el ID
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getIdProducto() == idProducto) {
                productos.remove(i); // Elimina el producto de la lista
                buscarId = true;
                break;
            }
        }

        //si se encuentra el producto, eliminelo
        if(buscarId){
            escribirArchivo();
            System.out.println("Producto:" +idProducto+",eliminado....");
        }else{
            System.out.println("Producto:"+ idProducto+" ,no encontrado");
        }

    }

    public void buscarProducto(){
        try {
            leerArchivo(); // para pasarle la info del archivo a la lista
        } catch (IOException e) {
            System.out.println("Error al cargar los productos: " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("----------------------");
        System.out.println("Ingrese la busqueda   ");
        System.out.println("1.Buscar por categoria");
        System.out.println("2.Buscar por nombre   ");
        System.out.println("3.Buscar por ID       ");
        System.out.println("Opción:               ");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion){
            case 1:
                System.out.println("Ingrese la categoria:");
                String categoria=scanner.nextLine();
                buscarPorCategoria(categoria);
                break;

            case 2:
                System.out.println("Ingrese el nombre del producto:");
                String nombre=scanner.nextLine();
                buscarPorNombre(nombre);
                break;

            case 3:
                System.out.println("Ingrese el ID:");
                int id=scanner.nextInt();
                buscarPorId(id);
                break;

            default:
                System.out.println("Opción no valida....");
        }

    }

    private void buscarPorCategoria(String categoria) {
        boolean encontrado = false;
        System.out.println("Productos en la categoría \"" + categoria + "\":");
        for (Producto p : productos) {
            if (p.getCategoria().equalsIgnoreCase(categoria)) {
                System.out.println(p);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No se encontraron productos en esta categoría.");
        }
    }

    private void buscarPorNombre(String nombre) {
        boolean encontrado = false;
        System.out.println("Productos con el nombre \"" + nombre + "\":");
        for (Producto p : productos) {
            if (p.getNombreProducto().equalsIgnoreCase(nombre)) {
                System.out.println(p);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No se encontraron productos con este nombre.");
        }
    }

    private void buscarPorId(int id) {
        boolean encontrado = false;
        for (Producto p : productos) {
            if (p.getIdProducto() == id) {
                System.out.println("Producto encontrado: " + p);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            System.out.println("No se encontró un producto con ID: " + id);
        }
    }

    public Producto precioProducto(){
        try {
            leerArchivo(); // para pasarle la info del archivo a la lista
        } catch (IOException e) {
            System.out.println("Error al cargar los productos: " + e.getMessage());
        }

        Producto productoMayorValor = productos.get(0); // Inicialmente asumimos que el primer producto es el más caro

        for (Producto p : productos) {
            if (p.getPrecio() > productoMayorValor.getPrecio()) {
                productoMayorValor = p; // Actualizamos si encontramos un producto más caro
            }
        }
        System.out.println(productoMayorValor.getIdProducto() + "," +
                productoMayorValor.getNombreProducto() + "," +
                productoMayorValor.getCategoria() + "," +
                productoMayorValor.getPrecio() + "," +
                productoMayorValor.getCantidadDisponible());
        return productoMayorValor;
    }

    public Map<String,Integer> calcularCantidadCategoria(){
        try {
            leerArchivo(); // para pasarle la info del archivo a la lista
        } catch (IOException e) {
            System.out.println("Error al cargar los productos: " + e.getMessage());
        }

        //Se crea el mapa para almacenar la cantidad de productos por categoria
        Map<String, Integer> conteoCategoria = new HashMap<>();

        //for each para iterar en los productos
        for(Producto producto:productos){
            String categoria = producto.getCategoria();
            //Incrementa el contador para la categoria
            conteoCategoria.put(categoria, conteoCategoria.getOrDefault(categoria, 0) + 1);
        }
        return conteoCategoria;
    }

    public void generarReporte(){
        try {
            leerArchivo(); // para pasarle la info del archivo a la lista
        } catch (IOException e) {
            System.out.println("Error al cargar los productos: " + e.getMessage());
        }

        double valorTotal = 0.0;
        String reporte = "src/resource/reporte_inventario.txt";

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(reporte))){
            bw.write("-----------------------");
            bw.write("Reporte Inventario\n");
            bw.write("-----------------------\n");

            for(Producto producto:productos){
                double totalProducto=producto.getPrecio()*producto.getCantidadDisponible();

                bw.write("ID Producto: "+producto.getIdProducto()+
                        ", Nombre producto: "+producto.getNombreProducto()+
                        ", Categoria producto: "+producto.getCategoria()+
                        ", Precio: "+producto.getPrecio()+
                        ", Cantidad Disponible: "+producto.getCantidadDisponible()+
                        ", Valor Total: $ "+totalProducto+"\n");

                //Para que de el valor del total inventario
                valorTotal +=totalProducto;
            }
            bw.write("\n-------------------------------\n");
            bw.write("Valor total inventario: $"+valorTotal+"\n");
            System.out.println("Reporte generado exitosamente...ir a la siguiente ruta:"+reporte);
        }catch (IOException e){
            System.out.println("Error al generar el reporte:"+e.getMessage());
        }
    }


    private void escribirArchivo(){
        try (FileWriter fw = new FileWriter(ruta);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter next = new PrintWriter(bw)) {
            for (Producto p : productos) {
                next.println(p.getIdProducto() + "," +
                        p.getNombreProducto() + "," +
                        p.getCategoria() + "," +
                        p.getPrecio() + "," +
                        p.getCantidadDisponible()); }
        } catch (IOException e) {
            System.out.println("Error al actualizar el archivo: " + e.getMessage());
        }

    }


}
