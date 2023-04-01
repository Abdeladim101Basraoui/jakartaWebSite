package app.quiz.quiz.DTO;

import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "enseignant")
public class Enseignant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cin")
    private String cin;
    @Column(name = "nom")
    private String nom;
    @Column(name = "specialities")
    @ElementCollection
    private List<String> specialites;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="IdDepart" )
    private Departement departement;


    public Enseignant(Long id, String cin, String nom, List<String> specialites, Departement departement) {
        this.id = id;
        this.cin = cin;
        this.nom = nom;
        this.specialites = specialites;
        this.departement = departement;
    }

    public Enseignant() {

    }

    public Enseignant(String cin, String nom, Departement departement) {
        this.cin = cin;
        this.nom = nom;
        this.departement = departement;
    }

    public Enseignant(Long id, String cin, String nom, Departement departement) {
        this.id = id;
        this.cin = cin;
        this.nom = nom;
        this.departement = departement;
    }


    public String getCin() {
        return cin;
    }

    public String getNom() {
        return nom;
    }

    public List<String> getSpecialites() {
        return specialites;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setSpecialites(List<String> specialites) {
        this.specialites = specialites;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }
}
