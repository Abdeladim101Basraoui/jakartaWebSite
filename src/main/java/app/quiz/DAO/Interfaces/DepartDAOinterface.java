package app.quiz.quiz.DAO.Interfaces;

import app.quiz.quiz.DTO.Departement;

import java.util.List;

public interface DepartDAOinterface {




        void ajouterDepart(Departement departement);
        void supprimert(int id_departement);
        Departement listerDepartementParId(int id_departement);
        List<Departement> listerTousDepartements();
        void updateDepartement(Departement departement);


}
