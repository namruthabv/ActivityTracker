package webapp;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

// test jenkins
@WebServlet(urlPatterns = "/login.do")
public class LoginServlet extends HttpServlet{

	UserValid uservalid = new UserValid();
	// Testing
	//ToDoList todolist = new ToDoList();
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
		req.setAttribute("mesg",req.getParameter("mesg"));
		System.out.println(" coming hre" + req.getParameter("mesg"));
		req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req,resp);

		///String name = req.getParameter("name");
		///req.setAttribute("name" , name);
		//req.setAttribute("feedback", req.getParameter("feedback"));
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
		System.out.println(" coming --*****---" + req.getAttribute("mesg"));
		/*
		if(req.getAttribute("mesg") != null){
			//req.setAttribute("mesg",req.getAttribute("mesg"));
			System.out.println(" coming -----" + req.getAttribute("mesg"));
			req.getRequestDispatcher("/WEB-INF/views/welcome.jsp").forward(req,resp);
		}
		*/

		String name = req.getParameter("name");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		String dbName = null;
		String dbPassword = null;
		String dbEmail= null;

		String sql = "select * from registration where name = ? and password = ? ";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","root123");
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,name);
			ps.setString(2,password);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dbName=rs.getString("name");
				dbPassword=rs.getString("password");
				dbEmail=rs.getString("email");
			}
			if(name.equals(dbName) && password.equals(dbPassword))
			{
				System.out.println("if -- if");
				req.getSession().setAttribute("name", name);
				System.out.println(" if if if if " + dbEmail);
				req.getSession().setAttribute("email", dbEmail);
				req.setAttribute("password", password);
				resp.sendRedirect("/activity-list.do");
			}
			else if(!name.equals(dbName) && !password.equals(dbPassword)){
				System.out.println("else if else if");
				req.setAttribute("errorMessage", "Invalid Credentials, Please enter a valid Username and password or Register as new user!!");
				req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
			}
			else{
				System.out.println("else only only");
				req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/*
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {

		String name = req.getParameter("name");
		String password = req.getParameter("password");

		boolean isValidUser = uservalid.userValidation(name, password);
		if(isValidUser) {
			req.getSession().setAttribute("name", name);
			req.setAttribute("password", password);
			resp.sendRedirect("/activity-list.do");
		}
		else if(req.getParameter("mesg") != null){
			req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
		}
		else{
			req.setAttribute("errorMessage", "Invalid Credentials!!");
			req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
		}
	}
	*/
}
