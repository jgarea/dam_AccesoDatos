package repositorio;

import org.hibernate.Session;
import org.hibernate.Transaction;

import entity.Cita;

public class CitaRepositorio implements Repositorio<Cita> {
    private Session session;
    
    public CitaRepositorio(Session session) {
        this.session = session;
    }

    @Override
    public void guardar(Cita t) {
        Transaction trx = session.beginTransaction();
        session.persist(t);
        System.out.println("Se ha guardado la cita con el paciente y doctor indicados");
        trx.commit();
    }

    @Override
    public void actualizar(Cita t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizar'");
    }

    @Override
    public void eliminar(Cita t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminar'");
    }

    public boolean doctorTieneCita(int id_doctor) {
        Transaction trx = session.beginTransaction();
        Integer id = session.createQuery("SELECT c.doctor.id FROM Cita c WHERE c.doctor.id = :id_doctor", Integer.class)
        .setParameter("id_doctor", id_doctor).uniqueResult();
        trx.commit();
        return id != null;
    }
}
