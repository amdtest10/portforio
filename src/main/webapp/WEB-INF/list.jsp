<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="todo.web.Todo, java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>TODOタスク管理</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <style>
        .buttons-container {
            display: flex;
            justify-content: space-around;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>タスク一覧</h1>
        <%
            String loginUser = (String) session.getAttribute("username");
            String userRole = (String) session.getAttribute("role");
        %>
        <p>ログインユーザ : <%= loginUser %></p>
        <p>ログイン権限 : <%= userRole %></p>
        
        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>番号</th>
                        <th>タイトル</th>
                        <th>期限</th>
                        <th>最終更新</th>
                        <th>状況</th>
                        <th>詳細画面へ</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    List<Todo> todos = (List<Todo>) request.getAttribute("todoList");
                    if (todos != null) {
                        Todo todo;
                        for (int i = 0; i < todos.size(); i++) {
                            todo = todos.get(i);
                    %>
                    <tr>
                        <td><%= todo.getId() %></td>
                        <td><%= todo.getTitle() %></td>
                        <td><%= todo.getLimitdate().toString().substring(0, 10) %></td>
                        <td><%= todo.getLastupdate() %></td>
                        <td><%= todo.getLabel() %></td>
                        <td><a href="/TASKLIST/DetailServlet?id=<%= todo.getId() %>">詳細画面へ</a></td>
                    </tr>
                    <%
                        }
                    }
                    %>
                </tbody>
            </table>
        </div>
        
        <div class="buttons-container">
            <%
            if ("admin".equals(userRole)) {
            %>
            <form action="/TASKLIST/ConfirmActionServlet" method="POST">
                <input type="hidden" name="actionType" value="bulkDelete" />
                <button type="submit" class="btn btn-primary">一括削除</button>
            </form>
            
            <form action="/TASKLIST/adminPageServlet" method="GET">
                <button type="submit" class="btn btn-primary">ユーザー管理画面</button>
            </form>
            <%
            }
            %>
            <form action="/TASKLIST/DetailServlet" method="GET">
                <input type="hidden" name="id" value="0" />
                <button type="submit" class="btn btn-primary">新規</button>
            </form>
            
            <form action="/TASKLIST/logout.jsp" method="GET">
                <button type="submit" class="btn btn-primary">ログアウト</button>
            </form>
        </div>
    </div>
</body>
</html>

