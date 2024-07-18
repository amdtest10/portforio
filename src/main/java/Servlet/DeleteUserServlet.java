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


/**
 * 指定されたユーザー情報を削除するサーブレット
 */
@WebServlet("/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // フォームから送信されたユーザー名を取得
        String username = request.getParameter("userid");
        System.out.println(username);
        
        try (UserDAO userDao = new UserDAO()) {
            // 指定されたユーザーを削除
            userDao.deleteUser(username);
            // 更新後の全ユーザー情報を取得
            List<User> users = userDao.getAllUsers();
            System.out.println("ユーザー情報を取得: " + users.size() + "件");
            // 各ユーザー名を出力
            for (User user : users) {
                System.out.println(user.getUsername());
            }
            // ユーザー情報をリクエスト属性に設定
            request.setAttribute("users", users);
            // 管理者ページに転送
            request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
        } catch (SQLException e) {
            // SQLエラーが発生した場合はServletExceptionをスロー
            throw new ServletException("ユーザー削除中にエラーが発生しました", e);
        }
    }
}

