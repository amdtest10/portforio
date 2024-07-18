<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>確認</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <style>
        .container {
            margin-top: 50px;
        }

        .confirmation-box {
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 5px;
            margin-bottom: 20px;
        }

        .btn-margin {
            margin-right: 10px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1 class="text-center mb-4">確認</h1>
        <div class="confirmation-box">
            <%
            String actionType = request.getParameter("actionType");

            if ("bulkDelete".equals(actionType)) {
            %>
            <p>全てのタスクを削除してもよろしいですか？</p>
            <div class="row">
                <div class="col-md-6">
                    <form action="/TASKLIST/DeleteAllServlet" method="POST">
                        <button type="submit" class="btn btn-success btn-margin">はい</button>
                    </form>
                    <form action="/TASKLIST/SearchServlet" method="GET">
                        <button type="submit" class="btn btn-secondary btn-margin">いいえ</button>
                    </form>
                </div>
            </div>
            <%
            } else {
                String id = request.getParameter("id");
                String title = request.getParameter("title");
                String task = request.getParameter("task");
                String limitdate = request.getParameter("limitdate");
                String userid = request.getParameter("userid");
                String status = request.getParameter("status");
                String action = request.getParameter("action");

                String actionUrl = "";
                if ("削除".equals(action)) {
                    actionUrl = "/TASKLIST/DeleteServlet";
                } else if ("更新".equals(action) || "新規登録".equals(action)) {
                    actionUrl = "/TASKLIST/RegisterServlet";
                }

                if ("削除".equals(action)) {
            %>
            <p>以下のタスクを削除してもよろしいですか？</p>
            <%
                } else if ("更新".equals(action)) {
            %>
            <p>以下のタスクを更新してもよろしいですか？</p>
            <%
                } else if ("新規登録".equals(action)) {
            %>
            <p>以下のタスクを登録してもよろしいですか？</p>
            <%
                }
            %>
            <div class="row">
                <div class="col-md-6">
                    <p>タイトル: <%= title %></p>
                    <p>タスク: <%= task %></p>
                    <p>期限: <%= limitdate %></p>
                    <p>ユーザID: <%= userid %></p>
                </div>
                <div class="col-md-6">
                    <table>
                        <tr>
                            <form action="<%= actionUrl %>" method="POST">
                                <input type="hidden" name="id" value="<%= id %>" />
                                <input type="hidden" name="title" value="<%= title %>" />
                                <input type="hidden" name="task" value="<%= task %>" />
                                <input type="hidden" name="limitdate" value="<%= limitdate %>" />
                                <input type="hidden" name="userid" value="<%= userid %>" />
                                <input type="hidden" name="status" value="<%= status %>" />
                                <button type="submit" class="btn btn-success btn-margin">はい</button>
                            </form>
                            <form action="/TASKLIST/SearchServlet" method="GET">
                                <button type="submit" class="btn btn-secondary btn-margin">いいえ</button>
                            </form>
                        </tr>
                    </table>
                </div>
            </div>
            <%
            }
            %>
        </div>
    </div>
</body>
</html>
