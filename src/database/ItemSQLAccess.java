package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by llei on 2016/10/24.
 */
public class ItemSQLAccess {
    static DBConnUtil db1 = null;
    static ResultSet ret = null;

    public static boolean insert(Item item) {
        boolean flag = true;
        Connection conn = null;
        PreparedStatement ps = null;    //创建PreparedStatement 对象

        String sql = "insert into tb_good values(?,?,?,?,?,?,?,?,?)";  //sql语句不再采用拼接方式，应用占位符问号的方式写sql语句。
        conn = DBConnUtil.getConn();
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, item.getProductID()); //对占位符设置值，占位符顺序从1开始，第一个参数是占位符的位置，第二个参数是占位符的值。
            ps.setString(2, item.getUrl());
            ps.setString(3, item.getEfficacy());
            ps.setString(4, item.getTitle());
            ps.setInt(5, item.getComment_num());
            ps.setString(6, item.getBrand());
            ps.setString(7, item.getStandard());
            ps.setString(8, item.getKeywords());
            ps.setString(9, item.getGood_rate());
            int i = ps.executeUpdate();
            if (i == 0) {
                flag = false;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            DBConnUtil.closeAll(null, ps, conn);
        }
        return flag;
    }
}
