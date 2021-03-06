package ru.alikovzaur.library.servlets;

import ru.alikovzaur.library.controllers.BookController;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet(name = "ShowImage", urlPatterns = "/ShowImage")
public class ShowImage extends HttpServlet {

    @Inject
    private BookController bookController;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("image/jpeg");
        try (OutputStream out = resp.getOutputStream()) {
            int id = Integer.valueOf(req.getParameter("id"));
            byte[] image = bookController.getImage(id);
            if (image != null){
                resp.setContentLength(image.length);
                out.write(image);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
