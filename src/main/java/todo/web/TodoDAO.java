package todo.web;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TodoDAO extends DAO {

    // TODOリストを取得するメソッド
    public List<Todo> todoList() throws Exception {
        List<Todo> returnList = new ArrayList<>();

        String sql = "SELECT id, title, task, limitdate, lastupdate, userid, label, td.status FROM todo_list as td LEFT JOIN status_list as st ON st.status = td.status order by id";

        PreparedStatement statement = getPreparedStatement(sql);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            Todo dto = new Todo();
            dto.setId(rs.getInt("id"));
            dto.setTitle(rs.getString("title"));
            dto.setTask(rs.getString("task"));
            dto.setLimitdate(rs.getTimestamp("limitdate"));
            dto.setLastupdate(rs.getTimestamp("lastupdate"));
            dto.setUserid(rs.getString("userid"));
            dto.setLabel(rs.getString("label"));
            returnList.add(dto);
        }
        return returnList;
    }

    // 指定したIDのTODO詳細を取得するメソッド
    public Todo detail(int id) throws Exception {
        String sql = "SELECT id, title, task, limitdate, lastupdate, userid, label, td.status FROM todo_list as td LEFT JOIN status_list as st on st.status = td.status where id = ?";

        PreparedStatement statement = getPreparedStatement(sql);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();

        Todo dto = new Todo();
        while (rs.next()) {
            dto.setId(rs.getInt("id"));
            dto.setTitle(rs.getString("title"));
            dto.setTask(rs.getString("task"));
            dto.setLimitdate(rs.getTimestamp("limitdate"));
            dto.setLastupdate(rs.getTimestamp("lastupdate"));
            dto.setUserid(rs.getString("userid"));
            dto.setLabel(rs.getString("label"));
            dto.setStatus(rs.getInt("status"));
        }
        return dto;
    }

    // TODOを削除するメソッド
    public int delete(int id) throws Exception {
        String sql = "DELETE FROM todo_list where id = ?";

        int result = 0;
        try {
            PreparedStatement statement = getPreparedStatement(sql);
            statement.setInt(1, id);
            result = statement.executeUpdate();
            super.commit(); // コミット
        } catch (Exception e) {
            super.rollback(); // ロールバック
            throw e;
        }
        return result;
    }

    // TODOを追加するメソッド
    public int registerInsert(Todo dto) throws Exception {
        String sql = "INSERT INTO todo_list(title, task, limitdate, lastupdate, userid, status) values (?, ?, ?, now(), ?, 0)";

        int result = 0;

        try {
            PreparedStatement statement = getPreparedStatement(sql);
            statement.setString(1, dto.getTitle());
            statement.setString(2, dto.getTask());
            statement.setString(3, dto.getInputLimitdate());
            statement.setString(4, dto.getUserid());
            result = statement.executeUpdate();
            super.commit(); // コミット
        } catch (Exception e) {
            super.rollback(); // ロールバック
            throw e;
        }
        return result;
    }

    // TODOを更新するメソッド
    public int registerUpdate(Todo dto) throws Exception {
        String sql = "UPDATE todo_list SET title = ?, task = ?, limitdate = ?, lastupdate = now(), userid = ?, status = ? WHERE id = ?";

        int result = 0;
        try {
            PreparedStatement statement = getPreparedStatement(sql);
            statement.setString(1, dto.getTitle());
            statement.setString(2, dto.getTask());
            statement.setString(3, dto.getInputLimitdate());
            statement.setString(4, dto.getUserid());
            statement.setInt(5, dto.getStatus());
            statement.setInt(6, dto.getId());
            result = statement.executeUpdate();
            super.commit(); // コミット
        } catch (Exception e) {
            super.rollback(); // ロールバック
            throw e;
        }
        return result;
    }

    // 全てのTODOを削除するメソッド
    public void deleteAll() throws SQLException {
        String deleteQuery = "DELETE FROM todo_list"; // 実際のテーブル名に置き換えてください
        PreparedStatement ps = null;

        try {
            ps = getPreparedStatement(deleteQuery);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // 例外のキャッチと処理
            throw e; // 例外を再スローするか、適切な処理を追加する
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close(); // ステートメントをクローズ
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

