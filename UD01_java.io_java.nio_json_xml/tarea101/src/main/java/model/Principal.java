package model;

/**
 * Aplicación que permita registrar autores y acceder a su información. 
 * Toda la información relacionada con los autores se almacenará en un fichero JSON.
 * @author jgntr
 */
public class Principal {

    public static void main(String[] args) {
        AplicacionAutores app = new AplicacionAutores();
        app.ejecutar();
    }
}
