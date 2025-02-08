/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;

import entity.Paciente;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Juan
 */
public class PacienteRepositorio implements Repositorio<Paciente> {

    private Session session;

    public PacienteRepositorio(Session session) {
        this.session = session;
    }

    @Override
    public void guardar(Paciente paciente) {
        Transaction trx = session.beginTransaction();
        session.persist(paciente);
        System.out.println("Se ha guardado el paciente con id " + paciente.getId());
        trx.commit();
    }

    @Override
    public void actualizar(Paciente paciente) {
        Transaction trx = session.beginTransaction();
        session.merge(paciente);
        trx.commit();
    }

    @Override
    public void eliminar(Paciente paciente) {
        Transaction trx = session.beginTransaction();
        session.remove(paciente);
        trx.commit();
        System.out.println("Se ha eliminado el paciente con id " + paciente.getId());
    }

    public List<Paciente> ver() {
        Transaction trx = session.beginTransaction();
        List<Paciente> pacientes = session.createQuery("FROM Paciente", Paciente.class).getResultList();
        trx.commit();
        return pacientes;
    }

    public Paciente buscarPorNombre(String nombre) {
        Transaction trx = session.beginTransaction();
        Paciente paciente = session.createQuery("FROM Paciente p WHERE p.nombre = :nombre", Paciente.class).setParameter("nombre", nombre).uniqueResult();
        trx.commit();
        return paciente;
    }

    public int obtenerUltimoId() {
        Transaction trx = session.beginTransaction();
        Integer ultimoId = session.createQuery("SELECT MAX(p.id) FROM Paciente p", Integer.class).uniqueResult();
        trx.commit();
        return ultimoId != null ? ultimoId : 0;
    }

    public boolean existePaciente(int id) {
        Transaction trx = session.beginTransaction();
        Integer idbase = session.createQuery("SELECT p.id FROM Paciente p WHERE p.id = :id", Integer.class).setParameter("id", id).uniqueResult();
        trx.commit();
        return idbase != null;
    }
}
