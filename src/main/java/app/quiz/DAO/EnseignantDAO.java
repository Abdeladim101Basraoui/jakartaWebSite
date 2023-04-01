package app.quiz.quiz.DAO;

import app.quiz.quiz.DAO.Interfaces.EnseignantDAOInterface;
import app.quiz.quiz.DTO.Departement;
import app.quiz.quiz.DTO.Enseignant;
import jakarta.servlet.ServletException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EnseignantDAO implements EnseignantDAOInterface {

    private Session session;

    public EnseignantDAO(Session _session) {
        super();
       this.session = _session;
    }

    @Override
    public void AjouterEnseignant(Enseignant enseignant) {
        Transaction tx  = this.session.beginTransaction();
                this.session.save(enseignant);
                tx.commit();

    }

    @Override
    public void modifierEnseignant(Enseignant enseignant) {
        Transaction tx  = this.session.beginTransaction();
        this.session.update(enseignant);
        tx.commit();
    }

    @Override
    public void supprimerEnseignant(String cin) {
Transaction tx = this.session.beginTransaction();
        Enseignant enseignant = this.session.get(Enseignant.class,cin);

        if(enseignant!=null)
            this.session.delete(enseignant);
    }

    @Override
    public Enseignant trouverEnseignant(String cin) {
        return (Enseignant) this.session.get(Enseignant.class,cin);

    }

    @Override
    public List<Enseignant> trouverLesEnseignants() {
        Query<Enseignant> query = this.session.createQuery("from Enseignant",Enseignant.class);
        List<Enseignant> enseignants = query.getResultList();
        return enseignants;
    }

    @Override
    public List<Enseignant> getEnseignantsByDepartement(Departement departement) {
        List<Enseignant> enseignants = null;
        try {
            Transaction tx = this.session.beginTransaction();
            String hql = "FROM Enseignant E WHERE E.departement = :departement";
            Query query = this.session.createQuery(hql);
            query.setParameter("departement", departement);
            enseignants = query.list();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enseignants;
    }

    @Override
    public List<Enseignant> getEnseignantsBySpecialite(String specialite) {
        List<Enseignant> enseignants = null;
        try {
            Transaction tx = this.session.beginTransaction();
            String hql = "FROM Enseignant E WHERE :specialite MEMBER OF E.specialites";
            Query query = session.createQuery(hql);
            query.setParameter("specialite", specialite);
            enseignants = query.list();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enseignants;
    }

    public Enseignant getEnseignantByCin(String cin) {
        String hql = "FROM Enseignant e WHERE e.cin = :cin";
        Query query = this.session.createQuery(hql, Enseignant.class);
        query.setParameter("cin", cin);
        return (Enseignant) query.uniqueResult();
    }

    public List<String> addSpecialities(String cin,String new_spec){
    Enseignant enseignant = getEnseignantByCin(cin);
    List<String> Specialities  = getEnseignantSpecialities(enseignant);
try{

    if (Specialities.contains(new_spec)){
    throw new Exception("the specialite is already exists in for the enseignant :"+enseignant.getNom());
    }else {
        Specialities.add(new_spec);
        enseignant.setSpecialites(Specialities);
        this.modifierEnseignant(enseignant);
    }
}
        catch (Exception ex){
            ex.printStackTrace();
        }
    return Specialities;
    }

    public List<String> getEnseignantSpecialities(Enseignant enseignant)
    {
        List<String> Specialities =new ArrayList<>();
        try {
            this.session.getTransaction().begin();
            Query query = session.createQuery("select distinct e.specialites from Enseignant e where e.cin =:cin");
            query.setParameter("cin",enseignant.getCin());
            List<String> results = query.getResultList();
            for (String result :results)
            {
                if (result!=null)
                {
                    String[] specArr = ((String) result).split(",");
                    for (String spec :specArr){
                        spec = spec.trim();
                        if (spec.isEmpty() && !Specialities.contains(spec))
                        {
                            Specialities.add(spec);
                        }
                    }
                }
                {

                }
            }

            this.session.getTransaction().commit();
        }
        catch (Exception ex){
            this.session.getTransaction().rollback();
            ex.printStackTrace();
        }
        return  Specialities;
    }
}
