package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by llei on 2016/10/24.
 */
public class ItemSQLAccess {

    public static boolean insert(Item item, PreparedStatement ps) {
        boolean flag = true;
        try {
            ps.setInt(1, item.getProductID()); //对占位符设置值，占位符顺序从1开始，第一个参数是占位符的位置，第二个参数是占位符的值。
            ps.setString(2, item.getUrl());
            ps.setString(3, item.getEfficacy());
            ps.setString(4, item.getTitle());
            ps.setInt(5, item.getComment_num());
            ps.setString(6, item.getBrand());
            ps.setString(7, item.getStandard());
            ps.setString(8, item.getKeywords());
            ps.setString(9, item.getGood_rate());
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

    public static ResultSet queryTitleByBrand(String brand) {
        ResultSet rs = null;
        Connection conn = DBConnUtil.getConn();
        String sql = "select title from tb_good where brand = ? order by title";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);    //创建PreparedStatement 对象
            ps.setString(1, brand);
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

}