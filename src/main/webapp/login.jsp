<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログインフォーム</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
	crossorigin="anonymous">
<script>
	function togglePasswordVisibility() {
		var passwordField = document.getElementById("password");
		var confirmField = document.getElementById("confirm_password");
		var toggleButton = document.getElementById("togglePasswordBtn");

		if (passwordField.type === "password") {
			passwordField.type = "text";
			confirmField.type = "text";
			toggleButton.textContent = "パスワードを非表示にする";
		} else {
			passwordField.type = "password";
			confirmField.type = "password";
			toggleButton.textContent = "パスワードを表示する";
		}
	}
</script>
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<a class="navbar-brand" href="#">タスク管理アプリケーション</a>
	</nav>
	<div class="container mt-5">
		<div class="row justify-content-center">
			<div class="col-md-6">
				<div class="card">
					<div class="card-header text-center">
						<h2>ログインフォーム</h2>
					</div>
					<div class="card-body">
						<form action="/TASKLIST/LoginServlet" method="POST">
							<div class="form-group">
								<label for="username">ユーザーID</label>
								<input type="text" class="form-control" id="username" name="j_username" placeholder="ユーザーID" required>
							</div>
							<div class="form-group">
								<label for="password">パスワード</label>
								<input type="password" class="form-control" id="password" name="j_password" placeholder="パスワード" required>
							</div>
							<div class="form-group">
								<label for="confirm_password">確認用パスワード</label>
								<input type="password" class="form-control" id="confirm_password" name="confirm_password" placeholder="確認用パスワード" required>
							</div>
							<div class="form-group text-center">
								<button type="button" class="btn btn-secondary" id="togglePasswordBtn" onclick="togglePasswordVisibility()">パスワードを表示する</button>
							</div>
							<div class="form-group text-center">
								<button type="submit" class="btn btn-primary">ログイン</button>
							</div>
							<p class="text-center message">
								新規ユーザー？ <a href="/TASKLIST/Registration.jsp">ユーザー新規登録</a>
							</p>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFyDVtEEiL6kMOre+A5t6bL6ANslsU8wXd5gUENOMr5uZf5M1tLdfV" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOUHJwFQHAlc6xXjwZjP3tw9KfdJfSVp9F/a1rvL6sNYkMf" crossorigin="anonymous"></script>
</body>
</html>
