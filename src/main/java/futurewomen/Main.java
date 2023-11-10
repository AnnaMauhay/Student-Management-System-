package futurewomen;

import futurewomen.CourseManagement.Course;
import futurewomen.CourseManagement.CourseManagement;
import futurewomen.StudentInformationManagement.Student;
import futurewomen.StudentInformationManagement.StudentManagement;
import futurewomen.UserManagement.Role;
import futurewomen.UserManagement.UserManagement;

public class Main {
    private static UserManagement userManager = new UserManagement();
    private static StudentManagement studentManager = new StudentManagement();
    private static CourseManagement courseManager = new CourseManagement();
    public static void main(String[] args) {
        MainStudentManagementApp app = new MainStudentManagementApp();
        app.run();

//        testUserManagementCRUD();
//        testStudentPersistence();
//        testCourseManagement();

//        Student st1 = new Student(1, "Anna");
//        Student st2 = new Student(2, "Liza");
//        Student st3 = new Student(3, "Bella");
//        Student st4 = new Student(4, "Erwin");
//
//        Course cr1 = new Course(1, "Science");
//        Course cr2 = new Course(2, "Math");
//        Course cr3 = new Course(3, "Language");
//
//        CourseManagement.enrollStudentToCourse(st1, cr1);
//        CourseManagement.enrollStudentToCourse(st1, cr2);
//        CourseManagement.enrollStudentToCourse(st1, cr3);
//
//        CourseManagement.enrollStudentToCourse(st2, cr1);
//        CourseManagement.enrollStudentToCourse(st2, cr3);
//
//        CourseManagement.enrollStudentToCourse(st3, cr1);
//
//        CourseManagement.enrollStudentToCourse(st4, cr3);
//
//        System.out.println(cr1.getName());
//        CourseManagement.displayStudentsInCourse(cr1);
//        System.out.println("=================");
//
//        System.out.println(cr2.getName());
//        CourseManagement.displayStudentsInCourse(cr2);
//        System.out.println("=================");
//
//        System.out.println(cr3.getName());
//        CourseManagement.displayStudentsInCourse(cr3);
//        System.out.println("=================");
//
//        CourseManagement.updateGrade(st3, cr1, 25f);
//        CourseManagement.displayStudentGrades(st3);
    }

    private static void testStudentPersistence() {
//        Student st1 = new Student(1, "Anna");
//        Student st2 = new Student(2, "Liza");
//        Student st3 = new Student(3, "Bella");
//
//        StudentManagement.addStudent(st1);
//        StudentManagement.addStudent(st2);
//        StudentManagement.addStudent(st3);
//        StudentManagement.displayStudents();
//        System.out.println("================");
//
//        StudentManagement.serializeStudentsList();

        studentManager.deserializeStudentsList();
        studentManager.displayStudents();

    }

    private static void testCourseManagement() {
//        Course cr1 = new Course(1, "Science");
//        Course cr2 = new Course(2, "Math");
//        Course cr3 = new Course(3, "Language");
//        CourseManagement.addOfferedCourse(cr1);
//        CourseManagement.addOfferedCourse(cr2);
//        CourseManagement.addOfferedCourse(cr3);
//        CourseManagement.displayOfferedCourses();
//        System.out.println("================");
//
//        CourseManagement.serializeOfferedCourses();

        courseManager.deserializeOfferedCourses();
        courseManager.displayOfferedCourses();
    }

    private static void testUserManagementCRUD() {
        userManager.registerNewUser("Anna", "pass", Role.ADMIN);
        userManager.registerNewUser("Liza", "pass", Role.STUDENT);
        userManager.updateUsername("Liza", "pass", "Lizabells");
        userManager.deleteUser("Anna", "pass");
        userManager.registerNewUser("Amanda", "pass2", Role.TEACHER);
        userManager.displayUsers();

        userManager.saveUsersToFile("UserList.txt");
        userManager.readUsersFromFile("UserList.txt");
    }
}
