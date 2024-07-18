<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="todo.web.Todo"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TODOタスク管理</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
	crossorigin="anonymous">
<style>
/* Custom CSS to remove borders */
.no-border {
	border: none;
}
</style>
</head>
<body>
	<div class="container">
		<h1>タスク詳細</h1>
		<%
		Todo todo = (Todo) request.getAttribute("todo");
		String loginUser = (String) session.getAttribute("username");
		String userRole = (String) session.getAttribute("role");
		String errormessage = (String) request.getAttribute("errormessage");
		%>
		<p>
			ログインユーザ : <%=loginUser%>
		</p>

		<% if (errormessage != null && !errormessage.isEmpty()) { %>
		<div class="alert alert-danger">
			<%= errormessage %>
		</div>
		<% } %>

		<form action="/TASKLIST/ConfirmActionServlet" method="POST">
			<table class="table no-border">
				<tr>
					<th>番号</th>
					<%
					if (todo.getId() > 0) {
					%>
					<td><%=todo.getId()%></td>
					<%
					} else {
					%>
					<td>新規</td>
					<%
					}
					%>
				</tr>
				<tr>
					<th>タイトル</th>
					<td><input type="text" name="title"
						value="<%=todo.getTitle()%>" size="40" /></td>
				</tr>
				<tr>
					<th>タスク</th>
					<td><input type="text" name="task" value="<%=todo.getTask()%>"
						maxlength="128" size="60" /></td>
				</tr>
				<tr>
					<th>期限</th>
					<td><input type="text" name="limitdate"
						value="${ todo.limitdate != null ? todo.limitdate.toString().substring(0, 10) : '' }"
						pattern="\d{4}-\d{2}-\d{2}" title="YYYY-MM-DD の形式で入力してください"
						size="10" required /></td>
				</tr>
				<tr>
					<th>ユーザID</th>
					<%
					if (todo.getId() > 0) {
					%>
					<td><input type="text" name="userid"
						value="<%=todo.getUserid()%>" size="8" readonly /></td>
					<%
					} else {
					%>
					<td><input type="text" name="userid" value="<%=loginUser%>"
						size="8" readonly /></td>
					<%
					}
					%>
				</tr>
				<tr>
					<th>状況</th>
					<td><select name="status" class="form-control">
							<option value="0" <%=todo.getStatus() == 0 ? "selected" : ""%>>未着手</option>
							<option value="1" <%=todo.getStatus() == 1 ? "selected" : ""%>>着手</option>
							<option value="2" <%=todo.getStatus() == 2 ? "selected" : ""%>>完了</option>
							<option value="3" <%=todo.getStatus() == 3 ? "selected" : ""%>>保留</option>
					</select></td>
				</tr>
			</table>

			<input type="hidden" name="id" value="<%=todo.getId()%>" />
			<table>
				<tr>
					<%-- 登録ボタン（新規登録または更新） --%>
					<input type="submit" class="btn btn-primary" name="action"
						value="${ todo.getId() > 0 ? '更新' : '新規登録' }" />
		</form>
		<%-- 削除ボタン（管理者のみ表示） --%>
		<%
		if (todo.getId() > 0 && "admin".equals(userRole)) {
		%>
		<form action="/TASKLIST/ConfirmActionServlet" method="POST">
			<input type="hidden" name="id" value="<%=todo.getId()%>" /> <input
				type="hidden" name="title" value="<%=todo.getTitle()%>" /> <input
				type="hidden" name="task" value="<%=todo.getTask()%>" /> <input
				type="hidden" name="limitdate" value="<%=todo.getLimitdate()%>" />
			<input type="hidden" name="userid" value="<%=todo.getUserid()%>" />
			<input type="hidden" name="status" value="<%=todo.getStatus()%>" />
			<input type="hidden" name="action" value="削除" /> <input
				type="submit" value="削除" class="btn btn-success btn-margin" />
		</form>
		<%
		}
		%>

		<form action="/TASKLIST/SearchServlet" method="POST">
			<input type="submit" value="戻る" class="btn btn-secondary btn-margin" />
		</form>
		</tr>
		</table>
	</div>
</body>
</html>


