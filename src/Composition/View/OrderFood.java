package Composition.View;

import Composition.Model.Food;
import Composition.Model.Order_food;
import Composition.Model.Order_info;
import Composition.Operation.FoodOpe;
import Composition.Operation.OrderOpe;
import Composition.Util.JDBUtil;
import Composition.Util.StringUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class OrderFood extends JFrame{

    public static int Order_id=0;//订单号：下单的时候获取

    //private JDesktopPane desktop=new JDesktopPane();
    //private CheckOrder checkOrder=new CheckOrder();
    public OrderFood() throws Exception {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("国际校区点餐系统");
        this.setContentPane(panel1);

    }

    private void initComponents() throws Exception {

        //this.setContentPane(desktop);

//        //设置背景
//        layeredPane=new JLayeredPane();
//        JPanel background=new JPanel();
//        background.setBounds(0,0,1750,890);
//        JLabel bg=new JLabel(itran("src/Composition/util_images/Entrance.jpg",1750,890));
//        background.add(bg);
//        layeredPane.add(background,JLayeredPane.DEFAULT_LAYER);
//
//        //设置panel
//        panel1.setBackground(new Color(255,255,255,150));
//        panel1.setBounds(0,0,1750,890);//panel一定要设置大小才能显出来
//        layeredPane.add(panel1,JLayeredPane.MODAL_LAYER);

//        jp1.setBackground(new Color(255,255,255,0));
//        jp2.setBackground(new Color(255,255,255,0));
//        jt1.setBackground(new Color(255,255,255,0));
//        jt2.setBackground(new Color(255,255,255,0));
//        jp3.setBackground(new Color(255,255,255,0));
//        jp4.setBackground(new Color(255,255,255,0));
//        js1.setBackground(new Color(255,255,255,0));
//        js2.setBackground(new Color(255,255,255,0));
//        js3.setBackground(new Color(255,255,255,0));
//        js4.setBackground(new Color(255,255,255,0));
//        js5.setBackground(new Color(255,255,255,0));
//        menu1.setBackground(new Color(255,255,255,0));
//        menu2.setBackground(new Color(255,255,255,0));
//        menu3.setBackground(new Color(255,255,255,0));
//        menu4.setBackground(new Color(255,255,255,0));
//        menu5.setBackground(new Color(255,255,255,0));
//        菜式导航.setBackground(new Color(255,255,255,0));

        //西部区域
        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jt1.setSelectedIndex(0);
            }
        });
        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jt1.setSelectedIndex(1);
            }
        });
        jb3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jt1.setSelectedIndex(2);
            }
        });
        jb4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jt1.setSelectedIndex(3);
            }
        });
        jb5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jt1.setSelectedIndex(4);
            }
        });

        //中部区域
        menu_get(menu1,"主食类");
        menu_get(menu2,"包饭类");
        menu_get(menu3,"炸鸡类");
        menu_get(menu4,"年糕类");
        menu_get(menu5,"米酒烧酒类");

        //南部区域:货品筛选
        Ftype.addItem("全部");
        Ftype.addItem("主食类");
        Ftype.addItem("包饭类");
        Ftype.addItem("炸鸡类");
        Ftype.addItem("年糕类");
        Ftype.addItem("米酒烧酒类");
        Ftype.setSelectedIndex(0);

        jb_search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    Food food=jb_searchActionListener(e);
                try {
                    jb_menu_update(food);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //南部区域：下单-结算-加单
        //加入购物车
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add_ActionListener(e);
            }
        });
        //删除购物车
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete_ActionListener(e);
            }
        });
        //下单
        //安全检查-数据导入数据库-订单新建-清理工作
        order.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    order_ActionListener(e);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        //加单
        //安全检查-数据入库（订单号不变）-订单补充-清理工作
        reorder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    reorder_ActionListener(e);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        //结账
        //弹窗（能否结账/确认结账）-结账成功
        CheckOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    checkout_ActionListner(e);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //东部区域
        add_item_jc(jc1,jc2);//添加桌号&用餐人数选项
        //购物车&订单详情：初始化
        String[]colname=new String[]{"序号","名称","数量","单价","总价"};
        shoppingcar.setModel(new DefaultTableModel(new Object[][]{},colname){
            boolean[]canEdit=new boolean[]{false,false,false,false};
            public boolean isCellEdiatable(int rowIndex,int colIndex){return canEdit[colIndex];}
        });
        orderingcar.setModel(new DefaultTableModel(new Object[][]{},colname){
            boolean[]canEdit=new boolean[]{false,false,false,false};
            public boolean isCellEdiatable(int rowIndex,int colIndex){return canEdit[colIndex];}
        });

