package Servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import todo.web.Todo;
import todo.web.TodoDAO;

/**
 * 指定されたidをもとにタスク情報の
 * 詳細ページを表示するサーブレット
 */
@WebServlet("/DetailServlet")
public class DetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetailServlet() {
        super();
        // コンストラクタ
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // リクエストからIDを取得
        int id = Integer.parseInt(request.getParameter("id"));
        
        Todo dto;
        
        if (id >= 1) {
            try (TodoDAO dao = new TodoDAO()) {
                // IDに基づいてTodoの詳細を取得
                dto = dao.detail(id);
            } catch (Exception e) {
                // エラーが発生した場合はServletExceptionをスロー
                throw new ServletException(e);
            }
        } else {
            // 無効なIDの場合、新しいTodoオブジェクトを作成
            dto = new Todo();
            dto.setId(0);
        }
        
        // 取得したTodoオブジェクトをリクエスト属性に設定
        request.setAttribute("todo", dto);
        // 詳細表示ページにフォワード
        jakarta.servlet.RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/detail.jsp");
        rd.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // POSTリクエストの場合、GETメソッドを呼び出す
        doGet(request, response);
    }
}

