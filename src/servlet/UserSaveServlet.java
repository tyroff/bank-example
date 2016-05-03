package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Role;
import domain.User;
import service.UserService;

public class UserSaveServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer id = null;
		String login = null;
		Role role = null;
		try {
			role = Role.valueOf(request.getParameter("role"));
			login = request.getParameter("login");
			if(!Pattern.matches("\\w+", login)) {
				login = null;
			}
			id = Integer.parseInt(request.getParameter("id"));
		} catch(NullPointerException | IllegalArgumentException e) {}
		if(login != null && role != null) {
			User user = new User();
			user.setLogin(login);
			user.setRole(role);
			if(id != null) {
				user.setId(id);
			}
			UserService service = null;
			try {
				service = new UserService();
				service.save(user);
			} catch(SQLException e) {
				throw new ServletException(e);
			} finally {
				if(service != null) {
					service.close();
				}
			}
		}
		response.sendRedirect(request.getContextPath() + "/index.html");
	}
}
