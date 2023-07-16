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

public class UserInfo extends JFrame{
    public UserInfo(){
        initComponents();
        //标题，位置大小，最小化，可变性，关闭程序，添加层次
        this.setTitle("国际校区点餐系统");
        this.setBounds(450,250,750,500);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayeredPane(layeredPane);
    }
    private void initComponents(){
        //设置背景
        layeredPane=new JLayeredPane();
        JPanel background=new JPanel();
        background.setBounds(0,0,750,500);
        JLabel bg=new JLabel(itran("src/Composition/util_images/Entrance.jpg",750,500));
        background.add(bg);
        layeredPane.add(background,JLayeredPane.DEFAULT_LAYER);

        //设置panel
        panel.setBackground(new Color(255,255,255,200));
        panel.setBounds(180,50,400,350);//panel一定要设置大小才能显出来
        layeredPane.add(panel,JLayeredPane.MODAL_LAYER);

        jp2.setBackground(new Color(255,255,255,0));
        jp3.setBackground(new Color(255,255,255,0));
        jp4.setBackground(new Color(255,255,255,0));
        jp5.setBackground(new Color(255,255,255,0));
        jp6.setBackground(new Color(255,255,255,0));

        id.setIcon(itran("src/Composition/util_images/userName.png",20,20));
        pwd.setIcon(itran("src/Composition/util_images/password.png",20,20));
        mail.setIcon(itran("src/Composition/util_images/email.png",20,20));
        phone.setIcon(itran("src/Composition/util_images/phone.png",20,20));

        //获取信息并显示
        idtxt.setText(UserLogin.current_user.getUser_id());
        pwdtxt.setText(Integer.toString(UserLogin.current_user.getUser_pwd()));
        mailtxt.setText(UserLogin.current_user.getE_mail());
        phonetxt.setText(UserLogin.current_user.getPhone());

        //不能编辑
        idtxt.setEditable(false);
        pwdtxt.setEditable(false);
        mailtxt.setEditable(false);
        phonetxt.setEditable(false);

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new UserMenu().setVisible(true);
            }
        });
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idtxt.setEditable(true);
                pwdtxt.setEditable(true);
                mailtxt.setEditable(true);
                phonetxt.setEditable(true);
                idtxt.setForeground(new Color(121,121,121));
                pwdtxt.setForeground(new Color(121,121,121));
                mailtxt.setForeground(new Color(121,121,121));
                phonetxt.setForeground(new Color(121,121,121));
            }
        });
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    save_ActionListener(e);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    private void save_ActionListener(ActionEvent e) throws Exception {
        if(mailtxt.isEditable()==false){
            JOptionPane.showMessageDialog(null,"请先按下‘编辑’按钮！","提示",JOptionPane.WARNING_MESSAGE);
            return;
        }
        JDBUtil jdbUtil=new JDBUtil();
        Connection con=null;
        try{
            con=jdbUtil.getCon();
            //检查：改错，退回；可以，保留
            String id=idtxt.getText();
            String pwd=pwdtxt.getText();
            String mail=mailtxt.getText();
            String phone=phonetxt.getText();
            if(StringUtil.isEmpty(id)||id.length()>10){
                idtxt.setText(UserLogin.current_user.getUser_id());
            }
            if(id.length()>10){
                JOptionPane.showMessageDialog(null,"昵称超过10字!\n请重新取名！","提示",JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(StringUtil.isEmpty(pwd)||(!StringUtil.isNum(pwd))||(pwd.length()>8)){
                pwdtxt.setText(String.valueOf(UserLogin.current_user.getUser_pwd()));
            }
            if((!StringUtil.isNum(pwd))||(pwd.length()>8)){
                JOptionPane.showMessageDialog(null,"请创建1-8位纯数字密码！","提示",JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(StringUtil.isEmpty(mail)||(!StringUtil.isEmail(mail))){
                mailtxt.setText(UserLogin.current_user.getE_mail());
            }
            if(!StringUtil.isEmail(mail)){
                JOptionPane.showMessageDialog(null,"请填写正确的邮箱！","提示",JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(StringUtil.isEmpty(phone)||(!StringUtil.isPhone(phone))){
                phonetxt.setText(UserLogin.current_user.getPhone());
            }
            if(!StringUtil.isPhone(phone)){
                JOptionPane.showMessageDialog(null,"请填写正确的手机号！","提示",JOptionPane.WARNING_MESSAGE);
                return;
            }

            //调用数据库
            if(id.equals(UserLogin.current_user.getUser_id())){//不改用户名的话可直接修改内容
                UserLogin.current_user=new User(id,Integer.parseInt(pwd),mail,phone);
                int out=UserOpe.UserModify(con,UserLogin.current_user,new User(id));
                if(out==1){
                    JOptionPane.showMessageDialog(null,"账号修改成功！","提示",JOptionPane.WARNING_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(null,"账号修改失败！\n请重试！","提示",JOptionPane.WARNING_MESSAGE);
                }
            }else{
                ResultSet re= UserOpe.UserExist(con,new User(id));
                if(re.next()){
                    JOptionPane.showMessageDialog(null,"账号名已重复！\n请更改后重试！","提示",JOptionPane.WARNING_MESSAGE);
                    idtxt.setText(id);
                    return;
                }else{
                    UserLogin.current_user=new User(id,Integer.parseInt(pwd),mail,phone);
                    int out=UserOpe.UserModify(con,UserLogin.current_user,new User(id));
                    if(out==1){
                        JOptionPane.showMessageDialog(null,"账号修改成功！","提示",JOptionPane.WARNING_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(null,"账号修改失败！\n请重试！","提示",JOptionPane.WARNING_MESSAGE);
                    }
                }
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }finally {
            jdbUtil.closeCon(con);
        }
    }
//    public static void main(String[]args){
//
//        new UserInfo().setVisible(true);
//    }

    private Icon itran(String name,int w,int h){
        ImageIcon image=new ImageIcon(name);
        Image img=image.getImage();
        img=img.getScaledInstance(w,h,Image.SCALE_DEFAULT);
        image.setImage(img);
        return image;
    }
    private JLayeredPane layeredPane;
    private JLabel Title;
    private JTextField idtxt;
    private JTextField pwdtxt;
    private JTextField mailtxt;
    private JTextField phonetxt;
    private JButton back;
    private JButton edit;
    private JButton save;
    private JLabel id;
    private JLabel pwd;
    private JLabel mail;
    private JLabel phone;
    private JPanel panel;
    private JPanel jp2;
    private JPanel jp3;
    private JPanel jp4;
    private JPanel jp5;
    private JPanel jp6;
}
