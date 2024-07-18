package Servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import todo.web.User;
import todo.web.UserDAO;

/**
 *  ログイン時にデータベース上のユーザー情報とロールを
 *  チェックして、それぞれのロールのtop画面へ遷移する
 *  サーブレット
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDao;

    public void init() throws ServletException {
        userDao = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("j_username");
        String password = request.getParameter("j_password");
        String confirmpassword = request.getParameter("confirm_password");
        
        try {
            if (password.equals(confirmpassword) && userDao.authenticateUser(username, password)) {
                // ログイン成功時の処理
                String userRole = userDao.getUserRole(username);
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                session.setAttribute("role", userRole);

                // ロールに応じたページへの遷移
                if ("admin".equals(userRole)) {
          
                    List<User> users = userDao.getAllUsers();
                    System.out.println("ユーザー情報を取得: " + users.size() + "件");
                    for (User user : users) {
                        System.out.println(user.getUsername());
                    }
                    request.setAttribute("users", users);
                    request.getRequestDispatcher("/WEB-INF/AdminTopPage.jsp").forward(request, response);
                } else {
                	request.getRequestDispatcher("/WEB-INF/top.jsp").forward(request, response);
                }
            } else {
                // ログイン失敗時の処理
                
                request.setAttribute("message", "ユーザー名またはパスワードが間違っています。");
                request.getRequestDispatcher("/login_error.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Error in authentication", e);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
    
    public void destroy() {
        userDao.close(); // データベース接続をクローズ
    }
}
