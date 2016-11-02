package servlet;

import database.CommentSQLAccess;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by llei on 2016/11/2.
 */
public class GetInitialDataServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");
        CommentSQLAccess.getTypeNumOfAllBrand();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}