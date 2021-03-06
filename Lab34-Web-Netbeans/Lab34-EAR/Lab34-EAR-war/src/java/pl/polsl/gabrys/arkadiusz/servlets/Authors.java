package pl.polsl.gabrys.arkadiusz.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import pl.polsl.gabrys.arkadiusz.DatabaseManagerLocal;
import pl.polsl.gabrys.arkadiusz.model.Author;
import pl.polsl.gabrys.arkadiusz.model.Book;

/**
 * Servlet for managing all Authors actions
 * @author arkad_000
 * @version 1.0
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
            case "add":
                addAuthor(request, response);
                return;
            case "find":
                findAuthor(request, response);
                return;
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
                sendError(request, response, "Wrong action name for Authors");
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

    /**
     * Shows list view with all Authors
     * @param request the request object
     * @param response the response object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void showList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<Author> authors = db.findAllAuthors();
        
        request.setAttribute("authors", authors);
        request.getRequestDispatcher("/authors.jsp").include(request, response);
    }
    
    /**
     * Finds Author or shows books list
     * @param request the request object
     * @param response the response object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs 
     */
    private void findAuthor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Map<String, String[]> params = request.getParameterMap();
        
        List<Author> authors = db.findAuthorsByName(params.get("name")[0]);
        
        if (authors.isEmpty()) {
            showList(request, response);
        } else {
            request.setAttribute("author", authors.get(0));
            request.getRequestDispatcher("/authorDetails.jsp").include(request, response);
        }
    }
    
    /**
     * Adds new Author or shows add Author form
     * @param request the request object
     * @param response the response object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs 
     */
    private void addAuthor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ArrayList<String> requiredParameters = new ArrayList<String>();
        requiredParameters.add("name");
        requiredParameters.add("lastName");
        
        Map<String, String[]> params = request.getParameterMap();
        
        if (params.keySet().containsAll(requiredParameters)) {
            String name = params.get("name")[0];
            String lastName = params.get("lastName")[0];
            
            if (name.trim().isEmpty()) {
                sendError(request, response, "Author name cannot be empty");
                return;
            }
            
            if (lastName.trim().isEmpty()) {
                sendError(request, response, "Author last name cannot be empty");
                return;
            }
            
            db.persistAuthor(name, lastName);
            showList(request, response);
        } else {
            request.getRequestDispatcher("/authorAdd.jsp").include(request, response);
        }
    }

    /**
     * Shows details for Author listing all Books
     * @param request the request object
     * @param response the response object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void showDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String[] values = request.getParameterValues("details");
        
        if (values.length < 1 || values[0].trim().equals("")) {
                sendError(request, response, "Details id not specified");
                return;
        }
        
        Long id = Long.parseLong(values[0]);
        Author author = db.findAuthorById(id);
        
        if (author == null) {
            sendError(request, response, "No Author with given id found");
            return;
        }        
        request.setAttribute("author", author);
        request.setAttribute("books", author.getBooks());
        request.getRequestDispatcher("/authorDetails.jsp").include(request, response);
    }
    
    /**
     * Deletes Author
     * @param request the request object
     * @param response the response object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void deleteAuthor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String[] values = request.getParameterValues("delete");
        
        if (values.length < 1 || values[0].trim().equals("")) {
                sendError(request, response, "Delete id not specified");
                return;
        }
        
        Long id = Long.parseLong(values[0]);
        db.removeAuthor(id);
        
        showList(request, response);
    }
    
    /**
     * Updates Atuhor or shows update Author form
     * @param request the request object
     * @param response the response object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void updateAuthor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ArrayList<String> requiredParameters = new ArrayList<String>();
        requiredParameters.add("name");
        requiredParameters.add("lastName");
        
        Map<String, String[]> params = request.getParameterMap();
        
        String[] values = params.get("update");
        
        if (values.length < 1 || values[0].trim().equals("")) {
                sendError(request, response, "Update id not specified");
                return;
        }
        
        Long id = Long.parseLong(values[0]);
        Author author = db.findAuthorById(id);
        
        if (author == null) {
            sendError(request, response, "No Author with given id found");
            return;
        }        
        
        if (params.keySet().containsAll(requiredParameters)) {
            String name = params.get("name")[0];
            String lastName = params.get("lastName")[0];
            
            if (name.trim().isEmpty()) {
                sendError(request, response, "Author name cannot be empty");
                return;
            }
            
            if (lastName.trim().isEmpty()) {
                sendError(request, response, "Author last name cannot be empty");
                return;
            }
            
            db.mergeAuthor(id, name, lastName);
            showList(request, response);
        } else {
            request.setAttribute("author", author);
            request.getRequestDispatcher("/authorUpdate.jsp").include(request, response);
        }
    }
    
    /**
     * Shows error page
     * @param request the request object
     * @param response I/nse the response object
     * @param message the error message
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if aO error occurs
     */
    private void sendError(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        
        JspException e = new JspException(message);
        request.setAttribute("error", e);
        RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
        rd.forward(request, response);
    }
}
