package database;

import java.sql.*;

public class DBConnUtil {

    private static String jdbcDriver = "com.mysql.jdbc.Driver";      //定义连接信息
    private static String jdbcUrl = "jdbc:mysql://127.0.0.1/bluemoon";
    private static String jdbcUser = "root";
    private static String jdbcPasswd = "2";

    public static Connection getConn() {   // 建立连接方法
        Connection conn = null;
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPasswd);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) {  // 关闭连接（用于查）
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}