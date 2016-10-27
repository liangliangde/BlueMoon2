package database;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;

/**
 * Created by llei on 2016/10/24.
 */
public class DataProcess {
    public static void main(String[] args) throws IOException {
//        insertItems("data\\items");
        insertCommnent("data\\comments");
    }

    private static void insertCommnent(String path) throws IOException{
        File[] files = new File(path).listFiles();
        for (File file : files) {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String linetxt = null;
            while ((linetxt = br.readLine()) != null) {
                Comment curComment = parseComment(linetxt);
                CommentSQLAccess.insert(curComment);
            }
        }
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
        }
        return comment;
    }

    private static void insertItems(String path) throws IOException {
        File[] files = new File(path).listFiles();
        for (File file : files) {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String linetxt = null;
            while ((linetxt = br.readLine()) != null) {
                Item curItem = parseItem(linetxt);
                ItemSQLAccess.insert(curItem);
            }
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