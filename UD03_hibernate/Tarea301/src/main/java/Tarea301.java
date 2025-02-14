import entity.Cita;
import entity.Doctor;
import entity.Hospital;
import entity.Paciente;
import entity.Recibe;
import entity.Tratamiento;

import org.hibernate.Session;

import repositorio.CitaRepositorio;
import repositorio.DoctorRepositorio;
import repositorio.HospitalRepositorio;
import repositorio.PacienteRepositorio;
import repositorio.RecibeRepositorio;
import repositorio.TratamientoRepositorio;

import java.time.LocalDate;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

/**
 *
 * @author Juan
 */
public class Tarea301 {
    static Scanner scanner;
    static DoctorRepositorio doctorRepositorio;
    static PacienteRepositorio pacienteRepositorio;
    static TratamientoRepositorio tratamientoRepositorio;
    static RecibeRepositorio recibeRepositorio;
    static CitaRepositorio citaRepositorio;
    static HospitalRepositorio hospitalRepositorio;
    public static void main(String[] args) {
        scanner = new Scanner(System.in);

        Session session = HibernateUtil.get().openSession();

        doctorRepositorio = new DoctorRepositorio(session);
        pacienteRepositorio = new PacienteRepositorio(session);
        tratamientoRepositorio = new TratamientoRepositorio(session);
        recibeRepositorio = new RecibeRepositorio(session);
        citaRepositorio = new CitaRepositorio(session);
        hospitalRepositorio = new HospitalRepositorio(session);


        int opcion = 0;
        while (opcion!=13) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Crear doctor");
            System.out.println("2. Modificar doctor");
            System.out.println("3. Borrar doctor por ID");
            System.out.println("4. Crear paciente");
            System.out.println("5. Modificar paciente");
            System.out.println("6. Borrar paciente por nombre");
            //Hay que cambiar la clave unica de id_doctor de la tabla cita para probar
            System.out.println("7. Asignar doctor a paciente");
            System.out.println("8. Indicar fecha de fin de tratamiento");
            System.out.println("9. Cambiar hospital de tratamiento");
            System.out.println("10. Mostrar datos de un paciente");
            System.out.println("11. Mostrar datos de tratamientos por hospital");
            System.out.println("12. Mostrar número total de tratamientos por hospital");
            System.out.println("13. Salir");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir nueva línea

            switch (opcion) {
                case 1:
                    crearDoctor();
                    break;
                case 2:
                    modificarDoctor();
                    break;
                case 3:
                    eliminarDoctor();
                    break;
                case 4:
                    crearPaciente();
                    break;
                case 5:
                    modificarPaciente();
                    break;
                case 6:
                    borrarPaciente();
                    break;
                case 7:
                    asignarDoctorAPaciente();
                    break;
                case 8:
                    indicarFechaFinTratamiento();
                    break;
                case 9:
                    cambiarHospitalTratamiento();
                    break;
                case 10:
                    mostrarDatosPaciente();
                    break;
                case 11:
                    mostrarDatosTratamientosPorHospital();
                    break;
                case 12:
                    mostrarNumeroTotalTratamientosPorHospital();
                    break;
                case 13:
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
      session.close();  
    }

    private static void crearDoctor() {
        try {
            System.out.println("Introduzca el nombre del doctor:");
            String nombre = scanner.nextLine();
            System.out.println("Introduzca la especialidad del doctor:");
            String especialidad = scanner.nextLine();
            System.out.println("Introduzca el teléfono del doctor:");
            String telefono = scanner.nextLine();
            Doctor doctor = new Doctor();
            doctor.setId(doctorRepositorio.obtenerUltimoId() + 1);
            doctor.setNombre(nombre);
            doctor.setEspecialidad(especialidad);
            doctor.setTelefono(telefono);
            doctorRepositorio.guardar(doctor);
        } catch (Exception e) {
            System.out.println("Error al guardar el doctor");
        }
    }

    private static void modificarDoctor() {
        System.out.println("Introduzca el ID del doctor a modificar:");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        boolean encontrado = doctorRepositorio.existeDoctor(id);
        if (encontrado) {
            System.out.println("Introduzca el nuevo nombre del doctor:");
            String nombre = scanner.nextLine();
            System.out.println("Introduzca la nueva especialidad del doctor:");
            String especialidad = scanner.nextLine();
            System.out.println("Introduzca el nuevo teléfono del doctor:");
            String telefono = scanner.nextLine();
            Doctor doctor = new Doctor();
            doctor.setId(id);
            doctor.setNombre(nombre);
            doctor.setEspecialidad(especialidad);
            doctor.setTelefono(telefono);
            doctorRepositorio.actualizar(doctor);
        } else {
            System.out.println("No se ha encontrado el doctor con ID " + id);
        }
    }

    private static void eliminarDoctor() {
        System.out.println("Introduzca el ID del doctor a borrar:");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        boolean encontrado = doctorRepositorio.existeDoctor(id);
        if (encontrado) {
            Doctor doctor = new Doctor();
            doctor.setId(id);
            doctorRepositorio.eliminar(doctor);
        } else {
            System.out.println("No se ha encontrado el doctor con ID " + id);
        }
    }

    private static void crearPaciente() {
        try {
            System.out.println("Introduzca el nombre del paciente:");
            String nombre = scanner.nextLine();
            System.out.println("Introduzca la fecha de nacimiento del paciente:");
            System.out.println("Introduzca el día:");
            String dia = scanner.nextLine();
            System.out.println("Introduzca el mes:");
            String mes = scanner.nextLine();
            System.out.println("Introduzca el año:");
            String anho = scanner.nextLine();
            LocalDate fecha_nacimiento = LocalDate.of(Integer.parseInt(anho), Integer.parseInt(mes), Integer.parseInt(dia));
            System.out.println("Introduzca la dirección del paciente:");
            String direccion = scanner.nextLine();

            Paciente paciente = new Paciente();
            paciente.setId(pacienteRepositorio.obtenerUltimoId() + 1);
            paciente.setNombre(nombre);
            paciente.setFecha_nacimiento(fecha_nacimiento);
            paciente.setDireccion(direccion);
            pacienteRepositorio.guardar(paciente);
            

        } catch (Exception e) {
            System.out.println("Error al guardar el paciente");
            e.printStackTrace();
        }
    }

    private static void modificarPaciente() {
        System.out.println("Introduzca el ID del paciente  modificar:");
         int id = scanner.nextInt();
        scanner.nextLine(); 
        boolean encontrado = pacienteRepositorio.existePaciente(id);
        if (encontrado) {
            System.out.println("Introduzca el nuevo nombre del paciente:");
            String nombre = scanner.nextLine();
            System.out.println("Introduzca la nueva fecha de nacimiento del paciente:");
            System.out.println("Introduzca el día:");
            String dia = scanner.nextLine();
            System.out.println("Introduzca el mes:");
            String mes = scanner.nextLine();
            System.out.println("Introduzca el año:");
            String anho = scanner.nextLine();
            LocalDate fecha_nacimiento = LocalDate.of(Integer.parseInt(anho), Integer.parseInt(mes), Integer.parseInt(dia));
            System.out.println("Introduzca la nueva dirección del paciente:");
            String direccion = scanner.nextLine();

            Paciente paciente = new Paciente();
            paciente.setId(id);
            paciente.setNombre(nombre);
            paciente.setFecha_nacimiento(fecha_nacimiento);
            paciente.setDireccion(direccion);
            pacienteRepositorio.actualizar(paciente);
        } else {
            System.out.println("No se ha encontrado el paciente con ID " + id);
        }
    }

    private static void borrarPaciente() {
        System.out.println("Introduzca el nombre del paciente a borrar:");
        String nombre = scanner.nextLine();
        Paciente paciente = pacienteRepositorio.buscarPorNombre(nombre);
        if (paciente != null) {
            pacienteRepositorio.eliminar(paciente);
        } else {
            System.out.println("No se ha encontrado el paciente con nombre " + nombre);
        }
    }

    private static void asignarDoctorAPaciente() {
        System.out.println("Introduzca el nombre del doctor:");
        String nombreDoctor = scanner.nextLine();
        System.out.println("Introduzca el nombre del paciente:");
        String nombrePaciente = scanner.nextLine();

        Doctor doctor = doctorRepositorio.buscarPorNombre(nombreDoctor);
        Paciente paciente = pacienteRepositorio.buscarPorNombre(nombrePaciente);

        if(doctor == null){
            System.out.println("No se ha encontrado el doctor con nombre " + nombreDoctor);
            return;
        }
        if(paciente == null){
            System.out.println("No se ha encontrado el paciente con nombre " + nombrePaciente);
            return;
        }
            if(citaRepositorio.doctorTieneCita(doctor.getId())){
                System.out.println("El doctor ya tiene una cita asignada, prueba con otro doctor");
                return;
            }
        

       
            Cita cita = new Cita();
            cita.setDoctor(doctor);
            cita.setPaciente(paciente);
            cita.setFecha(LocalDate.now());
            cita.setEstado("Pendiente");
            citaRepositorio.guardar(cita);
            System.out.println("Doctor " + nombreDoctor + " asignado al paciente " + nombrePaciente);
        
    }

    private static void indicarFechaFinTratamiento() {
        System.out.println("Introduzca el nombre del paciente:");
        String nombrePaciente = scanner.nextLine();
        Paciente paciente=pacienteRepositorio.buscarPorNombre(nombrePaciente);
        if (paciente == null) {
            System.out.println("No se ha encontrado el paciente con nombre " + nombrePaciente);
            return;
        }
        System.out.println("Introduzca la fecha de inicio del tratamiento:");
        System.out.println("Introduzca el día:");
        String dia = scanner.nextLine();
        System.out.println("Introduzca el mes:");
        String mes = scanner.nextLine();
        System.out.println("Introduzca el año:");
        String anho = scanner.nextLine();
        LocalDate fecha_inicio = LocalDate.of(Integer.parseInt(anho), Integer.parseInt(mes), Integer.parseInt(dia));
        System.out.println("Introduzca el tipo de tratamiento:");
        String tipo = scanner.nextLine();
        Integer id_tratamiento = tratamientoRepositorio.getByTipo(tipo);
        if (id_tratamiento == null) {
            System.out.println("No se ha encontrado el tratamiento con tipo " + tipo);
            return;
        }
        System.out.println("Introduzca la fecha de fin del tratamiento:");
        System.out.println("Introduzca el día:");
        dia = scanner.nextLine();
        System.out.println("Introduzca el mes:");
        mes = scanner.nextLine();
        System.out.println("Introduzca el año:");
        anho = scanner.nextLine();
        LocalDate fecha_fin = LocalDate.of(Integer.parseInt(anho), Integer.parseInt(mes), Integer.parseInt(dia));
        Recibe recibe = new Recibe();
        recibe.setPaciente(paciente);
        recibe.setTratamiento(tratamientoRepositorio.getById(id_tratamiento));
        recibe.setFecha_inicio(fecha_inicio);
        recibe.setFecha_fin(fecha_fin);
        recibeRepositorio.actualizar(recibe);
        
    }
   
    private static void cambiarHospitalTratamiento() {
        System.out.println("Introduzca el id del tratamiento:");
        int id_tratamiento = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Introduzca el nombre del hospital actual:");
        String hospital_actual = scanner.nextLine();
        System.out.println("Introduzca el nombre del hospital del nuevo tratamiento:");
        String hospital_nuevo = scanner.nextLine();
        tratamientoRepositorio.cambiarHospitalTratamiento(id_tratamiento, hospital_actual, hospital_nuevo);
    }

    private static void mostrarDatosPaciente() {
        
        System.out.println("Introduzca el nombre del paciente:");
        String nombrePaciente = scanner.nextLine();
        Paciente paciente = pacienteRepositorio.buscarPorNombre(nombrePaciente);
        if (paciente == null) {
            System.out.println("No se ha encontrado el paciente con nombre " + nombrePaciente);
            return;
        }
        System.out.println("ID: " + paciente.getId());
        System.out.println("Nombre: " + paciente.getNombre());
        System.out.println("Fecha de nacimiento: " + paciente.getFecha_nacimiento());
        System.out.println("Dirección: " + paciente.getDireccion());
        System.out.println("Tratamientos que recibe:");
        System.out.println(paciente.getRecibe());
        System.out.println("Citas que tiene:");
        System.out.println(paciente.getCitas());
    }

    private static void mostrarDatosTratamientosPorHospital() {
        System.out.println("Introduzca el nombre del hospital:");
        String nombreHospital = scanner.nextLine();
        System.out.println("Tratamientos del hospital:");
        Hospital hospital = hospitalRepositorio.getByNombre(nombreHospital);
        if (hospital == null) {
            System.out.println("No se ha encontrado el hospital con nombre " + nombreHospital);
            return;
        }
        System.out.println(hospital.getTratamiento());
    }

    private static void mostrarNumeroTotalTratamientosPorHospital() {
        System.out.println("Introduzca el nombre del hospital:");
        String nombreHospital = scanner.nextLine();
        Hospital hospital = hospitalRepositorio.getByNombre(nombreHospital);
        if (hospital == null) {
            System.out.println("No se ha encontrado el hospital con nombre " + nombreHospital);
            return;
        }
        System.out.println("Número total de tratamientos del hospital " + nombreHospital + ": " + hospital.getTratamiento().size());
    }



}
