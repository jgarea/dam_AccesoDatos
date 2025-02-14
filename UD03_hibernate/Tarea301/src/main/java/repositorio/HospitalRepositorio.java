package repositorio;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import entity.Hospital;
import entity.Paciente;

public class HospitalRepositorio implements Repositorio<Hospital> {
private Session session;

    public HospitalRepositorio(Session session) {
        this.session = session;
    }

    @Override
    public void guardar(Hospital t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'guardar'");
    }

    @Override
    public void actualizar(Hospital t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizar'");
    }

    @Override
    public void eliminar(Hospital t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminar'");
    }

    public Hospital getByNombre(String nombre) {
        Transaction trx = session.beginTransaction();
        Hospital hospital = session.createQuery("FROM Hospital h WHERE h.nombre = :nom", Hospital.class).setParameter("nom", nombre).uniqueResult();
        trx.commit();
        return hospital;
        
    }
}
