package Servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import todo.web.TodoDAO;

/**
 * タスク情報を削除する際に使用するサーブレット
 */
@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteServlet() {
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
        
        // リクエストから削除対象のIDを取得
        String paramId = request.getParameter("id");
        
        try (TodoDAO dao = new TodoDAO()) {
            // Todoアイテムを削除
            dao.delete(Integer.parseInt(paramId));
        } catch (Exception e) {
            // エラーが発生した場合はServletExceptionをスロー
            throw new ServletException(e);
        }
        
        // 削除後、検索サーブレットにフォワード
        RequestDispatcher rd = request.getRequestDispatcher("/SearchServlet");
        rd.forward(request, response);
    }
}

