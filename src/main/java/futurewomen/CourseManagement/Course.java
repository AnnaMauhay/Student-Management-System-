package futurewomen.CourseManagement;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Objects;

public class Course implements Serializable {
    private int courseID;
    private String name;

    private int units = 3;

    public Course(int courseID, String name) {
        this.courseID = courseID;
        this.name = name;
    }

    public Course(int courseID, String name, int units) {
        this.courseID = courseID;
        this.name = name;
        this.units = units;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name==null?"":name.replaceAll("\\s", "").toLowerCase(),
                courseID);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj==null) return false;
        if (obj instanceof Course course){
            return course.hashCode()==this.hashCode();
        }
        return false;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
