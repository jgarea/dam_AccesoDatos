/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @NonNull
    private String estado;
    
    @OneToOne
    @JoinColumn(name = "id_doctor")
    private Doctor doctor;
    
    public void setDoctorBidireccional(Doctor doctor){
        this.doctor=doctor;
        this.doctor.setCita(this);
    }
    
    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;
    
    
}
