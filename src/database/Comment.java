package database;

/**
 * Created by llei on 2016/10/26.
 */
public class Comment {
    private int userID;
    private int productID;
    private String comment_date;
    private String comment_text;
    private String comment_stars;
    private String userlevel;

    public Comment(int userID, int productID, String comment_date, String comment_stars, String comment_text, String userlevel) {
        this.userID = userID;
        this.productID = productID;
        this.comment_date = comment_date;
        this.comment_stars = comment_stars;
        this.comment_text = comment_text;
        this.userlevel = userlevel;
    }

    public int getUserID() {
        return userID;
    }

    public int getProductID() {
        return productID;
    }

    public String getComment_date() {
        return comment_date;
    }

    public String getComment_text() {
        return comment_text;
    }

    public String getComment_stars() {
        return comment_stars;
    }

    public String getUserlevel() {
        return userlevel;
    }
}
