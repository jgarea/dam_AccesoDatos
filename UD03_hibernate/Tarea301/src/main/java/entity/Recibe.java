package entity;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recibe")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recibe {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;
    
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tratamiento_id")
    private Tratamiento tratamiento;

    @Id
    @Column(name = "fecha_inicio")
    private LocalDate fecha_inicio;

    @Column(name = "fecha_fin")
    private LocalDate fecha_fin;

    @Override
    public String toString() {
        return "Recibe [fecha_fin=" + fecha_fin + ", fecha_inicio=" + fecha_inicio + ", tratamiento=" + tratamiento + "]\n";
    }
}
