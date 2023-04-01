package app.quiz.quiz.DTO;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "departement")
public class Departement
{
    @Id
    @Column(name = "IdDepart")
    private  int IdDepart;
    @Column(name = "nom_departement")
    private String nomDepartement;

    @ManyToOne
    @JoinColumn
    private Enseignant chef;
    @OneToMany(mappedBy = "departement")
    private List<Enseignant> enseignants;

    public Departement(int idDepart, String nomDepartement, Enseignant chef, List<Enseignant> enseignants) {
        IdDepart = idDepart;
        this.nomDepartement = nomDepartement;
        this.chef = chef;
        this.enseignants = enseignants;
    }

    public Departement() {
    }

    public Departement(String nom) {
        this.nomDepartement  = nom;
    }

    public Departement(int id, String nom) {
        this.IdDepart = id;
        this.nomDepartement = nom;
    }

    public int getIdDepartement() {
        return IdDepart;
    }

    public void setIdDepartement(int idDepartement) {
        IdDepart = idDepartement;
    }

    public String getNomDepartement() {
        return nomDepartement;
    }

    public void setNomDepartement(String nomDepartement) {
        this.nomDepartement = nomDepartement;
    }

    public Enseignant getChef() {
        return chef;
    }

    public void setChef(Enseignant chef) {
        this.chef = chef;
    }

    public List<Enseignant> getEnseignants() {
        return enseignants;
    }

    public void setEnseignants(List<Enseignant> enseignants) {
        this.enseignants = enseignants;
    }
}
