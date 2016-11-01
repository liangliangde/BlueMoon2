package database;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by llei on 2016/10/24.
 */
public class DataProcess {
    public static void main(String[] args) throws IOException {
        insertItems("data\\aomiao\\items");
        insertCommnent("data\\aomiao\\comments");
        insertItems("data\\chaoneng\\items");
        insertCommnent("data\\chaoneng\\comments");
        insertItems("data\\jieba\\items");
        insertCommnent("data\\jieba\\comments");
        insertItems("data\\lanyueliang\\items");
        insertCommnent("data\\lanyueliang\\comments");
        insertItems("data\\taizi\\items");
        insertCommnent("data\\taizi\\comments");
        insertItems("data\\walch\\items");
        insertCommnent("data\\walch\\comments");
        System.out.print("finish!!");
    }

    private static void insertCommnent(String path) throws IOException {
        Connection conn = DBConnUtil.getConn();
        String sql = "insert into tb_comment(userID, productID, comment_date, comment_text, comment_stars, userlevel) values(?,?,?,?,?,?)";  //sql语句不再采用拼接方式，应用占位符问号的方式写sql语句。
        PreparedStatement ps = null;    //创建PreparedStatement 对象
        try {
            ps = conn.prepareStatement(sql);
            File[] files = new File(path).listFiles();
            int count = 0;
            int batchSize = 1;
            for (File file : files) {
                FileInputStream fis = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                String linetxt = null;
                while ((linetxt = br.readLine()) != null) {
                    try {
                        Comment curComment = parseComment(linetxt);
                        CommentSQLAccess.insert(curComment, ps);
                        if (++count % batchSize == 0) {
                            ps.executeBatch();
                        }
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                fis.close();
                isr.close();
                br.close();
            }
            ps.executeBatch();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        if (str.split(" ").length != 2) {
            return false;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }

    private static Comment parseComment(String linetxt) {
        Comment comment = null;
        try {
            JSONTokener jsonParser = new JSONTokener(linetxt);
            JSONObject jsonObject = (JSONObject) jsonParser.nextValue();
            String comment_date = removeParthesis(jsonObject.get("comment_date").toString().trim());
            String comment_text = removeParthesis(jsonObject.get("comment_text").toString().trim());
            int userID = Integer.parseInt(removeParthesis(jsonObject.get("userID").toString().trim()));
            String comment_stars = removeParthesis(jsonObject.get("comment_stars").toString().trim());
            String userlevel = removeParthesis(jsonObject.get("userlevel").toString().trim());
            int productID = Integer.parseInt(removeParthesis(jsonObject.get("productid").toString().trim()));
            comment = new Comment(userID, productID, comment_date, comment_stars, comment_text, userlevel);
        } catch (JSONException e) {
            System.out.println("Comment parse error !!!!");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comment;
    }

    private static void insertItems(String path) throws IOException {
        Connection conn = DBConnUtil.getConn();
        String sql = "insert into tb_good values(?,?,?,?,?,?,?,?,?)";  //sql语句不再采用拼接方式，应用占位符问号的方式写sql语句。
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            File[] files = new File(path).listFiles();
            int count = 0;
            int batchSize = 1000;
            for (File file : files) {
                FileInputStream fis = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                String linetxt = null;
                while ((linetxt = br.readLine()) != null) {
                    Item curItem = parseItem(linetxt);
                    ItemSQLAccess.insert(curItem, ps);
                    if (++count % batchSize == 0) {
                        ps.executeBatch();
                    }
                }
            }
            ps.executeBatch();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Item parseItem(String linetxt) {
        Item item = null;
        try {
            JSONTokener jsonParser = new JSONTokener(linetxt);
            JSONObject jsonObject = (JSONObject) jsonParser.nextValue();
            String url = removeParthesis(jsonObject.get("pro_url").toString().trim());
            String efficacy = removeParthesis(jsonObject.getString("efficacy").toString().trim());
            String title = removeParthesis(jsonObject.getString("title").toString().trim());
            int comment_num = Integer.parseInt(removeParthesis(jsonObject.getString("comment_num").toString().trim()));
            String brand = removeParthesis(jsonObject.getString("brand").toString().trim());
            String standard = removeParthesis(jsonObject.getString("standard").toString().trim());
            String keywords = removeParthesis(jsonObject.getString("keywords").toString().trim());
            String good_rate = removeParthesis(jsonObject.getString("good_rate").toString().trim());
            int productID = Integer.parseInt(removeParthesis(jsonObject.getString("productid").toString().trim()));
            item = new Item(productID, url, efficacy, title, comment_num, brand, standard, keywords, good_rate);
        } catch (JSONException e) {
            System.out.println("Item parse error !!!!");
            e.printStackTrace();
        }
        return item;
    }

    private static String removeParthesis(String pro_url) {
        return pro_url.replaceAll("\\[|\\]|\"", "").trim();
    }
}