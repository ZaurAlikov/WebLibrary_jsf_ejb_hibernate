package ru.alikovzaur.library.servlets;

import ru.alikovzaur.library.controllers.SearchController;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(name = "ShowImage", urlPatterns = "/ShowImage")
public class ShowImage extends HttpServlet {

    @Inject
    private SearchController searchController;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("image/jpeg");
        OutputStream out = resp.getOutputStream();
        try{
            long id = Long.valueOf(req.getParameter("id"));
            byte[] image = searchController.getImage(id);
            resp.setContentLength(image.length);
            out.write(image);
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service(req, resp);
    }
}
