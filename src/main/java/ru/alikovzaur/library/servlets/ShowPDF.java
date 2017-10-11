package ru.alikovzaur.library.servlets;

import ru.alikovzaur.library.controllers.BookController;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

@WebServlet(name = "ShowPDF", urlPatterns = "/ShowPdfFile")
public class ShowPDF extends HttpServlet {
    @Inject
    private BookController bookController;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/pdf");
        OutputStream out = resp.getOutputStream();
        try{
            String action = req.getParameter("action");
            long id = Long.valueOf(req.getParameter("id"));
            byte[] pdf = bookController.getPdf(id);
            if(action.equals("read")){
                resp.setContentLength(pdf.length);
            } else if(action.equals("download")){
                String name = URLEncoder.encode(req.getParameter("name"), "UTF-8").replace("+"," ");
                resp.setHeader("Content-Disposition", "attachment;filename=" + name + ".pdf");
            }
            out.write(pdf);
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
