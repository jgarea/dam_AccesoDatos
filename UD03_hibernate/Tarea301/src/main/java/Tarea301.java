import entity.Doctor;
import entity.Paciente;

import org.hibernate.Session;
import repositorio.DoctorRepositorio;
import repositorio.PacienteRepositorio;

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
    public static void main(String[] args) {
        scanner = new Scanner(System.in);

        Session session = HibernateUtil.get().openSession();

        doctorRepositorio = new DoctorRepositorio(session);
        pacienteRepositorio = new PacienteRepositorio(session);

        int opcion = 0;
        while (opcion!=13) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Crear doctor");
            System.out.println("2. Modificar doctor");
            System.out.println("3. Borrar doctor por ID");
            System.out.println("4. Crear paciente");
            System.out.println("5. Modificar paciente");
            System.out.println("6. Borrar paciente por nombre");
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
                    // Implementar indicarFechaFinTratamiento
                    break;
                case 9:
                    // Implementar cambiarHospitalTratamiento
                    break;
                case 10:
                    // Implementar mostrarDatosPaciente
                    break;
                case 11:
                    // Implementar mostrarDatosTratamientosPorHospital
                    break;
                case 12:
                    // Implementar mostrarNumeroTotalTratamientosPorHospital
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

        

        if (doctor != null && paciente != null) {
            // Asignar el doctor al paciente
            // Aquí se debe implementar la lógica para asignar el doctor al paciente
            // Dependiendo de la estructura de las entidades y la base de datos

            //TODO
             //paciente.
            System.out.println("Doctor " + nombreDoctor + " asignado al paciente " + nombrePaciente);
        } else {
            if (doctor == null) {
                System.out.println("No se ha encontrado el doctor con nombre " + nombreDoctor);
            }
            if (paciente == null) {
                System.out.println("No se ha encontrado el paciente con nombre " + nombrePaciente);
            }
        }
    }
}
