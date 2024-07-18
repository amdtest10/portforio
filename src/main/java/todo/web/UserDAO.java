package todo.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.mindrot.jbcrypt.BCrypt;

public class UserDAO extends DAO {

    public UserDAO() {
        super();
    }

    // パスワードのハッシュ化
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // パスワードの検証
    public boolean checkPassword(String enteredPassword, String hashedPassword) {
        return BCrypt.checkpw(enteredPassword, hashedPassword);
    }

    // ユーザー登録メソッド
    public void registerUser(String username, String password) throws SQLException, NamingException {
        String hashedPassword = hashPassword(password);
        String insertUserQuery = "INSERT INTO users (userid, password) VALUES (?, ?)";
        try (PreparedStatement ps = getConnection().prepareStatement(insertUserQuery)) {
            ps.setString(1, username);
            ps.setString(2, hashedPassword);
            ps.executeUpdate();
            commit(); // コミット
        } catch (SQLException e) {
            rollback(); // ロールバック
            throw e;
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    // ユーザーのロールを追加するメソッド
    public void addUserRole(String username, String role) throws SQLException, NamingException {
        String insertRoleQuery = "INSERT INTO roles (userid, role) VALUES (?, ?)";
        try (PreparedStatement ps = getConnection().prepareStatement(insertRoleQuery)) {
            ps.setString(1, username);
            ps.setString(2, role);
            ps.executeUpdate();
            commit(); // コミット
        } catch (SQLException e) {
            rollback(); // ロールバック
            throw e;
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    // ユーザー認証メソッド
    public boolean authenticateUser(String username, String password) throws SQLException, NamingException {
        String query = "SELECT password FROM users WHERE userid = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(query)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hashedPasswordFromDB = rs.getString("password");
                    return checkPassword(password, hashedPasswordFromDB);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return false; // 認証失敗
    }

    // ユーザーのロールを更新するメソッド
    public void updateUserRole(String username, String newRole) throws SQLException {
        String sql = "UPDATE roles SET role = ? WHERE userid = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newRole);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
            connection.commit(); // コミット
        } catch (SQLException e) {
            connection.rollback(); // ロールバック
            throw e;
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    // ユーザーを削除するメソッド
    public void deleteUser(String username) throws SQLException {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false); // トランザクション開始

            String sql1 = "DELETE FROM roles WHERE userid = ?";
            String sql2 = "DELETE FROM users WHERE userid = ?";

            try (PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
                 PreparedStatement preparedStatement2 = connection.prepareStatement(sql2)) {

                preparedStatement1.setString(1, username);
                preparedStatement1.executeUpdate();

                preparedStatement2.setString(1, username);
                preparedStatement2.executeUpdate();

                connection.commit(); // コミット
            } catch (SQLException e) {
                connection.rollback(); // ロールバック
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            // コネクションクローズ
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // ユーザーのロールを取得するメソッド
    public String getUserRole(String username) throws SQLException {
        String query = "SELECT role FROM roles WHERE userid = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(query)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("role");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return null; // 該当するユーザーが見つからない場合は null を返す
    }

    // 全ユーザーを取得するメソッド
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT users.userid, password, role FROM users JOIN roles ON users.userid = roles.userid";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                User user = new User();
                user.setUsername(resultSet.getString("userid"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getString("role"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return users; // ユーザーリストを返す
    }
}

