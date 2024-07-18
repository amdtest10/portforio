package Servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import todo.web.User;
import todo.web.UserDAO;

@WebServlet("/adminPageServlet")
public class adminPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// セッションからユーザー名とロールを取得する
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		String userRole = (String) session.getAttribute("role");

		// ログインしているかどうかを確認する
		if (username == null || userRole == null) {
			// ログインしていない場合、ログインページにリダイレクトするなどの処理を行う
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}

		// ログイン済みで、かつ管理者ロールの場合のみ、admin.jsp に遷移する
		if ("admin".equals(userRole)) {
			try (UserDAO userDao = new UserDAO()) {
				// ユーザー情報を取得する例（実際のデータベースアクセスに応じて適宜変更してください）
				List<User> users = userDao.getAllUsers(); // ユーザーのリストを取得するメソッドの例

				// リクエスト属性にユーザーのリストをセットする
				request.setAttribute("users", users);

				// admin.jsp にフォワードする
				request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);

			} catch (SQLException e) {
				e.printStackTrace();
				throw new ServletException("Error in retrieving user information", e);
			}
		} else {
			// 管理者以外の場合、適切なエラーメッセージを設定してログインページにリダイレクトするなどの処理を行う
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// doGetメソッドに処理を委譲する
		doGet(request, response);
	}
}
