package repositorio;

import org.hibernate.Session;
import org.hibernate.Transaction;

import entity.Hospital;
import entity.Tratamiento;

public class TratamientoRepositorio implements Repositorio<Tratamiento> {

    private Session session;

    public TratamientoRepositorio(Session session) {
        this.session = session;
    }

    @Override
    public void guardar(Tratamiento t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'guardar'");
    }

    @Override
    public void actualizar(Tratamiento t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizar'");
    }

    @Override
    public void eliminar(Tratamiento t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminar'");
    }

    public int getByTipo(String tipo) {
        Transaction trx = session.beginTransaction();
        Integer id = session.createQuery("SELECT t.id FROM Tratamiento t WHERE t.tipo = :tip", Integer.class).setParameter("tip", tipo).uniqueResult();
        trx.commit();
        return id;
    }

    public Tratamiento getById(Integer id_tratamiento) {
        Transaction trx = session.beginTransaction();
        Tratamiento tratamiento = session.createQuery("FROM Tratamiento t WHERE t.id = :id_tratamiento", Tratamiento.class)
                .setParameter("id_tratamiento", id_tratamiento).uniqueResult();
        trx.commit();
        return tratamiento;
    }

    public void cambiarHospitalTratamiento(int id_tratamiento, String hospital_actual, String hospital_nuevo) {
        Transaction trx = session.beginTransaction();
        Hospital hospital = session.createQuery("FROM Hospital h WHERE h.nombre = :nombre", Hospital.class)
                .setParameter("nombre", hospital_nuevo).uniqueResult();
        if (hospital == null) {
            System.out.println("El hospital nuevo no existe.");
            return;
        }
        int contador = session.createQuery("UPDATE Tratamiento t SET t.hospital = :hospital WHERE t.hospital.nombre = :hospital_actual AND t.id = :id_tratamiento")
                .setParameter("hospital", hospital)
                .setParameter("hospital_actual", hospital_actual)
                .setParameter("id_tratamiento", id_tratamiento)
                .executeUpdate();
        trx.commit();
        System.out.println(contador + " tratamientos han sido modificados.");
    }

}
