package Servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import todo.web.Todo;
import todo.web.TodoDAO;

/**
 * データベース上からタスク情報を獲得して
 * list.jspへ遷移するサーブレットです。
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // GETリクエストが来た場合、通常は何もせずにエラーとする場合が多いですが、ここではPOSTで処理するようにします。
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // セッションからユーザー名を取得し、ログインしているかを確認する
        HttpSession session = request.getSession(false); // セッションがない場合は新規作成しない
        if (session == null || session.getAttribute("username") == null) {
            // ログインしていない場合はログインページにリダイレクトする
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // ログイン済みの場合、TODOリストを取得してリクエスト属性に設定する
        try (TodoDAO dao = new TodoDAO()) {
            List<Todo> list = dao.todoList();
            request.setAttribute("todoList", list);
        } catch (Exception e) {
            System.out.println(e);
            throw new ServletException(e);
        }

        // リストの表示ページへフォワードする
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/list.jsp");
        rd.forward(request, response);
    }
}

