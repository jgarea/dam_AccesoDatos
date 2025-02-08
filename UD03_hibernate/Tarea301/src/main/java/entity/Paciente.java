/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Juan
 */
@Entity
@Table(name = "paciente")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Paciente {
    @Id
    @Column(name = "id")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    private String nombre;
    @NonNull
    //@Temporal(TemporalType.DATE)
    private LocalDate fecha_nacimiento;
    @NonNull 
    private String direccion;
    
    @OneToMany(mappedBy = "paciente")
    private List<Cita> citas;
    
    public void addCitas(Cita cita){
        this.citas.add(cita);
        cita.setPaciente(this);
    }
    
}
