package Composition.Model;

public class Order_food {
    private int Order_id;
    private String Food_name;
    private float Food_price;
    private int Food_amount;

    private float Food_sum_price;

    public Order_food(){
        super();
    }

    public Order_food(int amount,float price){
        super();
        utility(0,"",0,amount,price);
    }

    public Order_food(int id,String name){
        super();
        utility(id,name,0,0,0);
    }
    public  Order_food(int id){
        super();
        utility(id,"",0,0,0);
    }
    public Order_food(int order_id, String food_name, float food_price, int food_amount,float food_sum_price){
        super();
        utility(order_id,food_name,food_price,food_amount,food_sum_price);
    }

    private void utility(int order_id, String food_name, float food_price, int food_amount,float food_sum_price) {
        this.Order_id = order_id;
        this.Food_name = food_name;
        this.Food_price = food_price;
        this.Food_amount = food_amount;
        this.Food_sum_price=food_sum_price;
    }

    public float getFood_sum_price() {
        return Food_sum_price;
    }

    public void setFood_sum_price(float food_sum_price) {
        Food_sum_price = food_sum_price;
    }

    public int getOrder_id() {
        return Order_id;
    }

    public void setOrder_id(int order_id) {
        Order_id = order_id;
    }

    public String getFood_name() {
        return Food_name;
    }

    public void setFood_name(String food_name) {
        Food_name = food_name;
    }

    public float getFood_price() {
        return Food_price;
    }

    public void setFood_price(float food_price) {
        Food_price = food_price;
    }

    public int getFood_amount() {
        return Food_amount;
    }

    public void setFood_amount(int food_amount) {
        Food_amount = food_amount;
    }
}
