package pl.polsl.gabrys.arkadiusz.servlets;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.IOException;
import java.text.ParseException;
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
import pl.polsl.gabrys.arkadiusz.model.Book;

/**
 * Servlet for managing all Books actions
 * @author arkad_000
 * @version 1.0
 */
@WebServlet(name = "Books", urlPatterns = {"/Books"})
public class Books extends HttpServlet {
    
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
        try
        {
        switch (action) {
            case "add":
                addBook(request, response);
                return;
            case "find":
                findBook(request, response);
                return;
            case "details":
                showDetails(request, response);
                return;
            case "update":
                updateBook(request, response);
                return;
            case "delete":
                deleteBook(request, response);
                return;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Wrong action name for Books");
                return;
        }
        } catch (ParseException ex) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error while parsing data");
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
     * Shows list view with all Books
     * @param request the request object
     * @param response thre response object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void showList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<Book> books = db.findAllBooks();
        
        request.setAttribute("books", books);
        request.getRequestDispatcher("/books.jsp").include(request, response);
    }
    
    /**
     * Finds Book or shows books list
     * @param request the request object
     * @param response thre response object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs 
     */
    private void findBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Map<String, String[]> params = request.getParameterMap();
        
        List<Book> books = db.findBooksByTitle(params.get("name")[0]);
        
        if (books.isEmpty()) {
            showList(request, response);
        } else {
            request.setAttribute("book", books.get(0));
            request.getRequestDispatcher("/bookDetails.jsp").include(request, response);
        }
    }
    
    /**
     * Adds new Book or shows add Book form
     * @param request the request object
     * @param response thre response object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs 
     * @throws ParseException if date format is wrong
     */
    private void addBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        
        ArrayList<String> requiredParameters = new ArrayList<String>();
        requiredParameters.add("title");
        requiredParameters.add("pages");
        requiredParameters.add("releaseDate");
        requiredParameters.add("authorId");
        
        Map<String, String[]> params = request.getParameterMap();
        
        if (params.keySet().containsAll(requiredParameters)) {
            String title = params.get("title")[0];
            Long pages = Long.parseLong(params.get("pages")[0]);
            DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
            Date releaseDate = df.parse(params.get("releaseDate")[0]);
            Long authorId = Long.parseLong(params.get("authorId")[0]);
            
            db.persistBook(title, pages, releaseDate, authorId);
            
            showList(request, response);
        } else {
            request.getRequestDispatcher("/bookAdd.jsp").include(request, response);
        }
    }

    /**
     * Shows details for Book listing all Books
     * @param request the request object
     * @param response thre response object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void showDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String[] values = request.getParameterValues("details");
        
        if (values.length < 1 || values[0].trim().equals("")) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Details id not specified");
                return;
        }
        
        Long id = Long.parseLong(values[0]);
        Book book = db.findBookById(id);
        
        if (book == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No Book with given id found");
            return;
        }        
        request.setAttribute("book", book);
        request.getRequestDispatcher("/bookDetails.jsp").include(request, response);
    }
    
    /**
     * Deletes Book
     * @param request the request object
     * @param response thre response object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void deleteBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String[] values = request.getParameterValues("delete");
        
        if (values.length < 1 || values[0].trim().equals("")) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Delete id not specified");
                return;
        }
        
        Long id = Long.parseLong(values[0]);
        db.removeBook(id);
        
        showList(request, response);
    }
    
    /**
     * Updates Book or shows update Book form
     * @param request the request object
     * @param response thre response object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws ParseException if date format is wrong
     */
    private void updateBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        
        ArrayList<String> requiredParameters = new ArrayList<String>();
        requiredParameters.add("title");
        requiredParameters.add("pages");
        requiredParameters.add("releaseDate");
        requiredParameters.add("authorId");
        
        Map<String, String[]> params = request.getParameterMap();
        
        String[] values = params.get("update");
        
        if (values.length < 1 || values[0].trim().equals("")) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Update id not specified");
                return;
        }
        
        Long id = Long.parseLong(values[0]);
        Book book = db.findBookById(id);
        
        if (book == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No Book with given id found");
            return;
        }        
        
        if (params.keySet().containsAll(requiredParameters)) {
            String title = params.get("title")[0];
            Long pages = Long.parseLong(params.get("pages")[0]);
            DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
            Date releaseDate = df.parse(params.get("releaseDate")[0]);
            Long authorId = Long.parseLong(params.get("authorId")[0]);
            
            db.mergeBook(id, title, pages, releaseDate, authorId);
            
            showList(request, response);
        } else {
            request.setAttribute("book", book);
            request.setAttribute("author", book.getAuthor());
            request.getRequestDispatcher("/bookUpdate.jsp").include(request, response);
        }
    }
}
