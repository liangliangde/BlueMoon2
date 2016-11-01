package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by llei on 2016/10/26.
 */
public class CommentSQLAccess {
    static DBConnUtil db1 = null;
    static ResultSet ret = null;

    public static boolean insert(Comment comment, PreparedStatement ps) {
        boolean flag = true;
        try {
            ps.setInt(1, comment.getUserID()); //对占位符设置值，占位符顺序从1开始，第一个参数是占位符的位置，第二个参数是占位符的值。
            ps.setInt(2, comment.getProductID());
            ps.setString(3, comment.getComment_date());
            ps.setString(4, comment.getComment_text());
            ps.setString(5, comment.getComment_stars());
            ps.setString(6, comment.getUserlevel());
            ps.addBatch();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            flag = false;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static ResultSet getValidComment(int length) {
        ResultSet rs = null;
        Connection conn = DBConnUtil.getConn();
        try {
            String sql = "select commentID, comment_text from tb_comment where length(comment_text) > ?";
            PreparedStatement ps = conn.prepareStatement(sql);    //创建PreparedStatement 对象
            ps.setInt(1, length);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static void main(String[] args) throws SQLException {
        ResultSet rs = getValidComment(20);
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
    }
}