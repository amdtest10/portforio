package Servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import todo.web.User;
import todo.web.UserDAO;

@WebServlet("/UpdateUserRoleServlet")
public class UpdateUserRoleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // フォームから送信されたユーザー名と新しい役割を取得
        String username = request.getParameter("userid");
        String newRole = request.getParameter("role");

        try (UserDAO userDao = new UserDAO()) {
            // ユーザーの役割を更新
            userDao.updateUserRole(username, newRole);
            // 更新後の全ユーザー情報を取得
            List<User> users = userDao.getAllUsers();
            // 取得したユーザー情報をリクエスト属性に設定
            request.setAttribute("users", users);
            // 管理者ページに転送
            request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
        } catch (SQLException e) {
            // 例外が発生した場合はエラーメッセージを表示
            throw new ServletException("ユーザー役割の更新中にエラーが発生しました", e);
        }
    }
}
