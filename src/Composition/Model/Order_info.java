package Composition.Model;

import Composition.Operation.OrderOpe;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Order_info {
    private int Order_id;
    private int Order_table;
    private int Order_Status;//0:未结账 1已结账
    private int Order_Amount;
    private float Order_debt;
    private String User_id;
    private String Order_Date;
    private String Order_Time;



    public Order_info(){
        super();
    }

    public Order_info(String order_date,String order_time){
        super();
        utility(0,0,-1,0,0,"",order_date,order_time);
    }
    public Order_info(String user_id){
        super();
        utility(0,0,-1,0,0,user_id,"","");
    }
    public Order_info(int order_id){
        super();
        utility(order_id,0,-1,0,0,"","","");
    }

    public  Order_info(int amount,float debt){
        super();
        utility(0,0,-1,amount,debt,"","","");
    }
    public Order_info(int order_id, int order_table, int order_Status, int order_amount, float order_debt, String user_id,String order_date,String order_time){
        super();
        utility(order_id,order_table,order_Status,order_amount,order_debt,user_id,order_date,order_time);
    }

    private void utility(int order_id, int order_table, int order_Status,int order_amount, float order_debt, String user_id,String order_date,String order_time) {
        this.Order_id = order_id;
        this.Order_table = order_table;
        this.Order_Status = order_Status;
        this.Order_Amount=order_amount;
        this.Order_debt = order_debt;
        this.User_id = user_id;
        this.Order_Date=order_date;
        this.Order_Time=order_time;

    }

    public String getOrder_Date() {
        return Order_Date;
    }

    public void setOrder_Date(String order_Date) {
        Order_Date = order_Date;
    }

    public int getOrder_Amount() {
        return Order_Amount;
    }

    public void setOrder_Amount(int order_Amount) {
        Order_Amount = order_Amount;
    }
    public int getOrder_id() {
        return Order_id;
    }

    public void setOrder_id(int order_id) {
        Order_id = order_id;
    }

    public int getOrder_table() {
        return Order_table;
    }

    public void setOrder_table(int order_table) {
        Order_table = order_table;
    }

    public int getOrder_Status() {
        return Order_Status;
    }

    public void setOrder_Status(int order_Status) {
        Order_Status = order_Status;
    }

    public float getOrder_debt() {
        return Order_debt;
    }

    public void setOrder_debt(float order_debt) {
        Order_debt = order_debt;
    }

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String user_id) {
        User_id = user_id;
    }

    public String getOrder_Time() {
        return Order_Time;
    }

    public void setOrder_Time(String order_Time) {
        Order_Time = order_Time;
    }
}
