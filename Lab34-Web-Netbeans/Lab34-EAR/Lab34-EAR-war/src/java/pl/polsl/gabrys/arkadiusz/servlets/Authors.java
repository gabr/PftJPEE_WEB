/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.gabrys.arkadiusz.servlets;

import com.sun.xml.rpc.processor.modeler.j2ee.xml.string;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.polsl.gabrys.arkadiusz.DatabaseManagerLocal;
import pl.polsl.gabrys.arkadiusz.model.Author;

/**
 *
 * @author arkad_000
 */
@WebServlet(name = "Authors", urlPatterns = {"/Authors"})
public class Authors extends HttpServlet {
    
    @EJB
    private DatabaseManagerLocal db;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Enumeration<String> parameters = request.getParameterNames();
        
        // no params, show whole list
        if (!parameters.hasMoreElements()) {
            showList(request, response);
            return;
        }
        
        // get action name
        String action = parameters.nextElement();
        
        // perform action
        switch (action) {
            case "details":
                showDetails(request, response);
                return;
            case "update":
                updateAuthor(request, response);
                return;
            case "delete":
                deleteAuthor(request, response);
                return;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Wrong action name for Authors");
                return;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void showList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<Author> authors = db.findAllAuthors();
        
        request.setAttribute("authors", authors);
        request.getRequestDispatcher("/authors.jsp").include(request, response);
    }

    private void showDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String[] values = request.getParameterValues("details");
        
        if (values.length < 1 || values[0].trim().equals("")) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Details id not specified");
                return;
        }
        
        Long id = Long.parseLong(values[0]);
        Author author = db.findAuthorById(id);
        
        if (author == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No Author with given id found");
            return;
        }        
        request.setAttribute("author", author);
        request.setAttribute("books", author.getBooks());
        request.getRequestDispatcher("/authorDetails.jsp").include(request, response);
    }
    
    private void deleteAuthor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String[] values = request.getParameterValues("delete");
        
        if (values.length < 1 || values[0].trim().equals("")) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Delete id not specified");
                return;
        }
        
        Long id = Long.parseLong(values[0]);
        db.removeAuthor(id);
        
        showList(request, response);
    }
    
    private void updateAuthor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ArrayList<String> requiredParameters = new ArrayList<String>();
        requiredParameters.add("name");
        requiredParameters.add("lastName");
        
        Map<String, String[]> params = request.getParameterMap();
        
        String[] values = params.get("update");
        
        if (values.length < 1 || values[0].trim().equals("")) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Update id not specified");
                return;
        }
        
        Long id = Long.parseLong(values[0]);
        Author author = db.findAuthorById(id);
        
        if (author == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No Author with given id found");
            return;
        }        
        
        if (params.keySet().containsAll(requiredParameters)) {
            db.mergeAuthor(id, params.get("name")[0], params.get("lastName")[0]);
            showList(request, response);
        } else {
            request.setAttribute("author", author);
            request.getRequestDispatcher("/authorUpdate.jsp").include(request, response);
        }
    }
}
