package todo.web;

public class User {
    private String username; // ユーザー名
    private String password; // パスワード
    private String role;     // ユーザーのロール情報

    // ユーザーオブジェクトを初期化するコンストラクター
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // デフォルトコンストラクター
    public User() {
        // 自動生成されたコンストラクター・スタブ
    }

    // ユーザー名を取得するメソッド
    public String getUsername() {
        return username;
    }

    // ユーザー名を設定するメソッド
    public void setUsername(String username) {
        this.username = username;
    }

    // パスワードを取得するメソッド
    public String getPassword() {
        return password;
    }

    // パスワードを設定するメソッド
    public void setPassword(String password) {
        this.password = password;
    }

    // ユーザーのロールを取得するメソッド
    public String getRole() {
        return role;
    }

    // ユーザーのロールを設定するメソッド
    public void setRole(String role) {
        this.role = role;
    }
}
