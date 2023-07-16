package Composition.View;

import Composition.Model.User;
import Composition.Operation.UserOpe;
import Composition.Util.JDBUtil;
import Composition.Util.StringUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Savepoint;

public class UserRegist extends JFrame {


    public UserRegist(){
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

        jp1.setBackground(new Color(255,255,255,0));
        jp2.setBackground(new Color(255,255,255,0));
        jp3.setBackground(new Color(255,255,255,0));
        jp4.setBackground(new Color(255,255,255,0));
        jp5.setBackground(new Color(255,255,255,0));
        jp6.setBackground(new Color(255,255,255,0));

        id.setIcon(itran("src/Composition/util_images/userName.png",20,20));
        pwd.setIcon(itran("src/Composition/util_images/password.png",20,20));
        confirm.setIcon(itran("src/Composition/util_images/new_password.png",20,20));
        mail.setIcon(itran("src/Composition/util_images/email.png",20,20));
        phone.setIcon(itran("src/Composition/util_images/phone.png",20,20));

        //输入框设置
        idtxt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                MouseClick(idtxt);
            }
        });
        pwdtxt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                MouseClick(pwdtxt);
            }
        });
        confirmtxt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                MouseClick(confirmtxt);
            }
        });
        mailtxt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                MouseClick(mailtxt);
            }
        });
        phonetxt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                MouseClick(phonetxt);
            }
        });
        //clear
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clear_ActionListener(e);
            }
        });
        //register
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Register_ActionListener(e);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        //back
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Back_ActionListener(e);
            }
        });
    }
    private Icon itran(String name,int w,int h){
        ImageIcon image=new ImageIcon(name);
        Image img=image.getImage();
        img=img.getScaledInstance(w,h,Image.SCALE_DEFAULT);
        image.setImage(img);
        return image;
    }
    private void MouseClick(JTextField jt){
        jt.setText("");
        jt.setForeground(new Color(0,0,0));
    }
    private void Clear_ActionListener(ActionEvent e){
        idtxt.setText("");
        pwdtxt.setText("");
        confirmtxt.setText("");
        mailtxt.setText("");
        phonetxt.setText("");
    }
    private void Register_ActionListener(ActionEvent e) throws Exception {
        //判断--获取--判断id是否存在--添加--成功弹窗--返回UserLogin
        JDBUtil jdbUtil=new JDBUtil();
        Connection con=null;
        try{
            con= jdbUtil.getCon();
            //判断是否满足条件
            String id=idtxt.getText();
            String pwd=pwdtxt.getText();
            String re_pwd=confirmtxt.getText();
            String mail=mailtxt.getText();
            String phone=phonetxt.getText();
            if(StringUtil.isEmpty(id)){
                JOptionPane.showMessageDialog(null,"请填写账号昵称！","提示",JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(id.length()>10){
                JOptionPane.showMessageDialog(null,"昵称超过10字!\n请重新取名！","提示",JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(StringUtil.isEmpty(pwd)){
                JOptionPane.showMessageDialog(null,"请输入密码！","提示",JOptionPane.WARNING_MESSAGE);
                return;
            }
            if((!StringUtil.isNum(pwd))||(pwd.length()>8)){
                JOptionPane.showMessageDialog(null,"请创建1-8位纯数字密码！","提示",JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(StringUtil.isEmpty(re_pwd)){
                JOptionPane.showMessageDialog(null,"请再次输入密码进行确认！","提示",JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(!re_pwd.equals(pwd)){
                JOptionPane.showMessageDialog(null,"两次输入密码不一致！\n请重新确认输入密码！","提示",JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(StringUtil.isEmpty(phone)){
                JOptionPane.showMessageDialog(null,"请填写手机号！","提示",JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(!StringUtil.isPhone(phone)){
                JOptionPane.showMessageDialog(null,"请填写正确的手机号！","提示",JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(StringUtil.isEmpty(mail)){
                JOptionPane.showMessageDialog(null,"请填写邮箱！","提示",JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(!StringUtil.isEmail(mail)){
                JOptionPane.showMessageDialog(null,"请填写正确的邮箱！","提示",JOptionPane.WARNING_MESSAGE);
                return;
            }


            //调用数据库（判断id是否存在&&添加）
            ResultSet re= UserOpe.UserExist(con,new User(id));
            if(re.next()){
                JOptionPane.showMessageDialog(null,"该账号名已被注册！\n请重新设置账号名称！","提示",JOptionPane.WARNING_MESSAGE);
                return;
            }else{
                int out=UserOpe.UserAdd(con,new User(id,Integer.parseInt(pwd),mail,phone));
                if(out==1){
                    JOptionPane.showMessageDialog(null,"恭喜您！\n账号 '"+id+"' 已成功注册！","提示",JOptionPane.WARNING_MESSAGE);
                    this.dispose();
                    new UserLogin().setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(null,"注册失败！\n请重新尝试！","提示",JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

        }catch (Exception ex){
            JOptionPane.showMessageDialog(null,"注册失败！\n请重新尝试！","提示",JOptionPane.WARNING_MESSAGE);
            ex.printStackTrace();
        }finally {
            jdbUtil.closeCon(con);
        }
    }
    private void Back_ActionListener(ActionEvent e){
        this.dispose();
        new UserLogin().setVisible(true);
    }

    public static void main(String[]args){

        new UserRegist().setVisible(true);
    }
    private JLayeredPane layeredPane;
    private JLabel Title;
    private JTextField idtxt;
    private JTextField pwdtxt;
    private JTextField confirmtxt;
    private JButton clear;
    private JButton register;
    private JLabel pwd;
    private JPanel panel;
    private JLabel id;
    private JPanel jp1;
    private JPanel jp2;
    private JPanel jp3;
    private JLabel confirm;
    private JPanel jp4;
    private JButton back;
    private JPanel jp5;
    private JTextField mailtxt;
    private JLabel mail;
    private JTextField phonetxt;
    private JPanel jp6;
    private JLabel phone;
}
