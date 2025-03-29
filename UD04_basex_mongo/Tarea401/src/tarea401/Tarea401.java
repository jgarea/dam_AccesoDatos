/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tarea401;

import com.mongodb.client.*;
import com.mongodb.client.result.UpdateResult;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.basex.examples.api.BaseXClient;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author Juan
 */
public class Tarea401 {

    static Scanner sc = new Scanner(System.in);
    static MongoClient mongoClient;

    private static MongoDatabase database = null;
    private static MongoCollection<Document> coleccion;

    static String emailSeleccionado = null;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);

        String nombreBDXml = "videojuegos";
        String selectorBD = "db:get('" + nombreBDXml + "')";
        int opcion = 0;

        do {
            try (BaseXClient session = new BaseXClient("localhost", 1984, "admin", "admin")) {

                System.out.println("""
                                   1. Modificar el valor de un elemento de un XML según un ID.
                                   2. Eliminar un videojuego según su ID.
                                   3. Consulta 1: Obtener todos los videojuegos ordenados por plataforma y en segundo lugar por título (se mostrarán los siguientes campos: id, titulo, precio, disponibilidad, edad_minima_recomendada y plataforma).
                                   4. Consulta 2: Listar videojuegos con una edad_minima_recomendada menor o igual a X años (se mostrarán los siguientes campos: id, titulo, precio, disponibilidad, edad_minima_recomendada y plataforma). Se deberá mostrar la información ordenada según la edad_minima_recomendada.
                                   5. Consulta 3: Mostrar la plataforma, el titulo y el precio del videojuego más barato para cada plataforma. En el caso de haber varios se devolverá el de la primera posición.
                                   6. Consulta 4: Mostrar el titulo y el genero de aquellos videojuegos cuya descripcion incluya una subcadena, independientemente del uso de mayúsculas o minúsculas. Se deberá mostrar la información ordenada alfabéticamente según el genero.
                                   7. Consulta 5: Mostrar la cantidad total de videojuegos para cada plataforma (teniendo en cuenta el elemento disponibilidad) y calcular el porcentaje que representa respecto al total de videojuegos. Se deberá mostrar la información ordenada de forma descendente por la cantidad de videojuegos.
                                   8. Consulta 6: Mostrar el precio que costaría comprar todos los videojuegos disponibles (teniendo en cuenta el precio de cada videojuego y la disponibilidad de cada uno).""");
                System.out.println("\n");
                System.out.println("Mongo:");
                System.out.println("""
                                   9. Crear un nuevo usuario (no podrá haber email repetidos).
                                   10. Identificar usuario según el email. Dado el email se obtendrá el id del usuario de forma que las siguientes consultas se harán sobre ese usuario. Para cambiar de usuario se tendrá que volver a seleccionar esta opción.
                                   11. Borrar un usuario.
                                   12. Modificar el valor de un campo de la información del usuario.
                                   13. Añadir videojuegos al carrito del usuario. Se mostrará la lista de videojuegos cuya edad_minima_recomendada sea inferior o igual a la del usuario actual y se pedirá: id del videojuego y cantidad, así como si se desea seguir introduciendo más videojuegos.
                                   14. Mostrar el carrito del usuario. Se mostrarán los datos del carrito y el coste total.
                                   15. Comprar el carrito del usuario. Se mostrará el contenido del carrito junto con una orden de confirmación. Si la orden es positiva se pasarán todos los videojuegos a formar parte de una nueva compra y desaparecerán del carrito.
                                   16. Mostrar las compras del usuario, incluyendo la información de la fecha de cada compra.
                                   17. Consulta 1: Teniendo en cuenta todos los usuarios, calcular el coste de cada carrito y listar los resultados ordenados por el total de forma descendente.
                                   18. Consulta 2: Teniendo en cuenta todos los usuarios, calcular el total gastado por cada usuario en todas sus compras y listar los resultados ordenados por el total de forma ascendente.""");
                System.out.println("0. Salir");
                System.out.println("Introduce la opcion:");
                opcion = sc.nextInt();
                sc.nextLine();
                switch (opcion) {
                    case 0:
                        break;
                    case 1:
                        System.out.println("Introduce el ID del videojuego a modificar:");
                        int idModificar = sc.nextInt();
                        sc.nextLine();
                        System.out.println("Introduce el campo a modificar:");
                        String campo = sc.nextLine();
                        System.out.println("Introduce el nuevo valor:");
                        String nuevoValor = sc.nextLine();
                        gestionConsulta(session, "replace value of node " + selectorBD + "//videojuego[id = " + idModificar + "]/" + campo + " with \"" + nuevoValor + "\"");
                        System.out.println("Videojuego con id: " + idModificar + " ha sido modificado");

                        break;
                    case 2:
                        System.out.println("Introduce el ID del videojuego a eliminar:");
                        int id = sc.nextInt();
                        sc.nextLine();
                        gestionConsulta(session, "delete node " + selectorBD + "//videojuego[id = " + id + "]");
                        System.out.println("Videojuego con id: " + id + " ha sido eliminado");
                        break;
                    case 3:
                        gestionConsulta(session, "for $x in " + selectorBD + "//videojuego\n"
                                + "order by $x/plataforma, $x/titulo\n"
                                + "return <videojuego>\n"
                                + "{data($x/id)}\n"
                                + "{$x/titulo}\n"
                                + "{$x/precio}\n"
                                + "{$x/disponibilidad}\n"
                                + "{$x/edad_minima_recomendada}\n"
                                + "{$x/plataforma}\n"
                                + "</videojuego>");
                        break;
                    case 4:
                        System.out.println("Selecciona hasta que edad mostrar:");
                        int edad = sc.nextInt();
                        sc.nextLine();
                        gestionConsulta(session, "for $x in " + selectorBD + "//videojuego\n"
                                + "where $x/edad_minima_recomendada <= " + edad + "\n"
                                + "order by xs:integer($x/edad_minima_recomendada)\n"
                                + "return <videojuego>\n"
                                + "{$x/id}\n"
                                + "{$x/titulo}\n"
                                + "{$x/precio}\n"
                                + "{$x/disponibilidad}\n"
                                + "{$x/edad_minima_recomendada}\n"
                                + "{$x/plataforma}\n"
                                + "</videojuego>");
                        break;
                    case 5:
                        gestionConsulta(session, "for $x in distinct-values(" + selectorBD + "//videojuego/plataforma)\n"
                                + "let $minPrecio := min(" + selectorBD + "/videojuegos/videojuego[plataforma = $x]/precio)\n"
                                + "let $juego := (" + selectorBD + "/videojuegos/videojuego[plataforma = $x and precio = $minPrecio])[1]\n"
                                + "return\n"
                                + "<videojuego>\n"
                                + "    { $juego/plataforma }\n"
                                + "    { $juego/titulo }\n"
                                + "    { $juego/precio }\n"
                                + "</videojuego>");
                        break;
                    case 6:
                        System.out.println("Introduce la subcadena a buscar:");
                        String cadena = sc.nextLine();
                        cadena = cadena.toLowerCase();
                        gestionConsulta(session, "for $x in " + selectorBD + "//videojuego\n"
                                + "where contains(lower-case($x/descripcion), \"" + cadena + "\")\n"
                                + "order by $x/genero\n"
                                + "return <videojuego>\n"
                                + "    {$x/titulo}\n"
                                + "    {$x/genero}\n"
                                + "</videojuego>");
                        break;
                    case 7:
                        gestionConsulta(session, "for $x in distinct-values(" + selectorBD + "//videojuego/plataforma)\n"
                                + "let $total := count(" + selectorBD + "/videojuegos/videojuego)\n"
                                + "let $disponibles := count(" + selectorBD + "/videojuegos/videojuego[disponibilidad > 0 and plataforma = $x])\n"
                                + "let $porcentaje := $disponibles div $total * 100\n"
                                + "order by $total descending\n"
                                + "return\n"
                                + "<videojuego>\n"
                                + "    <plataforma>{ $x }</plataforma>\n"
                                + "    <total>{ $disponibles }</total>\n"
                                + "    <porcentaje>{ format-number($porcentaje, '0.00') }%</porcentaje>\n"
                                + "</videojuego>");
                        break;
                    case 8:
                        gestionConsulta(session, "concat(sum(for $x in " + selectorBD + "//videojuego[disponibilidad > 0]\n"
                                + "return $x/disponibilidad * $x/precio), \" €\")");
                        break;
                    case 9:
                        crearUsuario();
                        break;
                    case 10:
                        identificarUsuario();
                        break;
                    case 11:
                        borrarUsuario();
                        break;
                    case 12:
                        modificarValorCampo();
                        break;
                    case 13:
                        addVideojuegoCarrito();
                        break;
                    case 14:
                        mostrarCarrito();
                        break;
                    case 15:
                        comprarCarrito();
                        break;
                    case 16:
                        mostrarCompras();
                        break;
                    case 17:
                        consulta1();
                        break;
                    case 18:
                        consulta2();
                        break;
                    default:
                        throw new AssertionError();
                }
            } catch (IOException ex) {
                Logger.getLogger(Tarea401.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (opcion != 0) {
                System.out.println("Pulsa para continuar...");
                sc.nextLine();
            }
        } while (opcion != 0);

    }

    private static void gestionConsulta(BaseXClient session, String input) {
        // Ejecución de la consulta
        try (BaseXClient.Query query = session.query(input)) {
            // Comprobación e iteración de los resultados
            while (query.more()) {
                // Obtención del parámetro
                System.out.println(query.next());
            }

            // Imprimir la información de la consulta
            System.out.println(query.info());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void crearUsuario() {
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("tienda");
        coleccion = database.getCollection("clientes");

        System.out.println("Introduce el nombre:");
        String nombre = sc.nextLine();
        System.out.println("Introduce el email:");
        String email = sc.nextLine();
        System.out.println("Introduce la edad:");
        int edad = sc.nextInt();
        sc.nextLine();
        System.out.println("Introduce la dirección:");
        String direccion = sc.nextLine();
        Document doc = new Document("_id", email)
                .append("nombre", nombre)
                .append("email", email)
                .append("edad", edad)
                .append("direccion", direccion);

        Document existingUser = coleccion.find(new Document("email", email)).first();
        if (existingUser != null) {
            System.out.println("El email ya está registrado. No se puede crear el usuario.");
        } else {
            coleccion.insertOne(doc);
            System.out.println("Usuario creado: " + nombre + ", " + email + ", " + edad + ", " + direccion);
        }

    }

    private static void identificarUsuario() {
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("tienda");
        coleccion = database.getCollection("clientes");

        System.out.println("Introduce el email:");
        String input = sc.nextLine();
        Document existingUser = coleccion.find(new Document("email", input)).first();
        if (existingUser != null) {
            System.out.println("Usuario identificado: " + existingUser.get("nombre") + ", " + existingUser.get("email") + ", " + existingUser.get("edad") + ", " + existingUser.get("direccion"));
            emailSeleccionado = input;
        } else {
            System.out.println("El email no está registrado.");
        }
    }

    private static void borrarUsuario() {
        if (emailSeleccionado == null) {
            System.out.println("Primero identifica un usuario.");
            return;
        }
        String email = emailSeleccionado;
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("tienda");
        coleccion = database.getCollection("clientes");

        Document existingUser = coleccion.find(new Document("email", email)).first();
        if (existingUser != null) {
            coleccion.deleteOne(new Document("email", email));
            System.out.println("Usuario borrado: " + email);
            emailSeleccionado = null;
        } else {
            System.out.println("El email no está registrado.");
        }
    }

    private static void modificarValorCampo() {
        if (emailSeleccionado == null) {
            System.out.println("Primero identifica un usuario.");
            return;
        }
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("tienda");
        coleccion = database.getCollection("clientes");

        System.out.println("Introduce el campo a modificar:");
        String campo = sc.nextLine();
        System.out.println("Introduce el nuevo valor:");
        String nuevoValor = sc.nextLine();

        Document documentoFiltro = new Document("email", emailSeleccionado);
        Document documentoActualizacion = new Document("$set", new Document(campo, nuevoValor));
        UpdateResult rs = coleccion.updateOne(documentoFiltro, documentoActualizacion);
        if (rs.getMatchedCount() > 0) {
            System.out.println("Actualizacion correcta. " + rs.getMatchedCount() + " documentos actualizados.");
            if (campo.equals("email")) {
                emailSeleccionado = nuevoValor;
            }
        } else {
            System.out.println("No se ha actualizado ningún documento");
        }
    }

    private static void addVideojuegoCarrito() {
        if (emailSeleccionado == null) {
            System.out.println("Primero identifica un usuario.");
            return;
        }
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("tienda");
        coleccion = database.getCollection("clientes");

        MongoCollection<Document> coleccionVideojuegos = database.getCollection("juegos");
        Document existingUser = coleccion.find(new Document("email", emailSeleccionado)).first();
        Integer edadUsuario = existingUser.getInteger("edad");

        FindIterable<Document> iterDoc = coleccionVideojuegos.find();
        for (Document doc : iterDoc) {
            if (doc.getInteger("edad_minima_recomendada") <= edadUsuario) {
                System.out.println(doc.get("id") + " - " + doc.get("titulo") + " - " + doc.get("precio") + " - " + " - Edad minima:" + doc.get("edad_minima_recomendada") + " - " + doc.get("plataforma"));
            }
        }
        System.out.println("Introduce el id del videojuego:");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println("Introduce la cantidad:");
        int cantidad = sc.nextInt();
        sc.nextLine();
        Document doc = new Document("id", id)
                .append("cantidad", cantidad);
        coleccion.updateOne(new Document("email", emailSeleccionado), new Document("$push", new Document("carrito", doc)));
        System.out.println("Videojuego añadido al carrito: " + id + ", cantidad: " + cantidad);
    }

    private static void mostrarCarrito() {
        if (emailSeleccionado == null) {
            System.out.println("Primero identifica un usuario.");
            return;
        }
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("tienda");
        coleccion = database.getCollection("clientes");

        MongoCollection<Document> coleccionVideojuegos = database.getCollection("juegos");
        Document existingUser = coleccion.find(new Document("email", emailSeleccionado)).first();
        if (existingUser == null) {
            System.out.println("El email no está registrado.");
            return;
        }
        FindIterable<Document> iterDoc = coleccion.find();
        for (Document doc : iterDoc) {
            if (doc.get("email").equals(emailSeleccionado)) {
                System.out.println("Carrito de " + doc.get("nombre") + ":");
                List<Document> carrito = (List<Document>) doc.get("carrito");
                if (carrito.isEmpty()) {
                    System.out.println("El carrito está vacío.");
                } else {
                    double total = 0;
                    for (Document item : carrito) {
                        Document videojuego = coleccionVideojuegos.find(new Document("id", item.get("id"))).first();
                        System.out.println(videojuego.get("titulo") + " - " + videojuego.get("precio") + " - " + item.get("cantidad"));
                        total += videojuego.getDouble("precio") * item.getInteger("cantidad");
                    }
                    System.out.println("Total: " + total);
                }
            }
        }

    }

    private static void comprarCarrito() {
        if (emailSeleccionado == null) {
            System.out.println("Primero identifica un usuario.");
            return;
        }
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("tienda");
        coleccion = database.getCollection("clientes");

        MongoCollection<Document> coleccionVideojuegos = database.getCollection("juegos");
        Document existingUser = coleccion.find(new Document("email", emailSeleccionado)).first();
        if (existingUser == null) {
            System.out.println("El email no está registrado.");
            return;
        }

        FindIterable<Document> iterDoc = coleccion.find(new Document("email", emailSeleccionado));
        for (Document doc : iterDoc) {
            List<Document> carrito = (List<Document>) doc.get("carrito");
            if (carrito.isEmpty()) {
                System.out.println("El carrito está vacío.");
            } else {
                double total = 0;
                List<Document> videojuegosComprados = new ArrayList<>();

                for (Document item : carrito) {
                    Document videojuego = coleccionVideojuegos.find(new Document("id", item.get("id"))).first();
                    Document videojuegos = new Document();
                    videojuegos.append("videojuego_id", videojuego.get("id"))
                            .append("nombre", videojuego.get("titulo"))
                            .append("cantidad", item.get("cantidad"))
                            .append("precio_unitario", videojuego.get("precio"));
                    videojuegosComprados.add(videojuegos);
                    System.out.println(videojuego.get("titulo") + " - " + videojuego.get("precio") + " - " + item.get("cantidad"));
                    total += videojuego.getDouble("precio") * item.getInteger("cantidad");
                }
                System.out.println("Total: " + total);
                System.out.println("¿Desea confirmar la compra? (s/n)");
                String confirmacion = sc.nextLine();
                if (confirmacion.equals("s")) {
                    Document compra = new Document()
                            .append("compra_id", new ObjectId())
                            .append("videojuegos", videojuegosComprados)
                            .append("total", total)
                            .append("fecha", new Date());
                    coleccion.updateOne(new Document("email", emailSeleccionado), new Document("$push", new Document("compras", compra)));
                    coleccion.updateOne(new Document("email", emailSeleccionado), new Document("$set", new Document("carrito", new ArrayList<>())));
                    System.out.println("Compra realizada con éxito.");
                } else {
                    System.out.println("Compra cancelada.");
                }

            }
        }

    }

    private static void mostrarCompras() {
        if (emailSeleccionado == null) {
            System.out.println("Primero identifica un usuario.");
            return;
        }
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("tienda");
        coleccion = database.getCollection("clientes");

        FindIterable<Document> iterDoc = coleccion.find(new Document("email", emailSeleccionado));

        for (Document doc : iterDoc) {
            List<Document> compras = (List<Document>) doc.get("compras");
            if (compras.isEmpty()) {
                System.out.println("No hay compras realizadas.");
            } else {
                for (Document compra : compras) {
                    System.out.println("Compra realizada el " + compra.get("fecha") + ":");
                    List<Document> videojuegos = (List<Document>) compra.get("videojuegos");
                    for (Document videojuego : videojuegos) {
                        System.out.println(videojuego.get("nombre") + " - " + videojuego.get("precio_unitario") + " - " + videojuego.get("cantidad"));
                    }
                    System.out.println("Total: " + compra.get("total"));
                }
            }
        }

    }

    private static void consulta1() {
        if (emailSeleccionado == null) {
            System.out.println("Primero identifica un usuario.");
            return;
        }
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("tienda");
        coleccion = database.getCollection("clientes");

        List<Document> clientes = coleccion.find().into(new ArrayList<>());
        List<String> resultados = new ArrayList<>();

        for (Document cliente : clientes) {
            List<Document> carrito = (List<Document>) cliente.get("carrito");
            if (carrito != null && !carrito.isEmpty()) {
                double total = 0;
                int cont = 0;
                for (Document item : carrito) {
                    Integer id = item.getInteger("id");
                    int cantidad = item.getInteger("cantidad");

                    Document videojuego = database.getCollection("juegos").find(new Document("id", id)).first();
                    if (videojuego != null) {
                        double precio = videojuego.getDouble("precio");
                        total += precio * cantidad;
                    }
                    String resultado = "Carrito " + cont++ + " de " + cliente.getString("email") + ": " + total + " euros";
                    resultados.add(resultado);
                }

            }
        }

        // result.sort((a, b) -> {
        //     return Double.compare(Double.parseDouble(a.split("-")[1].replace(" euros", "")), Double.parseDouble(b.split("-")[1].replace(" euros", "")));
        // });
        
        for (String resultado : resultados) {
            System.out.println(resultado);
        }
        
        

    }

    private static void consulta2() {
        if (emailSeleccionado == null) {
            System.out.println("Primero identifica un usuario.");
            return;
        }
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("tienda");
        coleccion = database.getCollection("clientes");

        List<Document> clientes = coleccion.find().into(new ArrayList<>());
        ArrayList<String> result= new ArrayList();

        for (Document cliente : clientes) {
            List<Document> compras = (List<Document>) cliente.get("compras");
            if (compras != null && !compras.isEmpty()) {
                double total = 0;
                for (Document compra : compras) {
                    total += compra.getDouble("total");
                }
                
                result.add(cliente.getString("email")+"-"+ total + " euros");
               
            }
        }

        // result.sort((a, b) -> {
        //     return Double.compare(Double.parseDouble(a.split("-")[1].replace(" euros", "")), Double.parseDouble(b.split("-")[1].replace(" euros", "")));
        // });

        for (String resultado : result) {
            System.out.println(resultado);
        }
    }

}
