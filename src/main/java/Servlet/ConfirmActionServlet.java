package Servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * タスク情報とアクション（削除・更新）の属性を持ってConfirmAction.jspへ遷移します。
 */
@WebServlet("/ConfirmActionServlet")
public class ConfirmActionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // リクエストから必要なパラメータを取得
        String id = request.getParameter("id");
        String title = request.getParameter("title");
        String task = request.getParameter("task");
        String limitdate = request.getParameter("limitdate");
        String lastupdate = request.getParameter("lastupdate");
        String userid = request.getParameter("userid");
        String label = request.getParameter("label");
        String action = request.getParameter("action");

        // 取得したパラメータをリクエスト属性に設定
        request.setAttribute("id", id);
        request.setAttribute("title", title);
        request.setAttribute("task", task);
        request.setAttribute("limitdate", limitdate);
        request.setAttribute("lastupdate", lastupdate);
        request.setAttribute("userid", userid);
        request.setAttribute("label", label);
        request.setAttribute("action", action);

        // ConfirmAction.jspにフォワード
        request.getRequestDispatcher("/WEB-INF/ConfirmAction.jsp").forward(request, response);
    }
}
