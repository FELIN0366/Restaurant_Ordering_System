package Composition.Model;

public class Food {
    private int Food_id;
    private String Food_name;
    private String Food_type;
    private float Food_price;
    private String Food_image;
    private String Food_detail;

    private int Price_ceil;
    private int Price_floor;

    public Food(){
        super();
        util(0,"","",0,"","",999,0);
    }

    public Food(String name){
        super();
        util(0,name,"",0,"","",999,0);
    }
    public Food(String food_name, String food_type, float food_price, String food_image, String food_detail) {
        super();
        util(0,food_name,food_type,food_price, food_image, food_detail,999,0);
    }
    public Food(String food_name, String food_type, float food_price, String food_image, String food_detail,int price_ceil,int price_floor) {
        super();
        util(0,food_name,food_type,food_price, food_image, food_detail,price_ceil,price_floor);
    }

    private void util(int food_id, String food_name, String food_type, float food_price, String food_image, String food_detail,int price_ceil,int price_floor){
        this.Food_id = food_id;
        this.Food_name = food_name;
        this.Food_type = food_type;
        this.Food_price = food_price;
        this.Food_image = food_image;
        this.Food_detail = food_detail;
        this.Price_ceil=price_ceil;
        this.Price_floor=price_floor;
    }

    public int getPrice_ceil() {
        return Price_ceil;
    }

    public void setPrice_ceil(int price_ceil) {
        Price_ceil = price_ceil;
    }

    public int getPrice_floor() {
        return Price_floor;
    }

    public void setPrice_floor(int price_floor) {
        Price_floor = price_floor;
    }

    public int getFood_id() {
        return Food_id;
    }

    public void setFood_id(int food_id) {
        Food_id = food_id;
    }

    public String getFood_name() {
        return Food_name;
    }

    public void setFood_name(String food_name) {
        Food_name = food_name;
    }

    public String getFood_type() {
        return Food_type;
    }

    public void setFood_type(String food_type) {
        Food_type = food_type;
    }

    public float getFood_price() {
        return Food_price;
    }

    public void setFood_price(float food_price) {
        Food_price = food_price;
    }

    public String getFood_image() {
        return Food_image;
    }

    public void setFood_image(String food_image) {
        Food_image = food_image;
    }

    public String getFood_detail() {
        return Food_detail;
    }

    public void setFood_detail(String food_detail) {
        Food_detail = food_detail;
    }
}
