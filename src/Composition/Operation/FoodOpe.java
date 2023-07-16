package Composition.Operation;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import Composition.Model.Food;
import Composition.Util.JDBUtil;
import Composition.Util.StringUtil;

public class FoodOpe {
    //食物种类添加
    public static int FoodAdd(Connection con, Food food)throws Exception{
        String sql="insert into `food` values(null,?,?,?,?,?)";
        PreparedStatement pstmt=con.prepareStatement(sql);
        //执行SQL语句，并返回响应对象结果
        pstmt.setString(1,food.getFood_name());
        pstmt.setString(2,food.getFood_type());
        pstmt.setFloat(3,food.getFood_price());
        pstmt.setString(4,food.getFood_image());
        pstmt.setString(5,food.getFood_detail());
        return pstmt.executeUpdate();
        //executeUpdate:(int) 成功返回1
    }

    //根据条件筛选出食物列表
    public static ResultSet Foodlist(Connection con,Food food)throws Exception{
        StringBuffer sql=new StringBuffer("select * from `food`");
        if(!StringUtil.isEmpty(food.getFood_name())){
            sql.append(" and `Food_name` like '%"+food.getFood_name()+"%'");
        }
        if(!StringUtil.isNum(food.getFood_type())){
            sql.append(" and `Food_type` like '%"+food.getFood_type()+"%'");
        }
        sql.append(" and `Food_price` between "+food.getPrice_floor()+" and "+food.getPrice_ceil());
        //System.out.println(sql.toString().replaceFirst("and","where"));
        PreparedStatement pstmt=con.prepareStatement(sql.toString().replaceFirst("and","where"));

        return pstmt.executeQuery();
    }

    public static ResultSet FoodlistSearch(Connection con,Food food)throws Exception{
        StringBuffer sql=new StringBuffer("select * from `food`");
        if(!StringUtil.isEmpty(food.getFood_name())){
            sql.append(" and `Food_name` ='"+food.getFood_name()+"'");
        }
        if(!StringUtil.isNum(food.getFood_type())){
            sql.append(" and `Food_type` like '%"+food.getFood_type()+"%'");
        }
        sql.append(" and `Food_price` between "+food.getPrice_floor()+" and "+food.getPrice_ceil());
        //System.out.println(sql.toString().replaceFirst("and","where"));
        PreparedStatement pstmt=con.prepareStatement(sql.toString().replaceFirst("and","where"));

        return pstmt.executeQuery();
    }

    public static int FoodModify(Connection con,Food food,int id) throws Exception{
        String sql="update `food` set `Food_name`=?,`Food_type`=?,`Food_price`=?,`Food_image`=?,`Food_detail`=? where `Food_id`=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, food.getFood_name());
        pstmt.setString(2, food.getFood_type());
        pstmt.setFloat(3, food.getFood_price());
        pstmt.setString(4, food.getFood_image());
        pstmt.setString(5, food.getFood_detail());
        pstmt.setInt(6, id);
        return pstmt.executeUpdate();
    }

    public static int FoodDelete(Connection con,Food food) throws Exception{
        String sql="delete from `food` where `Food_id`=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setInt(1, food.getFood_id());
        return pstmt.executeUpdate();
    }

    public static void main(String[]args) throws Exception {
        Food food=new Food();

        JDBUtil jdbUtil=new JDBUtil();
        Connection con= jdbUtil.getCon();
        FoodAdd(con,food);
        FoodModify(con,food,2);
        FoodDelete(con,food);
        ResultSet re=Foodlist(con,food);
        while(re.next()){
            System.out.println(re.getString("Food_name"));
        }
        jdbUtil.closeCon(con);
    }
}
