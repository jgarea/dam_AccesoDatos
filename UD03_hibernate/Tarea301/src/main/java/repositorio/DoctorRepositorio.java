/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;

import entity.Doctor;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Juan
 */
public class DoctorRepositorio implements Repositorio<Doctor>{
    private Session session;
    
    public DoctorRepositorio(Session session) {
        this.session = session;
    }
    @Override
    public void guardar(Doctor doctor) {
        Transaction trx = session.beginTransaction();
        session.persist(doctor);
        System.out.println("Se ha guardado el doctor con id" + doctor.getId());
        trx.commit();
    }

    @Override
    public void actualizar(Doctor t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(Doctor t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
