package Composition.Model;

public class User {
    private String User_id;
    private int User_pwd;

    private String E_mail;
    private String Phone;

    public User(){
        super();
    }

    public User(String user_id){
        super();
        User_id=user_id;
    }
    public User(String user_id, int user_pwd,String e_mail,String phone) {
        super();
        User_id = user_id;
        User_pwd = user_pwd;
        E_mail=e_mail;
        Phone=phone;
    }

    public String getE_mail() {
        return E_mail;
    }

    public void setE_mail(String e_mail) {
        E_mail = e_mail;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String user_id) {
        User_id = user_id;
    }

    public int getUser_pwd() {
        return User_pwd;
    }

    public void setUser_pwd(int user_pwd) {
        User_pwd = user_pwd;
    }
}
