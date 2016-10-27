package database;

/**
 * Created by llei on 2016/10/25.
 */
public class Item {

    private int productID;
    private String url;
    private String efficacy;
    private String title;
    private int comment_num = 0;
    private String brand;
    private String standard;
    private String keywords;
    private String good_rate;

    public Item(int productID, String url, String efficacy, String title, int comment_num, String brand, String standard, String keywords, String good_rate) {
        this.productID = productID;
        this.url = url;
        this.efficacy = efficacy;
        this.title = title;
        this.comment_num = comment_num;
        this.brand = brand;
        this.standard = standard;
        this.keywords = keywords;
        this.good_rate = good_rate;
    }

    public int getProductID() {
        return productID;
    }

    public String getUrl() {
        return url;
    }

    public String getEfficacy() {
        return efficacy;
    }

    public String getTitle() {
        return title;
    }

    public int getComment_num() {
        return comment_num;
    }

    public String getBrand() {
        return brand;
    }

    public String getStandard() {
        return standard;
    }

    public String getKeywords() {
        return keywords;
    }

    public String getGood_rate() {
        return good_rate;
    }
}
