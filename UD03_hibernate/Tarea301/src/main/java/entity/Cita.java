/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

/**
 *
 * @author Juan
 */
@Entity
@Table(name = "cita")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cita {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    //@Temporal(TemporalType.DATE)
    private LocalDate fecha;
    @NonNull
    private String estado;
    
    @OneToOne
    @JoinColumn(name = "id_doctor")
    @ToString.Exclude 
    private Doctor doctor;
    
    public void setDoctorBidireccional(Doctor doctor){
        this.doctor=doctor;
        this.doctor.setCita(this);
    }
    
    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;
    
    @Override
    public String toString() {
        return "Cita{" + "id=" + id + ", fecha=" + fecha + ", estado=" + estado + '}';
    }
    
}
