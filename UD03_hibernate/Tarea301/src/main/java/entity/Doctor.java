package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Juan
 */
@Entity
@Table(name = "doctor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
    @Id
    @Column(name = "id")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "especialidad")
    private String especialidad;
    @Column(name = "telefono")
    private String telefono;
    
    
    @OneToOne(mappedBy = "doctor")
    //@ToString.Exclude // Evita la recursión infinita
    private Cita cita;


    @Override
    public String toString() {
        return "Doctor{" + "id=" + id + ", nombre=" + nombre + ", especialidad=" + especialidad + ", telefono=" + telefono + ", cita=" + cita + '}';
    }
    
}
