/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;

import entity.Doctor;
import java.util.List;
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
    public void actualizar(Doctor doctor) {
        Transaction trx = session.beginTransaction();
        session.merge(doctor);
        trx.commit();

    }

    @Override
    public void eliminar(Doctor doctor) {
        Transaction trx = session.beginTransaction();
        session.remove(doctor);
        trx.commit();
        System.out.println("Se ha eliminado el doctor con id" + doctor.getId());
    }
    
    public List<Doctor> ver(){
        Transaction trx = session.beginTransaction();
        List<Doctor> doctores = session.createQuery("FROM Doctor", Doctor.class).getResultList();
        trx.commit();
        return doctores;
    }
    
    public int obtenerUltimoId() {
        Transaction trx = session.beginTransaction();
        Integer ultimoId = session.createQuery("SELECT MAX(d.id) FROM Doctor d", Integer.class).uniqueResult();
        trx.commit();
        return ultimoId != null ? ultimoId : 0;
    }
    public boolean existeDoctor(int id) {
        Transaction trx = session.beginTransaction();
        Integer idbase = session.createQuery("SELECT d.id FROM Doctor d WHERE d.id = :id", Integer.class).setParameter("id", id).uniqueResult();
        trx.commit();
        return idbase != null;
    }
    
    public Doctor buscarPorNombre(String nombre) {
        Transaction trx = session.beginTransaction();
        Doctor doctor = session.createQuery("FROM Doctor d WHERE d.nombre = :nombre", Doctor.class)
                .setParameter("nombre", nombre)
                .uniqueResult();
        trx.commit();
        return doctor;
    }
    
}