//        //desktop添加
//        //先添加的就会在界面最上层(注意界面添加顺序)
//        desktop.setOpaque(false);
//        desktop.add(checkOrder);
//        checkOrder.setVisible(false);
//        desktop.add(panel1);
//        panel1.setSize(1705,890);//如需添加背景，则要重定义一个JPanel类

    }
    //添加桌号和人数组件(main)
    private void add_item_jc(JComboBox jc,JComboBox jc1){
        jc.addItem("请选择桌号");
        jc.setSelectedIndex(0);
        jc1.addItem("请选择人数");
        jc.setSelectedIndex(0);
        for(int i=1;i<=9;++i){
            jc.addItem("0"+Integer.toString(i));
            jc1.addItem(i);
        }
        for(int i=10;i<=30;++i){
            jc.addItem(Integer.toString(i));
            jc1.addItem(i);
        }
    }
    //照片格式转换(utility)
    private Icon itran(String name,int w,int h){
        ImageIcon image=new ImageIcon(name);
        Image img=image.getImage();
        img=img.getScaledInstance(w,h,Image.SCALE_DEFAULT);
        image.setImage(img);
        return image;
    }
    //order:下单(main)
    private void order_ActionListener(ActionEvent e) throws Exception {
        DefaultTableModel dtm=(DefaultTableModel) shoppingcar.getModel();
        DefaultTableModel dtmt=(DefaultTableModel) orderingcar.getModel();
        int num=dtm.getRowCount();
        //是否满足下单条件
        if(num<1){
            JOptionPane.showMessageDialog(null,"未添加商品！无法下单！请添加商品后重新下单！","提示",JOptionPane.WARNING_MESSAGE);
            return;
        }
        //进行加单
        if(dtmt.getRowCount()!=0){
            JOptionPane.showMessageDialog(null,"已下单！如需添加菜品，请进行加单操作！","提示",JOptionPane.WARNING_MESSAGE);
            return;
        }
        JDBUtil jdbUtil=new JDBUtil();
        Connection con=null;
        try{
            con=jdbUtil.getCon();
            //Order_info:下单！！！
            int table=Integer.parseInt(jc1.getSelectedItem().toString());//桌号
            int amount=StringUtil.select_int(SumAmount.getText());//菜的数量
            float debt=StringUtil.select_float(SumPrice.getText());//总价
            String id=UserLogin.current_user.getUser_id();
            //String id="Felin";
            String date=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String time=new SimpleDateFormat("HH:mm:ss").format(new Date());
            Order_info order_info=new Order_info(0,table,0,amount,debt,id,date,time);
            OrderOpe.OrderinfoAdd(con,order_info);
            //Order_food
            //0序号 1名称 2数量 3单价 4总价
            for(int i=0;i<num;++i){
                String name=(String) dtm.getValueAt(0,1);//菜名
                float price=StringUtil.YtoF((String) dtm.getValueAt(0,3));//单价
                int famount=(int)dtm.getValueAt(0,2);//数量
                float sumprice=StringUtil.YtoF((String) dtm.getValueAt(0,4)) ;//菜总价
                ResultSet re=OrderOpe.OrderinfoList(con,new Order_info(date,time));
                while (re.next()){ Order_id=re.getInt("Order_id");}
                Order_food order_food=new Order_food(Order_id,name,price,famount,sumprice);
                OrderOpe.OrderFoodAdd(con,order_food);
                //每次remove后row的下标都会变，所以只需要removeRow(0)即可
                dtm.removeRow(0);
                //订单详情
                Vector v=new Vector();
                v.add(0);v.add(name);v.add(famount);v.add(StringUtil.FtoY(price));v.add(StringUtil.FtoY(sumprice));
                dtmt.addRow(v);
            }
            //收尾工作
            OSumAmount.setText("菜品数量： "+amount+"件");
            OSumPrice.setText("菜品总价： "+StringUtil.FtoY(debt));
            SumPrice.setText("");
            SumAmount.setText("");
            index_update(dtmt);//更新dtmt序号
            //弹窗：已下单
            String message="已成功提交订单！\n您的订单号为："+Order_id;
            JOptionPane.showMessageDialog(null,message,"提示",JOptionPane.WARNING_MESSAGE);
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null,"订单提交失败！\n当前系统繁忙，请稍后再试！","提示",JOptionPane.WARNING_MESSAGE);
            ex.printStackTrace();
        }finally {
            jdbUtil.closeCon(con);
        }

    }
    //reorder：加单(main)
    private void reorder_ActionListener(ActionEvent e) throws Exception {
        DefaultTableModel dtm=(DefaultTableModel) shoppingcar.getModel();
        DefaultTableModel dtmt=(DefaultTableModel) orderingcar.getModel();
        int num=dtm.getRowCount();
        //是否满足加单条件
        if(num==0){
            JOptionPane.showMessageDialog(null,"未添加商品！无法下单！请添加商品后重新下单！","提示",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(dtmt.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"暂无任何菜品下单！请下单后再按需加单！","提示",JOptionPane.WARNING_MESSAGE);
            return;
        }
        JDBUtil jdbUtil=new JDBUtil();
        Connection con=null;
        try{
            con=jdbUtil.getCon();
            //Order_info
            //根据Order_id修改amount、debt
            int amount=StringUtil.select_int(OSumAmount.getText())+StringUtil.select_int(SumAmount.getText());//菜数量
            float debt=StringUtil.select_float(OSumPrice.getText())+StringUtil.select_float(SumPrice.getText());//总价

            Order_info bemodi=new Order_info(amount,debt);
            Order_info modi=new Order_info(Order_id);
            OrderOpe.OrderInfoModify(con,bemodi,modi);

            //Order_food
            //0序号 1名称 2数量 3单价 4总价
            for(int i=0;i<num;++i){

                String name=(String) dtm.getValueAt(0,1);//菜名
                float price=StringUtil.YtoF((String) dtm.getValueAt(0,3));//单价
                int famount=(int)dtm.getValueAt(0,2);//数量
                float sumprice=StringUtil.YtoF((String) dtm.getValueAt(0,4)) ;//菜总价

                int index=in_shoppingcar(dtmt,name);//判断是否在订单中
                if(index==-1){//不在订单中
                    //Order_food新增
                    Order_food order_food=new Order_food(Order_id,name,price,famount,sumprice);
                    OrderOpe.OrderFoodAdd(con,order_food);
                    //订单详情
                    Vector v=new Vector();
                    v.add(0);v.add(name);v.add(famount);v.add(StringUtil.FtoY(price));v.add(StringUtil.FtoY(sumprice));
                    dtmt.addRow(v);
                }else{
                    //Order_food修改
                    //根据id,food_name修改amount,sum_price
                    Order_food mo=new Order_food(Order_id,name);
                    ResultSet re=OrderOpe.OrderfoodList(con,mo);
                    while(re.next()){
                        famount+=re.getInt("Food_amount");
                        sumprice+=re.getFloat("Food_sum_price");
                    }
                    Order_food bemo=new Order_food(famount,sumprice);
                    OrderOpe.OrderFoodModify(con,bemo,mo);
                    //订单详情
                    dtmt.setValueAt(famount,index,2);
                    dtmt.setValueAt(StringUtil.FtoY(sumprice),index,4);
                }
                //每次remove后row的下标都会变，所以只需要removeRow(0)即可
                dtm.removeRow(0);
            }
            //收尾工作
            OSumAmount.setText("菜品数量： "+amount+"件");
            OSumPrice.setText("菜品总价： "+StringUtil.FtoY(debt));
            SumPrice.setText("");
            SumAmount.setText("");
            index_update(dtmt);//更新dtmt序号

            //弹窗：已下单
            String message="加单成功！\n您的订单号为："+Order_id;
            JOptionPane.showMessageDialog(null,message,"提示",JOptionPane.WARNING_MESSAGE);
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null,"加单失败！\n当前系统繁忙，请稍后再试！","提示",JOptionPane.WARNING_MESSAGE);
            ex.printStackTrace();
        }finally {
            jdbUtil.closeCon(con);
        }

    }
    //checkout:结账(main)
    private void checkout_ActionListner(ActionEvent e)throws Exception{
        DefaultTableModel dtmt=(DefaultTableModel) orderingcar.getModel();
        if(dtmt.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"结账失败！\n请先下单再结账！","提示",JOptionPane.WARNING_MESSAGE);
            return;
        }
        int out=JOptionPane.showConfirmDialog(null,"是否需要结账？","提示",JOptionPane.OK_OPTION);
        if(out!=JOptionPane.OK_OPTION) return;

        //由于历史原因在此处录入数据库
        JDBUtil jdbUtil=new JDBUtil();
        Connection con=null;
        try{
            con= jdbUtil.getCon();
            //结账成功弹窗
            //根据order_id得到amount debt
            Order_info order_info=new Order_info(Order_id);
            ResultSet re=OrderOpe.OrderinfoList(con,order_info);
            while(re.next()){
                int amount=re.getInt("Order_Amount");
                float debt=re.getFloat("Order_debt");
                String message="恭喜您已成功结账！\n您的餐品种类共："+amount+"件\n您的餐品费用共"+StringUtil.FtoY(debt);
                JOptionPane.showMessageDialog(null,message,"提示",JOptionPane.WARNING_MESSAGE);
            }
            //数据库状态修改
            Order_info modi=new Order_info(Order_id);
            Order_info bemodi=new Order_info(0,0,1,0,0,"","","");
            OrderOpe.OrderInfoModify(con,bemodi,modi);
            //善后工作
            clearJTable((DefaultTableModel) shoppingcar.getModel());
            clearJTable(dtmt);
            SumAmount.setText("");SumPrice.setText("");OSumAmount.setText("");OSumPrice.setText("");
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null,"系统错误！结账失败!","提示",JOptionPane.WARNING_MESSAGE);
            ex.printStackTrace();
        }finally {
            jdbUtil.closeCon(con);
            dispose();
            new CheckOrder().setVisible(true);
//            checkOrder.detail_view();
//            checkOrder.setVisible(true);
        }
    }
    //add:添加购物车(main)
    private void add_ActionListener(ActionEvent e){
        DefaultTableModel dtm=(DefaultTableModel)shoppingcar.getModel();
        if(!is_select_DeskandAmount(dtm)) return;

        int flag=0;//判断是否有菜品添加

        JPanel[]jp={menu1,menu2,menu3,menu4,menu5};

        for(int i=0;i<5;++i){
            int co=jp[i].getComponentCount();
            for(int j=0;j<co;++j){
                //0 name:checkbox 1 price:jlabel 2:jpanel 3:image
                JPanel J=(JPanel)jp[i].getComponent(j);
                JPanel K=(JPanel)J.getComponent(0);
                //0:CHECKBOX 1:LABEL
                JCheckBox jcb=(JCheckBox) K.getComponent(0);
                JLabel jla=(JLabel) K.getComponent(1);
                if(jcb.isSelected()){
                    flag=1;
                    int r=in_shoppingcar(dtm,jcb.getText());
                    if(r!=-1){
                        //物品在购物车：数量+1即可
                        jcb.setSelected(false);
                        int num=(int)dtm.getValueAt(r,2)+1;

                        String p1=jla.getText().substring(0,jla.getText().length()-1);//单价
                        String p2=(String) dtm.getValueAt(r,4);//以前商品的总价
                        float p=Float.parseFloat(p2.substring(0,p2.length()-1))+Float.parseFloat(p1);
                        String price=StringUtil.FtoY(p);
                        dtm.setValueAt(num,r,2);
                        dtm.setValueAt(price,r,4);
                    }else{
                        //物品不在购物车中:直接添加
                        jcb.setSelected(false);
                        Vector row=new Vector();
                        int n=dtm.getRowCount();
                        //0序号 1名称 2数量 3单价 4总价
                        row.add(n+1);
                        row.add(jcb.getText());//1名称
                        row.add(1);//2数量
                        row.add(jla.getText());//3单价
                        row.add(jla.getText());//4总价
                        dtm.addRow(row);
                    }
                }
            }
        }
        if(flag==0){
            JOptionPane.showMessageDialog(null,"请先选择相关菜品！","提示",JOptionPane.WARNING_MESSAGE);
            return;
        }
        updata_sum(dtm);
    }
    //delete:删除购物车（main）
    private void delete_ActionListener(ActionEvent e){
        DefaultTableModel dtm=(DefaultTableModel) shoppingcar.getModel();
        if(!is_select_DeskandAmount(dtm)) return;
        JPanel[]jp={menu1,menu2,menu3,menu4,menu5};

        for(int i=0;i<5;++i){
            int co=jp[i].getComponentCount();
            for(int j=0;j<co;++j){
                JPanel J=(JPanel) jp[i].getComponent(j);
                JPanel K=(JPanel) J.getComponent(0);
                JCheckBox jch=(JCheckBox) K.getComponent(0);//名称
                JLabel jl=(JLabel) K.getComponent(1);//单价
                if(jch.isSelected()){
                    jch.setSelected(false);
                    int row=in_shoppingcar(dtm,jch.getText());
                    if(row==-1){
                        JOptionPane.showMessageDialog(null,"该商品已不在购物车中！请重新选择！","提示",JOptionPane.WARNING_MESSAGE);
                        return;
                    }else{
                        if((int)dtm.getValueAt(row,2)>1){
                            int num=(int)dtm.getValueAt(row,2)-1;
                            dtm.setValueAt(num,row,2);
                        }else{
                            dtm.removeRow(row);
                            index_update(dtm);
                        }
                    }
                }

            }
        }
        updata_sum(dtm);
    }
    //判断shoppingcar中是否已经有该物品(utility)
    private int in_shoppingcar(DefaultTableModel dtm,String name){
        int num=dtm.getRowCount();
        for(int i=0;i<num;++i){
            if(dtm.getValueAt(i,1).equals(name))
                return i;
        }
        return -1;
    }
    //更新食物序号（utility）
    private void index_update(DefaultTableModel dtm){
        int n=dtm.getRowCount();
        for(int i=0;i<n;++i){
            dtm.setValueAt(i+1,i,0);
        }
    }
    //更新总价（utility）
    private void updata_sum(DefaultTableModel dtm){
        int num=0;
        float sum=0;
        for(int i=0;i<dtm.getRowCount();++i){
            String p=(String) dtm.getValueAt(i,4);
            Float price=Float.parseFloat(p.substring(0,p.length()-1));
            sum+=price;

            num+=(int)dtm.getValueAt(i,2);
        }
        SumAmount.setText("商品数量： "+num+"件");
        SumPrice.setText("总价： "+StringUtil.FtoY(sum));
    }
    //判断是否选择用餐人数与桌号&&设置茶位费(utility)
    private boolean is_select_DeskandAmount(DefaultTableModel dtm){
        String desk=jc1.getSelectedItem().toString();
        String num=jc2.getSelectedItem().toString();
        if(!StringUtil.isNum(desk)){
            JOptionPane.showMessageDialog(null,"请选择就餐桌号！","提示",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if(!StringUtil.isNum(num)){
            JOptionPane.showMessageDialog(null,"请选择就餐人数！","提示",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        jc1.setEnabled(false);

        if(orderingcar.getRowCount()==0){//未下单
            //0序号 1名称 2数量 3单价 4总价
            int n=Integer.parseInt(num);
            String price=StringUtil.FtoY((float)n*5);
            if(dtm.getRowCount()==0){
                Vector row=new Vector<>();
                row.add(1);
                row.add("茶位费");
                row.add(n);
                row.add("5.00元");
                row.add(price);
                dtm.addRow(row);
            }else{
                dtm.setValueAt(price,0,4);
                dtm.setValueAt(n,0,2);
            }
            jc2.setEnabled(false);
        }
        return true;
    }
    //清空JTable(utility)
    private void clearJTable(DefaultTableModel dtm){
        int num=dtm.getRowCount();
        for(int i=0;i<num;++i){
            dtm.removeRow(0);
        }
    }


    //search：获得筛选条件(main) return:food
    private Food jb_searchActionListener(ActionEvent e){
        Food food=new Food();
        String s_Fname=this.Fname.getText();
        String s_Ftype=this.Ftype.getSelectedItem().toString();
        String s_Pfloor=this.Pfloor.getText();//价格下界
        String s_Pceil=this.Pceil.getText();//价格上届
        if(!StringUtil.isEmpty(s_Fname))    food.setFood_name(s_Fname);
        if(this.Ftype.getSelectedIndex()!=0)    food.setFood_type(s_Ftype);
        if(StringUtil.isEmpty(s_Pfloor)) s_Pfloor="0";
        if(StringUtil.isEmpty(s_Pceil)) s_Pceil="999";
        if(StringUtil.isNum(s_Pfloor)&&StringUtil.isNum(s_Pceil)){
            int sf=Integer.parseInt(s_Pfloor);
            int sc=Integer.parseInt(s_Pceil);
            if(sf<=sc){
                food.setPrice_floor(sf);
                food.setPrice_ceil(sc);
            }else{
                JOptionPane.showMessageDialog(null,"请重新输入价格范围");
            }
        }else{
            JOptionPane.showMessageDialog(null,"请重新输入价格范围");
        }
        return food;
        //得到了可用于筛选的food类别
    }
    //search：更新界面(main)
    private void jb_menu_update(Food food) throws Exception {
        //JPanel[]jp={null,menu1,menu2,menu3,menu4,menu5};
        String[]name={"主食类","包饭类","炸鸡类","年糕类","米酒烧酒类"};
        Map<String,JPanel> map=new HashMap<>(){{
            put("主食类",menu1);
            put("包饭类",menu2);
            put("炸鸡类",menu3);
            put("年糕类",menu4);
            put("米酒烧酒类",menu5);
        }};
        if (StringUtil.isEmpty(food.getFood_type())) {
            for(String key:map.keySet()){
                food.setFood_type(key);
                JPanel jp=map.get(food.getFood_type());
                menu_view(jp,food);
            }
        }else{
            JPanel jp=map.get(food.getFood_type());
            menu_view(jp,food);

            Map<JPanel,JScrollPane> mapp=new HashMap<>(){{
                put(menu1,js1);
                put(menu2,js2);
                put(menu3,js3);
                put(menu4,js4);
                put(menu5,js5);
            }};
            jt1.setSelectedComponent(mapp.get(jp));
        }
    }
    //init：原始界面更新(main)
    private void menu_get(JPanel jp,String type) throws Exception {
        Food food=new Food();
        food.setFood_type(type);

        menu_view(jp,food);
    }
    //菜单界面刷新与创建(utility)
    private void menu_view(JPanel jp,Food food) throws Exception {
        jp.removeAll();
        jp.setLayout(new GridLayout(0,3));

        JDBUtil jdbUtil=new JDBUtil();
        //进行改写
        Connection con=null;
        try{
            con= jdbUtil.getCon();
            ResultSet rs= FoodOpe.Foodlist(con,food);
            while(rs.next()){
                //System.out.println(rs.getString("Food_name"));
                String name=rs.getString("Food_name");
                float p=rs.getFloat("Food_price");
                String image_url=rs.getString("Food_image");
                String price=new DecimalFormat("0.00").format(p)+"元";

                JLabel jl1=new JLabel(itran(image_url,200,150));
                JLabel jl2=new JLabel(price);
                JCheckBox jc=new JCheckBox(name);

                JPanel jp1=new JPanel();
                jp1.add(jc);
                jp1.add(jl2);
                JPanel jp2=new JPanel();
                jp2.add(jp1,BorderLayout.WEST);
                jp2.add(jl1,BorderLayout.EAST);

                jp.add(jp2);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            jdbUtil.closeCon(con);
        }

        jp.updateUI();
    }

//    public static void main(String[]args) throws Exception {
//        new OrderFood().setVisible(true);
//    }
    private JLayeredPane layeredPane;
    private JPanel panel1;
    private JTabbedPane jt1;
    private JTabbedPane jt2;
    private JButton CheckOut;
    private JButton order;
    private JButton reorder;
    private JPanel 主食类;
    private JPanel 菜式导航;
    private JButton jb1;
    private JButton jb2;
    private JButton jb3;
    private JButton jb4;
    private JButton jb5;
    private JLabel jl1;
    private JLabel jl3;
    private JLabel jl4;
    private JLabel jl2;
    private JLabel jl5;
    private JLabel Title;
    private JTextField Fname;
    private JTextField Pfloor;
    private JTextField Pceil;
    private JButton jb_search;
    private JComboBox Ftype;
    private JScrollPane js1;
    private JScrollPane js2;
    private JScrollPane js3;
    private JScrollPane js4;
    private JScrollPane js5;
    private JPanel menu1;
    private JPanel menu2;
    private JPanel menu3;
    private JPanel menu4;
    private JPanel menu5;
    private JComboBox jc1;
    private JComboBox jc2;
    private JTable shoppingcar;
    private JLabel SumAmount;
    private JButton add;
    private JButton delete;
    private JLabel SumPrice;
    private JTable orderingcar;
    private JLabel OSumAmount;
    private JLabel OSumPrice;
    private JPanel jp1;
    private JPanel jp2;
    private JPanel jp3;
    private JPanel jp4;
}
