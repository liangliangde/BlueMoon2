package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by llei on 2016/10/26.
 */
public class CommentSQLAccess {

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
        PreparedStatement ps = null;
        try {
            String sql = "select commentID, comment_text from tb_comment where length(comment_text) > ?";
            ps = conn.prepareStatement(sql);    //创建PreparedStatement 对象
            ps.setInt(1, length);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rs;
    }

    public static void main(String[] args) throws SQLException {
//        getCommentOfTypeBrand("package", "蓝月亮");
        getTypeNumOfAllBrand();
    }

    public static String getTypeNumOfAllBrand() {
        StringBuilder sb = new StringBuilder();
        ResultSet rs = null;
        Connection conn = DBConnUtil.getConn();
        PreparedStatement ps = null;
        try {
            String sql = "select b.brand, sum(a.package) s1, sum(a.function) s2, sum(a.smell) s3, sum(a.service) s4, sum(a.express) s5, sum(a.price) s6 from tb_comment a, tb_good b where a.productID = b.productID group by b.brand;";
            ps = conn.prepareStatement(sql);    //创建PreparedStatement 对象
            rs = ps.executeQuery();
            while (rs.next()) {
//                System.out.println(rs.getString(1));
                sb.append(rs.getString(1)).append("###").append(rs.getString(2)).append("###")
                        .append(rs.getString(3)).append("###").append(rs.getString(4)).append("###")
                        .append(rs.getString(5)).append("###").append(rs.getString(6)).append("###")
                        .append(rs.getString(7)).append("\n");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static String getCommentOfTypeBrand(String type, String brand) throws SQLException {
        StringBuilder sb = new StringBuilder();
        ResultSet rs = null;
        Connection conn = DBConnUtil.getConn();
        PreparedStatement ps = null;
        try {
            String sql = "select a.comment_date, a.comment_text from tb_comment a, tb_good b where a.productID = b.productID and a." + type + " = 1 and b.brand = ? limit 100";
            ps = conn.prepareStatement(sql);    //创建PreparedStatement 对象
            ps.setString(1, brand);
            rs = ps.executeQuery();
            while (rs.next()) {
//                System.out.println(rs.getString(2));
                sb.append(rs.getString(1)).append("###").append(rs.getString(2)).append("\n");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}