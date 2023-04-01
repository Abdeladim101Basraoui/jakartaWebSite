package app.quiz.quiz.DAO.Interfaces;

import app.quiz.quiz.DTO.Departement;
import app.quiz.quiz.DTO.Enseignant;

import java.util.List;


public interface EnseignantDAOInterface {
void AjouterEnseignant(Enseignant enseignant);
    void modifierEnseignant(Enseignant enseignant);
void supprimerEnseignant(String cin);
Enseignant trouverEnseignant(String cin);
List<Enseignant> trouverLesEnseignants();
     List<Enseignant> getEnseignantsByDepartement(Departement departement);
    List<Enseignant> getEnseignantsBySpecialite(String specialite);
}
