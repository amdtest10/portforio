package todo.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DAO implements AutoCloseable {

    protected Connection connection = null;

    public DAO() { }

    // データベースへの接続を確立します
    public Connection getConnection() throws Exception {
        try {
            if (connection == null || connection.isClosed()) {
                InitialContext initCtx = new InitialContext();
                DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/localDB");
                connection = ds.getConnection();
            }
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
            connection = null; // 失敗時に接続をリセット
            throw e; // 例外を再スロー
        }
        return connection;
    }

    // 確立された接続を使用してステートメントを準備します
    public PreparedStatement getPreparedStatement(String sql) throws Exception {
        return getConnection().prepareStatement(sql);
    }

    // 現在のトランザクションをコミットします
    public void commit() throws SQLException {
        connection.commit();
    }

    // 現在のトランザクションをロールバックします
    public void rollback() throws SQLException {
        connection.rollback();
    }

    // データベース接続を閉じます
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 例外処理
        } finally {
            connection = null; // 接続を null にリセット
        }
    }
}

