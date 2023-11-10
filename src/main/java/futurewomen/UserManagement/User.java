package futurewomen.UserManagement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Objects;

public abstract class User implements Serializable {
    @Expose
    private String name;
    @Expose
    private String userName;
    private String password;
    @Expose
    private Role role;


    protected User(String userName, String password, Role role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
    }
    protected User(){
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    protected String getPassword() {
        return password;
    }

    protected void setPassword(String password) {
        this.password = password;
    }

    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected String getUserName() {
        return userName;
    }

    protected void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name==null?"":name.replaceAll("\\s", "").toLowerCase(),
                userName.replaceAll("\\s", "").toLowerCase(),
                password,
                role);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj==null) return false;
        if (obj instanceof User user){
            return user.hashCode()==this.hashCode();
        }
        return false;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.toJson(this);
    }
}
