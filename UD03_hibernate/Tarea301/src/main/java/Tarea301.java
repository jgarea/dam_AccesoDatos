
import entity.Doctor;
import org.hibernate.Session;
import repositorio.DoctorRepositorio;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

/**
 *
 * @author Juan
 */
public class Tarea301 {

    public static void main(String[] args) {
        System.out.println("Test");
        
        Session session = HibernateUtil.get().openSession();
        
        DoctorRepositorio doctorRepositorio = new DoctorRepositorio(session);
        
        Doctor doc = new Doctor();
        doc.setId(9);
        doc.setNombre("das");
        doc.setTelefono("132312");
        doc.setEspecialidad("dsaasd");
        doctorRepositorio.guardar(doc);
        
        
        session.close();
        System.out.println("Finalizando la conexion a MySQL");
    }
}
