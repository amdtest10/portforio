<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="todo.web.User"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ユーザー管理</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<a class="navbar-brand" href="#">タスク管理アプリケーション</a>
	</nav>
	<div class="container mt-5">
		<h1 class="mb-4">ユーザー管理</h1>
		<table class="table table-bordered">
			<thead class="thead-light">
				<tr>
					<th>ユーザー名</th>
					<th>ロール</th>
					<th>権限操作</th>
					<th>ユーザー削除</th>
				</tr>
			</thead>
			<tbody>
				<%
				List<User> users = (List<User>) request.getAttribute("users");
				if (users != null) {
					for (User user : users) {
				%>
				<tr>
					<td><%= user.getUsername() %></td>
					<td><%= user.getRole() %></td>
					<td>
						<form action="/TASKLIST/UpdateUserRoleServlet" method="POST" class="form-inline">
							<input type="hidden" name="userid" value="<%= user.getUsername() %>" /> 
							<select name="role" class="form-control mr-2">
								<option value="user" <%= "user".equals(user.getRole()) ? "selected" : "" %>>ユーザー</option>
								<option value="admin" <%= "admin".equals(user.getRole()) ? "selected" : "" %>>管理者</option>
							</select> 
							<input type="submit" value="更新" class="btn btn-primary" />
						</form>
					</td>
					<td>
						<form action="/TASKLIST/DeleteUserServlet" method="POST">
							<input type="hidden" name="userid" value="<%= user.getUsername() %>" /> 
							<input type="submit" value="削除" class="btn btn-danger" />
						</form>
					</td>
				</tr>
				<%
					}
				}
				%>
			</tbody>
		</table>
		<div class="mt-4">
			<form action="/TASKLIST/logout.jsp" method="POST" class="d-inline">
				<input type="submit" value="ログアウト" class="btn btn-secondary" />
			</form>
			<form action="/TASKLIST/SearchServlet" method="GET" class="d-inline ml-2">
				<input type="submit" value="タスク一覧へ" class="btn btn-primary" />
			</form>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFyDVtEEiL6kMOre+A5t6bL6ANslsU8wXd5gUENOMr5uZf5M1tLdfV" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOUHJwFQHAlc6xXjwZjP3tw9KfdJfSVp9F/a1rvL6sNYkMf" crossorigin="anonymous"></script>
</body>
</html>


