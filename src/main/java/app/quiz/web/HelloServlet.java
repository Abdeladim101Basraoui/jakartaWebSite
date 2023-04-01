package app.quiz.quiz.web;

import java.io.*;
import java.util.List;

import app.quiz.quiz.DAO.DB;
import app.quiz.quiz.DAO.DepartementDAO;
import app.quiz.quiz.DAO.EnseignantDAO;
import app.quiz.quiz.DTO.Departement;
import app.quiz.quiz.DTO.Enseignant;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.hibernate.Session;

@WebServlet(urlPatterns = {
        "/depts",
        "/addDept",
        "/enseiByDept",
        "/enseignBySpecial" ,
        "/addEnseign",
        "/updateEnseign",
        "/deleteEnseign",
        "/ficheEnseignant",
"/enseignants"})
public class HelloServlet extends HttpServlet {

    private Session session;
    private EnseignantDAO enseignantDAO;
    private DepartementDAO departementDAO;

    public void init() throws ServletException {
        super.init();
        try
        {
            this.session = DB.initSession();
            this.departementDAO = new DepartementDAO(session);
            this.enseignantDAO  = new EnseignantDAO(session);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public  void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String action = request.getServletPath();
    switch (action)
    { case "/depts":
        listerDepts(request, response);
        break;
        case "/addDept":
            addDept(request, response);
            break;
        case "/updateDept":
            updateDept(request, response);
            break;
        case "/enseignByDept":
            enseignByDept(request, response);
            break;
        case "/enseignBySpecial":
            enseignBySpecial(request, response);
            break;
        case "/addEnseign":
            addEnseign(request, response);
            break;
        case "/updateEnseign":
            updateEnseign(request, response);
            break;
        case "/deleteEnseign":
            deleteEnseign(request, response);
            break;
        case "/ficheEnseignant":
            ficheEnseignant(request, response);
            break;
        default:
//            request.setAttribute(listerDepts(request,response));
//            RequestDispatcher dispatche = request.getRequestDispatcher("departements.jsp");
//            response.sendRedirect(request.getContextPath() + "/departements");
        {
            try {
            throw new Exception("Default");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//            break;
        }

    }
    }

    private void ficheEnseignant(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cin = request.getParameter("cin");
        Enseignant enseignant = enseignantDAO.getEnseignantByCin(cin);
        request.setAttribute("enseignant",enseignant);
        request.getRequestDispatcher("enseignant.jsp").forward(request,response);

    }

    private void deleteEnseign(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        String cin  = request.getParameter("cin");
        Enseignant enseignant = enseignantDAO.getEnseignantByCin(cin);
        try {
            if (enseignant != null)
            {
            enseignantDAO.supprimerEnseignant(cin);
            }
            else {
                throw new ServletException("Enseignant n'est pas trouver");
            }
        }catch (
                Exception exception
        )
        {
            exception.printStackTrace();
        }
        response.sendRedirect(request.getContextPath()+"/enseignants");
    }

    private void updateEnseign(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String cin = request.getParameter("cin");
        String nom = request.getParameter("nom");
        String specialites = request.getParameter("specialites");
        int idDepartement = Integer.parseInt(request.getParameter("idDepartement"));
        Departement departement = departementDAO.listerDepartementParId(idDepartement);

        enseignantDAO.addSpecialities(cin,specialites);
        Enseignant enseignant = new Enseignant(id, cin, nom, departement);
        enseignantDAO.modifierEnseignant(enseignant);

        response.sendRedirect("enseignant?action=list");
    }

    private void addEnseign(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cin = request.getParameter("cin");
        String nom = request.getParameter("nom");
        String specialite = request.getParameter("specialite");
        int idDepartement = Integer.parseInt(request.getParameter("departement"));

        Departement departement = departementDAO.listerDepartementParId(idDepartement);
        Enseignant enseignant = new Enseignant(cin, nom, departement);
        enseignant.getSpecialites().add(specialite);

        enseignantDAO.AjouterEnseignant(enseignant);

        response.sendRedirect(request.getContextPath() + "/enseignants?specialite=" + specialite);
    }

    private void enseignBySpecial(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String specialite = request.getParameter("specialite");
        List<Enseignant> enseignants = enseignantDAO.getEnseignantsBySpecialite(specialite);
        request.setAttribute("enseignants", enseignants);
        request.setAttribute("specialite", specialite);
        request.getRequestDispatcher("/listeEnseignants.jsp").forward(request, response);
    }


    private void enseignByDept(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idDepart = Integer.parseInt(request.getParameter("id"));
        Departement departement = departementDAO.listerDepartementParId(idDepart);
        List<Enseignant> enseignants = enseignantDAO.getEnseignantsByDepartement(departement);
        request.setAttribute("departement", departement);
        request.setAttribute("enseignants", enseignants);
        RequestDispatcher dispatcher = request.getRequestDispatcher("enseignantsParDepartement.jsp");
        dispatcher.forward(request, response);
    }
    private void updateDept(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        int id = Integer.parseInt(request.getParameter("id"));
        String nom = request.getParameter("nom");
//        String chef = request.getParameter("chef");
//        DepartementDAO departementDAO = new DepartementDAO();
        Departement departement = departementDAO.listerDepartementParId(id);
        departement.setNomDepartement(nom);
//        departement.setChef();
        departementDAO.updateDepartement(departement);
        response.sendRedirect("liste_departements.jsp");

    }

    private void addDept(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String nom = request.getParameter("nom");
        Departement departement = new Departement(nom);
        departementDAO.ajouterDepart(departement);
        response.sendRedirect(request.getContextPath()+"/depts");
    }

    private void listerDepts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Departement> departements = departementDAO.listerTousDepartements();
        request.setAttribute("depts",departements);
        RequestDispatcher dispatcher = request.getRequestDispatcher("departements.jsp");
        dispatcher.forward(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }

    public void destroy() {
    }
}