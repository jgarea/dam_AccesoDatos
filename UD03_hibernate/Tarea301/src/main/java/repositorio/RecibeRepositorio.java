package repositorio;

import org.hibernate.Session;
import org.hibernate.Transaction;

import entity.Recibe;

public class RecibeRepositorio implements Repositorio<Recibe> {

    private Session session;

    public RecibeRepositorio(Session session) {
        this.session = session;
    }

    @Override
    public void guardar(Recibe t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'guardar'");
    }

    @Override
    public void actualizar(Recibe t) {
        Transaction trx = session.beginTransaction();
        session.merge(t);
        trx.commit();
    }

    @Override
    public void eliminar(Recibe t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminar'");
    }

}
