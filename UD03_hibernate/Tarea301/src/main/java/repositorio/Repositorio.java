/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package repositorio;

/**
 *
 * @author Juan
 */
public interface Repositorio<T> {
    void guardar(T t);
    void actualizar(T t);
    void eliminar(T t);
}
