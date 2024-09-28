package model;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JOptionPane;

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
import java.util.logging.Level;
import java.util.logging.Logger;

public class AplicacionAutores {

    private final String RUTA_FICHERO = "autores.json";
    private VentanaInicioSesion ventanaInicioSesion;
    private VentanaCrearAutor ventanaCrearAutor;
    private VentanaMenuAutor ventanaMenuAutor;
    private VentanaVerDatos ventanaVerDatos;
    private VentanaCambiarTitulo ventanaCambiarTitulo;
    private VentanaBorrarAutor ventanaBorrarAutor;

    private void crearFicheroJson() {
        File file = new File(RUTA_FICHERO);
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("[]");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void guardarFicheroJson(JSONArray autores) {
        // TODO
    }

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

    private int obtenerPosicionAutor(String nombreAutor, JSONArray autores) {
        for (int i = 0; i < autores.length(); i++) {
            JSONObject autor = autores.getJSONObject(i);
            if (autor.getString("autor").equals(nombreAutor)) {
                return i;
            }
        }
        return -1;
    }

    private JSONObject obtenerAutoresJson(String nombreAutor) {
        JSONArray autores = obtenerAutoresJson();
        int posicion = obtenerPosicionAutor(nombreAutor, autores);
        if (posicion != -1) {
            return autores.getJSONObject(posicion);
        }
        return null;
    }

    public void ejecutar() {
        crearFicheroJson();
        ventanaInicioSesion = new VentanaInicioSesion(this);
        ventanaInicioSesion.setVisible(true);
    }

    public void iniciarValidacion(String nombreAutor, String tituloLibroAutor) {
        JSONObject autor = obtenerAutoresJson(nombreAutor);
        if (autor == null) {
            System.out.println("El autor no existe");
            return;
        }
        if(autor.getString("titulo").equals(tituloLibroAutor)){
            System.out.println("Autor validado");
        } else{
            System.out.println("Combinación de autor y título no existente");
        }
    }

    public void cerrarSesion() {
        // TODO
    }

    public void crearAutor(String nombre, String titulo, String paginas, String editorial) {
        // TODO
    }

    public void cambiarTituloLibro(String nombreAutor, String nuevoTitulo) {
        // TODO
    }

    public void borrarAutor(String nombreAutor) {
        // TODO
    }

    public void mostrarVentanaCrearAutor() {
        // TODO
    }

    public void mostrarVentanaVerDatos(String nombreAutor) {
        // TODO
    }

    public void mostrarVentanaCambiarTitulo(String nombreAutor) {
        // TODO
    }

    public void mostrarVentanaBorrarAutor(String nombreAutor) {
        // TODO
    }

    public void mostrarMenuAutor(String nombreAutor) {
        // TODO
    }
}
