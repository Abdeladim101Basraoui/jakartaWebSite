package app.quiz.quiz.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DB {
    public static Session initSession(){
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        return  sf.openSession();
    }
}
