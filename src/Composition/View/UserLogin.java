package Composition.View;

import Composition.Model.User;
import Composition.Operation.UserOpe;
import Composition.Util.JDBUtil;
import Composition.Util.StringUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;

public class UserLogin extends JFrame {
    public static User current_user=null;//保留当前user的信息
    public UserLogin(){
        initComponets();
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("国际校区点餐系统");
        this.setLayeredPane(layeredPane);
        this.setBounds(450,250,750,500);

    }
    private void initComponets(){
        //设置背景
        layeredPane=new JLayeredPane();
        JPanel background=new JPanel();
        background.setBounds(0,0,750,500);
        JLabel bg=new JLabel(itran("src/Composition/util_images/Entrance.jpg",750,500));
        background.add(bg);
        layeredPane.add(background,JLayeredPane.DEFAULT_LAYER);

        //设置panel
        panel.setBackground(new Color(255,255,255,200));
        panel.setBounds(180,100,400,250);//panel一定要设置大小才能显出来
        layeredPane.add(panel,JLayeredPane.MODAL_LAYER);

        jp1.setBackground(new Color(255,255,255,0));
        jp2.setBackground(new Color(255,255,255,0));
        jp3.setBackground(new Color(255,255,255,0));

        user.setIcon(itran("src/Composition/util_images/userName.png",20,20));
        password.setIcon(itran("src/Composition/util_images/password.png",20,20));

        //登录
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    login_ActionListener(e);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //清空
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear_ActionListener(e);
            }
        });

        //注册
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register_ActionListener(e);
            }
        });
    }
    //login(main)
    private void login_ActionListener(ActionEvent e) throws Exception {
        //获取信息--user库中匹配--id存在--成功/密码错误--id不存在--成功后进入下一页面(传入id参数)
        JDBUtil jdbUtil=new JDBUtil();
        Connection con=null;
        try{
            con=jdbUtil.getCon();
            String userid=usertxt.getText();
            String userpwd=new String(pwdtxt.getPassword());

            //判断登录条件
            if(StringUtil.isEmpty(userid)){
                JOptionPane.showMessageDialog(null,"请输入账号！","提示",JOptionPane.WARNING_MESSAGE);
                return;//先执行finally,再return
            }
            if(StringUtil.isEmpty(userpwd)){
                JOptionPane.showMessageDialog(null,"请输入密码！","提示",JOptionPane.WARNING_MESSAGE);
                return;//先执行finally,再return
            }

            //数据库匹配
            ResultSet re= UserOpe.UserExist(con,new User(userid));
            if(re.next()){  //id存在
                String pwd=re.getString("User_pwd");
                if(pwd.equals(userpwd)){
                    current_user=new User(re.getString("User_id"),re.getInt("User_pwd"),re.getString("User_mail"),re.getString("User_phone"));
                    JOptionPane.showMessageDialog(null,"恭喜您!\n用户 '"+userid+"' 已成功登录！","提示",JOptionPane.WARNING_MESSAGE);
                    this.dispose();
                    new UserMenu().setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(null,"密码错误！\n请重新尝试！","提示",JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }else{//id 不存在
                JOptionPane.showMessageDialog(null,"该账号不存在！","提示",JOptionPane.WARNING_MESSAGE);
                return;
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            jdbUtil.closeCon(con);
        }
    }
    //clear(main)
    private void clear_ActionListener(ActionEvent e){
        usertxt.setText("");
        pwdtxt.setText("");
    }
    //register(main)
    private void register_ActionListener(ActionEvent e){
        this.dispose();
        new UserRegist().setVisible(true);
    }

    private Icon itran(String name,int w,int h){
        ImageIcon image=new ImageIcon(name);
        Image img=image.getImage();
        img=img.getScaledInstance(w,h,Image.SCALE_DEFAULT);
        image.setImage(img);
        return image;
    }

//    public static void main(String[]args){
//        new UserLogin().setVisible(true);
//    }

    private JLayeredPane layeredPane;
    private JPanel panel;
    private JButton login;
    private JButton clear;
    private JButton register;
    private JPasswordField pwdtxt;
    private JTextField usertxt;
    private JLabel Title;
    private JLabel user;
    private JLabel password;
    private JPanel jp1;
    private JPanel jp2;
    private JPanel jp3;
    private JButton back;
}
