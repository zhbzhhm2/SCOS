package code.source.es.model;
import java.io.Serializable;
/**
 * Created by zhang on 16-10-12.
 */
public class User implements Serializable {
    private String userName;
    private String passWord;
    private Boolean oldUser;
    public User(){
    }
    public User(String userName, String passWord, Boolean oldUser) {
        this.userName = userName;
        this.passWord = passWord;
        this.oldUser = oldUser;
    }
    public User(String userName){
        this.userName=userName;
        oldUser=false;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Boolean getOldUser() {
        return oldUser;
    }

    public void setOldUser(Boolean oldUser) {
        this.oldUser = oldUser;
    }
}
