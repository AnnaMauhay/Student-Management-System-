package futurewomen.CourseManagement;

import futurewomen.StudentInformationManagement.Student;
import futurewomen.Utils.CRUD;
import futurewomen.Utils.SerializeDeserializer;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static futurewomen.Utils.Constants.*;

public class CourseManagement {
    private List<Course> offeredCourses = new ArrayList<>();
    private CRUD<Course> crud = new CRUD<>();
    private SerializeDeserializer<Course> courseSerializeDeserializer = new SerializeDeserializer<>();
    private SerializeDeserializer<Student> studentSerializeDeserializer = new SerializeDeserializer<>();
    record CourseGradeRec(Student student, Course course, Float grade) {
        @Override
        public String toString() {
            return "CourseGradeRec{" +
                    "student=" + student +
                    ", course=" + course +
                    ", grade=" + grade +
                    '}';
        }
    }
    private Map<Student, List<CourseGradeRec>> enrolledStudents = new HashMap<>();
    public CourseManagement() {
        deserializeOfferedCourses();
        deserializeEnrolledStudents();
    }



    public boolean enrollStudentToCourse(Student student, Course course) {
        if (enrolledStudents.containsKey(student)) {
            if (enrolledStudents.get(student).stream().noneMatch(
                    courseGradeRec -> courseGradeRec.course.equals(course))) {
                enrolledStudents.get(student).add(new CourseGradeRec(student, course, 0f));
                serializeEnrolledStudents();
                return true;
            } else {
                System.out.println("Student is already enrolled to this course.");
                return false;
            }
        } else {
            List<CourseGradeRec> courseGradeRecList = new ArrayList<>();
            courseGradeRecList.add(new CourseGradeRec(student, course, 0f));
            enrolledStudents.put(student, courseGradeRecList);
            serializeEnrolledStudents();
            return true;
        }
    }

    public Course getCourse(int courseID){
        return offeredCourses.stream()
                .filter(course -> course.getCourseID()==courseID)
                .findFirst().get();
    }
    public void updateGrade(Student student, Course course, Float grade){
        if (enrolledStudents.containsKey(student)) {
            if (enrolledStudents.get(student).stream().noneMatch(
                    courseGradeRec -> courseGradeRec.course.equals(course))) {
                System.out.println("Student is not enrolled to this course.");
            } else {
                enrolledStudents.get(student).removeIf(courseGradeRec -> courseGradeRec.course.equals(course));
                enrolledStudents.get(student).add(new CourseGradeRec(student, course, grade));
            }
        }else System.out.println("Student is not enrolled to any course.");
    }

    public void displayStudentGrades(Student student){
        enrolledStudents.get(student).forEach(System.out::println);
    }

    public void displayStudentsInCourse(Course course){
        List<Student> students = new ArrayList<>();
        List<CourseGradeRec> courseGradeRecList = new ArrayList<>();
        enrolledStudents.values().forEach(courseGradeRecList::addAll);
        List<CourseGradeRec> enrolledInCourse = courseGradeRecList.stream().filter(courseGradeRec -> courseGradeRec.course.equals(course))
                .toList();
        enrolledInCourse.forEach(courseGradeRec -> students.add(courseGradeRec.student()));
        System.out.println(course.getName()+":");
        students.forEach(System.out::println);
    }

    public boolean addOfferedCourse(Course course) {
        if (crud.add(course, offeredCourses)) {
            serializeOfferedCourses();
            return true;
        }else return false;
    }

    public boolean deleteOfferedCourse(int courseID) {
        Course course = offeredCourses.stream()
                .filter(c -> c.getCourseID()==courseID)
                .findFirst().get();
        return crud.delete(course, offeredCourses);
    }

    public void serializeOfferedCourses() {
        courseSerializeDeserializer.serialize(offeredCourses, Path.of(DESTINATION_SERIALIZED_OBJ + SERIALIZED_OFFERED_COURSE));
    }

    public void deserializeOfferedCourses() {
        offeredCourses = courseSerializeDeserializer.deserialize(Path.of(DESTINATION_SERIALIZED_OBJ + SERIALIZED_OFFERED_COURSE));
        if (offeredCourses.isEmpty()) offeredCourses=offeredCourseGenerator();
    }

    public void serializeEnrolledStudents() {
        var pathName = Path.of(DESTINATION_SERIALIZED_OBJ + SERIALIZED_ENROLLED_STUDENTS);
        try (ObjectOutputStream outObj = new ObjectOutputStream(
                new FileOutputStream(pathName.toFile()))) {
            outObj.writeObject(enrolledStudents);
            System.out.println("...Enrolled Students serialized successfully.\n");
        } catch (IOException e) {
            System.out.println("Enrolled Students serialization failed. " + e.getMessage());
        }
    }

    public void deserializeEnrolledStudents() {
        var pathName = Path.of(DESTINATION_SERIALIZED_OBJ + SERIALIZED_ENROLLED_STUDENTS);
        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(pathName.toFile()))) {
            enrolledStudents = (Map<Student, List<CourseGradeRec>>) in.readObject();
            System.out.println("...Enrolled Students deserialized successfully.\n");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Enrolled Students deserialization failed. " + e.getMessage());
        }
    }
    public void displayOfferedCourses() {
        offeredCourses.forEach(System.out::println);
    }

    public List<Course> getOfferedCourses() {
        return offeredCourses;
    }

    private List<Course> offeredCourseGenerator() {
        List<Course> list = new ArrayList<>();
        int ctr = 1;
        for (CourseTitle title : CourseTitle.values()) {
            list.add(new Course(ctr++, title.getLabel()));
        }
        return list;
    }

}
