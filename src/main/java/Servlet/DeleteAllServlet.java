package Servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import todo.web.TodoDAO;

/**
 * タスク情報を一括削除します（管理者権限のみ使用可能）
 */
@WebServlet("/DeleteAllServlet")
public class DeleteAllServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteAllServlet() {
        super();
        // コンストラクタ
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // GETリクエストが来た場合、POSTメソッドを呼び出す
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try (TodoDAO dao = new TodoDAO()) {
            // TodoDAOを使ってすべてのTODOを削除
            dao.deleteAll(); 
            // 変更をデータベースにコミット
            dao.commit(); 
        } catch (Exception e) {
            // エラーが発生した場合はServletExceptionをスロー
            throw new ServletException(e);
        }
        // 削除後、検索サーブレットにフォワード
        jakarta.servlet.RequestDispatcher rd = request.getRequestDispatcher("/SearchServlet");
        rd.forward(request, response);
    }
}

