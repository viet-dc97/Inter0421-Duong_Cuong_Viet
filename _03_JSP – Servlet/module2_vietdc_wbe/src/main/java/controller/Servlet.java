package controller;

import model.bean.EducationDegree;
import utils.JdbcHelper;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Servlet", urlPatterns = "")
public class Servlet extends javax.servlet.http.HttpServlet {
    JdbcHelper jdbcHelper = new JdbcHelper();
    public List<EducationDegree> loadList(){
        ResultSet rs;
        ArrayList<EducationDegree> list = new ArrayList<>();
        try{
            rs = jdbcHelper.query("SELECT * FROM education_degree");
            while (rs.next()){
                EducationDegree edu = new EducationDegree();
                edu.setId(rs.getInt(1));
                edu.setName(rs.getString(2));
                list.add(edu);
            }
            return list;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        List<EducationDegree> list = loadList();
        request.setAttribute("list", list);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
        requestDispatcher.forward(request,response);
    }
}
