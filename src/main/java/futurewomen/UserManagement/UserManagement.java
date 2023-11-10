package futurewomen.UserManagement;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static futurewomen.Utils.Constants.DESTINATION_SERIALIZED_OBJ;
import static futurewomen.Utils.Constants.SERIALIZED_USER_LIST;

/*
User Registration and Login using Java Methods
What to do: Create methods for registering a new user and logging in.
Store username and password in an array or list.

Expected: Two separate methods, one for registration and one for login.
New users should be stored in memory for now.

 */
public class UserManagement {
    private List<String> userNameList = new ArrayList<>();
    private List<String> passwordList = new ArrayList<>();
    private List<User> userList = new ArrayList<>();

    public boolean registerNewUser(String userName, String password, Role role) {
        if (userNameList.contains(userName)) {
            System.out.println("Username already taken.");
            return false;
        }else{
            userNameList.add(userName);
            passwordList.add(password);
            userList.add(userGenerator(userName, password, role));
            saveUsersToFile(SERIALIZED_USER_LIST);
            return true;
        }
    }

    private User userGenerator(String userName, String password, Role role) {
        return switch (role) {
            case ADMIN -> new Admin(userName, password, role);
            case STUDENT -> new StudentUser(userName, password, role);
            case TEACHER -> new Teacher(userName, password, role);
        };
    }

    public boolean login(String userName, String password) {
        if (userNameList.contains(userName)) {
            return (passwordList.get(userNameList.indexOf(userName)).equals(password));
        } else return false;
    }

    public boolean deleteUser(String userName, String password) {
        if (login(userName, password)) {
            Optional<User> result = userList.stream().filter(user -> user.getUserName().equals(userName)).findFirst();
            if (result.isPresent()) {
                userList.remove(result.get());
                return true;
            }
        }
        return false;
    }
    public boolean updateUsername(String currentUserName, String password, String newUserName) {
        if (login(currentUserName, password) && !userNameList.contains(newUserName)) {
            Optional<User> result = userList.stream().filter(user -> user.getUserName().equals(currentUserName)).findFirst();
            if (result.isPresent()) {
                result.get().setUserName(newUserName);
                userNameList.add(userNameList.indexOf(currentUserName),newUserName);
                return true;
            }
        }
        return false;
    }
    public boolean updatePassword(String userName, String currentPassword, String newPassword) {
        if (login(userName, currentPassword)) {
            Optional<User> result = userList.stream().filter(user -> user.getPassword().equals(currentPassword)).findFirst();
            if (result.isPresent()) {
                result.get().setPassword(newPassword);
                passwordList.add(passwordList.indexOf(currentPassword),newPassword);
                return true;
            }
        }
        return false;
    }
     public String displayUser(String userName, String password){
        return getUser(userName, password).toString();
     }
    public User getUser(String userName, String password){
        if (login(userName, password)) {
            Optional<User> result = userList.stream().filter(user -> user.getUserName().equals(userName)).findFirst();
            if(result.isPresent()) return result.get();
        }
        return null;
    }

     public void displayUsers(){
        userList.forEach(System.out::println);
     }

     public void saveUsersToFile(String fileName){
        Path path = Path.of(DESTINATION_SERIALIZED_OBJ + fileName);
         try (ObjectOutputStream outObj = new ObjectOutputStream(
                 new FileOutputStream(path.toFile()))) {
             outObj.writeObject(userList);
             System.out.println("...User list serialized successfully.\n");
         } catch (IOException e) {
             System.out.println("User serialization failed. " + e.getMessage());
         }
     }

     public void readUsersFromFile(String fileName){
         Path path = Path.of(DESTINATION_SERIALIZED_OBJ + fileName);
         try (ObjectInputStream in = new ObjectInputStream(
                 new FileInputStream(path.toFile()))) {
             userList = (ArrayList<User>) in.readObject();
             System.out.println("...User list deserialized successfully.\n");

         } catch (IOException | ClassNotFoundException e) {
             System.out.println("User deserialization failed. " + e.getMessage());
         }
     }

}
