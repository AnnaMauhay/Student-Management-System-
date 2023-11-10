package futurewomen.StudentInformationManagement;

import com.google.gson.Gson;
import futurewomen.CourseManagement.Course;
import futurewomen.UserManagement.Role;
import futurewomen.UserManagement.StudentUser;
import futurewomen.UserManagement.User;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Student implements Serializable {
    private String name;
    private int studentID;
    private List<Course> enrolledCourses;
    private User loginCredentials;

    public User getLoginCredentials() {
        return loginCredentials;
    }

    public void setLoginCredentials(User loginCredentials) {
        this.loginCredentials = loginCredentials;
    }

    public Student(int studentID, String studentName) {
        this.studentID = studentID;
        this.name = studentName;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name==null?"":name.replaceAll("\\s", "").toLowerCase(),
                enrolledCourses,
                studentID);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj==null) return false;
        if (obj instanceof Student student){
            return student.hashCode()==this.hashCode();
        }
        return false;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
