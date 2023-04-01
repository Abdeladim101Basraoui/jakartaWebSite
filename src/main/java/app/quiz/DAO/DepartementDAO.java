package app.quiz.quiz.DAO;

import app.quiz.quiz.DAO.Interfaces.DepartDAOinterface;
import app.quiz.quiz.DTO.Departement;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class DepartementDAO implements DepartDAOinterface {
    private Session session;
    public DepartementDAO(Session _session){
        this.session =_session;
    }
    @Override
    public void ajouterDepart(Departement departement) {
            Transaction tx = session.beginTransaction();
        try
        {
        session.save(departement);
        tx.commit();
        }catch
        (
                Exception ex
        )
        {
            if (tx!=null)
            {
                tx.rollback();
            }
            ex.printStackTrace();
        }

    }

    @Override
    public void supprimert(int id_departement) {
        Transaction tx = this.session.beginTransaction();
        Departement dept = this.session.get(Departement.class,id_departement);
        // Suppression du département
        session.delete(dept);
        // Validation de la transaction
        tx.commit();
    }

    @Override
    public Departement listerDepartementParId(int id_departement) {
         return this.session.get(Departement.class, id_departement);
    }

    @Override
    public List<Departement> listerTousDepartements() {
        try {
//            List<Departement> depts = new ArrayList<>();
//
//            String hql = "SELECT Departement (d.idDepart, d.nomDepartement, e.nom) " +
//                    "FROM Departement d " +
//                    "LEFT JOIN d.chef e";
//            Query<Departement> query = session.createQuery(hql, Departement.class);
//            depts = query.list();


            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Departement> cq = cb.createQuery(Departement.class);
            Root<Departement> rootEntry = cq.from(Departement.class);
            CriteriaQuery<Departement> all = cq.select(rootEntry);
            Query<Departement> allQuery = session.createQuery(all);
            return allQuery.getResultList();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateDepartement(Departement departement) {

        try {
            Transaction tx = session.beginTransaction();
            session.update(departement);
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

}
