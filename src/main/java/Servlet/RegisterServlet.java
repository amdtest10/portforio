package Servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import todo.web.Todo;
import todo.web.TodoDAO;

/**
 * タスク情報の新規登録・更新処理を行うサーブレット
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// GETリクエストが発生した場合の処理
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("title");
		String task = request.getParameter("task");
		String inputLimitdate = request.getParameter("limitdate");
		String userid = request.getParameter("userid");
		int status = Integer.parseInt(request.getParameter("status"));
		
		Todo dto = new Todo();
		dto.setId(id);
		dto.setTitle(title);
		dto.setTask(task);
		dto.setInputLimitdate(inputLimitdate);
		dto.setUserid(userid);
		dto.setStatus(status);
		
		// バリデーションチェック
		if (!dto.valueCheck()) {
			request.setAttribute("errormessage", String.join(", ", dto.getErrorMessages()));
			request.setAttribute("todo", dto);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/detail.jsp");
			rd.forward(request, response);
			return;
		}
		
		try (TodoDAO dao = new TodoDAO()) {
			if (id == 0) {
				dao.registerInsert(dto);
			} else {
				dao.registerUpdate(dto);
			}
		} catch (Exception e) {
			// 例外が発生した場合の処理
			request.setAttribute("errormessage", "データベースエラーが発生しました: " + e.getMessage());
			request.setAttribute("todo", dto);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/detail.jsp");
			rd.forward(request, response);
			return;
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/SearchServlet");
		rd.forward(request, response);
	}
}
