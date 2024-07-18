package Servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import todo.web.UserDAO;

/**
 *  新規ユーザー情報を登録するサーブレットです。
 */
@WebServlet("/NewServlet")
public class NewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // GETリクエストの場合、POSTメソッドを呼び出す
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // フォームから送信されたユーザー名、パスワード、および確認用パスワードを取得
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmpassword = request.getParameter("confirm_password");
        
        // 入力値のバリデーションを行う（省略）
        if (password.equals(confirmpassword)) {
            try (UserDAO userDao = new UserDAO()) {
                // ユーザーを登録し、デフォルトの役割を追加
                userDao.registerUser(username, password);
                userDao.addUserRole(username, "normal");
            } catch (SQLException | NamingException e) {
                // SQLまたはNamingのエラーをキャッチし、スタックトレースを出力
                e.printStackTrace();
            } catch (Exception e) {
                // その他の例外もキャッチ
                e.printStackTrace();
            }
            // 登録後にログイン画面などに遷移させる場合は、適切なURLにリダイレクトする
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        } else {
            // パスワードが一致しない場合、エラーページに転送
            request.getRequestDispatcher("/Regi_error.jsp").forward(request, response);
        }
    }
}

