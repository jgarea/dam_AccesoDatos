package model;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import gui.VentanaBorrarAutor;
import gui.VentanaCambiarTitulo;
import gui.VentanaCrearAutor;
import gui.VentanaInicioSesion;
import gui.VentanaMenuAutor;
import gui.VentanaVerDatos;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JOptionPane;

public class AplicacionAutores {

    private final String RUTA_FICHERO = "autores.json";
    private VentanaInicioSesion ventanaInicioSesion;
    private VentanaCrearAutor ventanaCrearAutor;
    private VentanaMenuAutor ventanaMenuAutor;
    private VentanaVerDatos ventanaVerDatos;
    private VentanaCambiarTitulo ventanaCambiarTitulo;
    private VentanaBorrarAutor ventanaBorrarAutor;

    /**
     * crea el fichero JSON si todavía no existe.
     */
    private void crearFicheroJson() {
        File file = new File(RUTA_FICHERO);
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("[]");
            } catch (IOException ex) {
                System.out.println("Error al crear el fichero " + ex.getMessage());
            }
        }
    }

    /**
     * Guarda el JSONArray en el fichero
     *
     * @param autores
     */
    private void guardarFicheroJson(JSONArray autores) {
        try (FileWriter file = new FileWriter(RUTA_FICHERO)) {
            file.write(autores.toString());
            file.flush();
            
        } catch (IOException ex) {
            System.out.println("Error al guardar el fichero"+ex.getMessage());
        }
    }

    /**
     * devuelve un JSONArray que contiene a todos los autores registrados en la
     * aplicación.
     *
     * @return
     */
    private JSONArray obtenerAutoresJson() {
        JSONTokener token;
        JSONArray autores = null;
        try {
            token = new JSONTokener(new FileReader(RUTA_FICHERO));
            autores = new JSONArray(token);

        } catch (FileNotFoundException ex) {
            System.out.println("Fichero no encontrado " + ex.toString());
        }
        return autores;
    }

    /**
     * devuelve la posición de un autor dentro del array de autores. Si el autor
     * no está en el array, devuelve -1.
     *
     * @param nombreAutor
     * @param autores
     * @return
     */
    private int obtenerPosicionAutor(String nombreAutor, JSONArray autores) {
        for (int i = 0; i < autores.length(); i++) {
            JSONObject autor = autores.getJSONObject(i);
            if (autor.getString("autor").equals(nombreAutor)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * devuelve todos los datos de un autor, en formato JSONObject. Si el autor
     * no existe, devuelve null.
     *
     * @param nombreAutor
     * @return
     */
    private JSONObject obtenerAutoresJson(String nombreAutor) {
        JSONArray autores = obtenerAutoresJson();
        int posicion = obtenerPosicionAutor(nombreAutor, autores);
        if (posicion != -1) {
            return autores.getJSONObject(posicion);
        }
        return null;
    }

    /**
     * ejecuta la ventana de inicio de validación.
     */
    public void ejecutar() {
        crearFicheroJson();
        ventanaInicioSesion = new VentanaInicioSesion(this);
        ventanaInicioSesion.setVisible(true);
    }

    /**
     * inicia la validación en base al autor y al título introducidos.
     *
     * @param nombreAutor
     * @param tituloLibroAutor
     */
    public void iniciarValidacion(String nombreAutor, String tituloLibroAutor) {
        JSONObject autor = obtenerAutoresJson(nombreAutor);
        if (autor == null) {
            ventanaInicioSesion.mostrarMensaje("El autor no existe");
            return;
        }
        if (autor.getString("titulo").equals(tituloLibroAutor)) {
            ventanaInicioSesion.mostrarMensaje("Autor validado");
            mostrarMenuAutor(nombreAutor);
        } else {
            ventanaInicioSesion.mostrarMensaje("Combinación de autor y título no existente");
        }
    }

    /**
     * cierra la sesión y vuelve a la ventana de inicio.
     */
    public void cerrarSesion() {
        if (ventanaBorrarAutor != null) {
            ventanaBorrarAutor.setVisible(false);
            ventanaBorrarAutor = null;
        }
        if (ventanaCambiarTitulo != null) {
            ventanaCambiarTitulo.setVisible(false);
            ventanaCambiarTitulo = null;
        }
        if (ventanaCrearAutor != null) {
            ventanaCrearAutor.setVisible(false);
            ventanaCrearAutor = null;
        }
        if (ventanaMenuAutor != null) {
            ventanaMenuAutor.setVisible(false);
            ventanaMenuAutor = null;
        }
        if (ventanaVerDatos != null) {
            ventanaVerDatos.setVisible(false);
            ventanaVerDatos = null;
        }
        if (ventanaInicioSesion != null) {
            ventanaInicioSesion.setVisible(false);
            ventanaInicioSesion = null;
        }
        ventanaInicioSesion = new VentanaInicioSesion(this);
        ventanaInicioSesion.setVisible(true);

    }

    /**
     * registra un autor en el fichero JSON en función de los datos pasados por
     * parámetro.
     *
     * @param nombre
     * @param titulo
     * @param paginas
     * @param editorial
     */
    public void crearAutor(String nombre, String titulo, String paginas, String editorial) {
        JSONObject autor = new JSONObject();
        autor.put("autor", nombre);
        autor.put("titulo", titulo);
        autor.put("paginas", paginas);
        autor.put("editorial", editorial);
        JSONArray autores = obtenerAutoresJson();
        autores.put(autor);
        guardarFicheroJson(autores);
        cerrarSesion();

    }

    /**
     * cambia el título del libro del autor en el fichero JSON.
     *
     * @param nombreAutor
     * @param nuevoTitulo
     */
    public void cambiarTituloLibro(String nombreAutor, String nuevoTitulo) {
        JSONArray autores = obtenerAutoresJson();
        int pos = obtenerPosicionAutor(nombreAutor, autores);
        if (pos != -1) {
            autores.getJSONObject(pos).put("titulo", nuevoTitulo);
            guardarFicheroJson(autores);
        }
    }

    /**
     * borrar el autor del fichero JSON y cierra la sesión de validación.
     *
     * @param nombreAutor
     */
    public void borrarAutor(String nombreAutor) {
        JSONArray autores = obtenerAutoresJson();
        int posicion = obtenerPosicionAutor(nombreAutor, autores);
        if (posicion != -1) {
            autores.remove(posicion);
            guardarFicheroJson(autores);
        }
    }

    /**
     * abre la ventana para crear un nuevo autor.
     */
    public void mostrarVentanaCrearAutor() {
        ventanaCrearAutor = new VentanaCrearAutor(this);
        ventanaCrearAutor.setVisible(true);
    }

    /**
     * abre la ventana en la que se muestran los datos del autor.
     *
     * @param nombreAutor
     */
    public void mostrarVentanaVerDatos(String nombreAutor) {
        JSONObject autor = obtenerAutoresJson(nombreAutor);
        ventanaVerDatos = new VentanaVerDatos(this, autor.getString("autor"), autor.getString("paginas"), autor.getString("editorial"));
        ventanaVerDatos.setVisible(true);
        // TODO
    }

    /**
     * abre la ventana que permite introducir un nuevo nombre del título.
     *
     * @param nombreAutor
     */
    public void mostrarVentanaCambiarTitulo(String nombreAutor) {
        ventanaCambiarTitulo = new VentanaCambiarTitulo(this, nombreAutor);
        ventanaCambiarTitulo.setVisible(true);
    }

    /**
     * abre la ventana para confirmar el borrado del autor.
     *
     * @param nombreAutor
     */
    public void mostrarVentanaBorrarAutor(String nombreAutor) {
        ventanaBorrarAutor = new VentanaBorrarAutor(this, nombreAutor);
        ventanaBorrarAutor.setVisible(true);
    }

    /**
     * Muestra la ventana del autor
     *
     * @param nombreAutor
     */
    public void mostrarMenuAutor(String nombreAutor) {
        ventanaMenuAutor = new VentanaMenuAutor(this, nombreAutor);
        ventanaMenuAutor.setVisible(true);
    }

    /**
     * Metodo para validar la creación de un autor
     *
     * @param nombreAutor
     * @param tituloLibro
     * @param paginas
     * @param editorial
     * @return
     */
    public boolean validarCrearAutor(String nombreAutor, String tituloLibro, String paginas, String editorial) {
        if (nombreAutor == null || nombreAutor.isEmpty()) {
            mostrarErrorModal("El nombre del autor no puede estar vacío");
            return false;
        }
        if (tituloLibro == null || tituloLibro.isEmpty()) {
            mostrarErrorModal("El título del libro no puede estar vacío");
            return false;
        }
        if (!esNumeroValido(paginas)) {
            mostrarErrorModal("El número de páginas debe ser un número");
            return false;
        }
        if (editorial == null || editorial.isEmpty()) {
            mostrarErrorModal("La editorial no puede estar vacía");
            return false;
        }
        return true;
    }

    /**
     * Metodo para saber si un numero es mayor que 0
     *
     * @param paginas
     * @return
     */
    private boolean esNumeroValido(String paginas) {
        try {
            int numPaginas = Integer.parseInt(paginas);
            return numPaginas > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Metodo para mostrar un modal
     *
     * @param mensaje
     */
    private void mostrarErrorModal(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error de Validación", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Validar titulo no está vacio
     *
     * @param text
     * @return
     */
    public boolean validarCambiarTitulo(String text) {
        if (text == null || text.isEmpty()) {
            mostrarErrorModal("El titulo no puede estar vacío");
            return false;
        }
        return true;
    }
}
