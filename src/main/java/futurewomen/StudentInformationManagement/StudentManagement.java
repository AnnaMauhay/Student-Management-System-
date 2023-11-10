package futurewomen.StudentInformationManagement;

import futurewomen.Utils.CRUD;
import futurewomen.Utils.SerializeDeserializer;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static futurewomen.Utils.Constants.DESTINATION_SERIALIZED_OBJ;
import static futurewomen.Utils.Constants.SERIALIZED_STUDENTS_LIST;

public class StudentManagement {
    private List<Student> studentsList = new ArrayList<>();
    private CRUD<Student> crud = new CRUD<>();
    private SerializeDeserializer<Student> serializeDeserializer = new SerializeDeserializer<>();

    public StudentManagement(List<Student> students) {
        this.studentsList = students;
    }

    public StudentManagement() {
    }

    public boolean addStudent(Student student) {
        return crud.add(student, studentsList);
    }

    public boolean deleteStudent(Student student) {
        return crud.delete(student, studentsList);
    }

    public void serializeStudentsList() {
        serializeDeserializer.serialize(studentsList, Path.of(DESTINATION_SERIALIZED_OBJ + SERIALIZED_STUDENTS_LIST));
    }

    public Student getStudent(int studentID) {
        return studentsList.stream()
                .filter(student -> student.getStudentID()==studentID)
                .findFirst().get();
    }

    public void deserializeStudentsList() {
        studentsList = serializeDeserializer.deserialize(Path.of(DESTINATION_SERIALIZED_OBJ + SERIALIZED_STUDENTS_LIST));
        if (studentsList.isEmpty()) studentsList = enrolledStudentsGenerator();
    }

    public void displayStudents() {
        studentsList.forEach(System.out::println);
    }

    private List<Student> enrolledStudentsGenerator() {
        List<Student> list = new ArrayList<>();
        int ctr = 1;
        for (int i = 0; i < 15; i++) {
            list.add(new Student(ctr, "Student " + ctr));
            ctr++;
        }
        return list;
    }

}
