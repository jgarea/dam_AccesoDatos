package entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Tratamiento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tratamiento {
    @Id
    private int id;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "costo", precision = 10, scale = 2)
    private BigDecimal costo;

    @ManyToOne
    @JoinColumn(name = "id_hospital")
    private Hospital hospital;

    @OneToMany(mappedBy = "tratamiento")
    private List<Recibe> recibe;

    public void addRecibe(Recibe recibe){
        this.recibe.add(recibe);
        recibe.setTratamiento(this);
    }
    @Override
    public String toString() {
        return "Tratamiento [costo=" + costo + ", id=" + id + ", tipo=" + tipo + "]\n";
    }
}
