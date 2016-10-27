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

    public static boolean insert(Comment comment) {
        boolean flag = true;
        Connection conn = null;
        PreparedStatement ps = null;    //创建PreparedStatement 对象

        String sql = "insert into tb_comment(userID, productID, comment_date, comment_text, comment_stars, userlevel) values(?,?,?,?,?,?)";  //sql语句不再采用拼接方式，应用占位符问号的方式写sql语句。
        conn = DBConnUtil.getConn();
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, comment.getUserID()); //对占位符设置值，占位符顺序从1开始，第一个参数是占位符的位置，第二个参数是占位符的值。
            ps.setInt(2, comment.getProductID());
            ps.setString(3, comment.getComment_date());
            ps.setString(4, comment.getComment_text());
            ps.setString(5, comment.getComment_stars());
            ps.setString(6, comment.getUserlevel());
            int i = ps.executeUpdate();
            if (i == 0) {
                flag = false;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.print(ps);
        } finally {
            DBConnUtil.closeAll(null, ps, conn);
        }
        return flag;
    }
}