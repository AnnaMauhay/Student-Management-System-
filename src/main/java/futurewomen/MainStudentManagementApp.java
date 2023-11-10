package futurewomen;

import futurewomen.CourseManagement.Course;
import futurewomen.CourseManagement.CourseManagement;
import futurewomen.StudentInformationManagement.Student;
import futurewomen.StudentInformationManagement.StudentManagement;
import futurewomen.UserManagement.Role;
import futurewomen.UserManagement.User;
import futurewomen.UserManagement.UserManagement;

import java.util.InputMismatchException;
import java.util.Scanner;

import static futurewomen.Utils.Constants.APP_HEADER;
import static futurewomen.Utils.Constants.SERIALIZED_USER_LIST;

public class MainStudentManagementApp {
    private boolean isLoggedIn = false;
    private User loggedUser;
    private Role loggedRole = Role.STUDENT;
    private boolean quit = false;
    private Scanner input = new Scanner(System.in);
    private UserManagement userManager;
    private StudentManagement studentManager;
    private CourseManagement courseManager;

    public MainStudentManagementApp() {
        userManager = new UserManagement();
        studentManager = new StudentManagement();
        courseManager = new CourseManagement();
        studentManager.deserializeStudentsList();
    }

    public void run() {
        System.out.println(APP_HEADER);
        while (!quit) {
            switch (loggedRole) {
                case ADMIN, TEACHER:
                    displayAdminMainMenu();
                    break;
                default:
                    displayStudentMainMenu();
            }
        }
    }

    private void displayAdminMainMenu() {
        System.out.println("""
                MAIN MENU
                Please select the number of your choice:
                1 - User Management
                2 - Student Management
                3 - Course Management
                4 - Exit
                """);
        try {
            int selection = input.nextInt();
            input.nextLine();
            switch (selection) {
                case 1:
                    displayAdminUserManagementUI();
                    break;
                case 2:
                    studentManagement();
                    break;
                case 3:
                    courseManagement();
                    break;
                case 4:
                    exitApp();
                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println("Please enter integers only.");
        }
    }

    private void displayStudentMainMenu() {
        System.out.println("""
                MAIN MENU
                Please select the number of your choice:
                1 - User Management
                2 - Course Management
                3 - Exit
                """);
        try {
            int selection = input.nextInt();
            input.nextLine();
            switch (selection) {
                case 1:
                    displayStudentUserManagementUI();
                    break;
                case 2:
                    courseManagement();
                    break;
                case 3:
                    exitApp();
                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println("Please enter integers only.");
        }
    }

    private void courseManagement() {
        System.out.println("Course Management");
        courseManager.deserializeOfferedCourses();
        boolean wantsBack = false;
        while (!wantsBack) {
            System.out.println("""
                    Please select the number of your choice:
                    1 - View Offered Courses
                    2 - Add Offered Course
                    3 - Delete Offered Course
                    4 - Display Students Enrolled in each Course
                    5 - Enroll Student to Course
                    6 - Back
                    """);
            try {
                int selection = input.nextInt();
                input.nextLine();
                switch (selection) {
                    case 1:
                        courseManager.displayOfferedCourses();
                        break;
                    case 2:
                        displayAddOfferedCourse();
                        break;
                    case 3:
                        displayDeleteOfferedCourse();
                        break;
                    case 4:
                        displayStudentsEnrolled();
                        break;
                    case 5:
                        displayEnrollStudentToCourse();
                        break;
                    case 6:
                        wantsBack = true;
                        courseManager.serializeOfferedCourses();
                        break;
                }
                input.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Please enter integers only.");
            }
        }
    }

    private void displayEnrollStudentToCourse() {
        try {
            System.out.println("Please enter the course ID that you wish to enroll to.");
            courseManager.displayOfferedCourses();
            System.out.println("----------------");

            int courseID = input.nextInt();
            input.nextLine();

            System.out.println("Please enter the student ID that you wish to enroll.");
            studentManager.displayStudents();
            System.out.println("----------------");

            int studentID = input.nextInt();
            input.nextLine();

            Student student = studentManager.getStudent(studentID);
            Course course =courseManager.getCourse(courseID);
            if(courseManager.enrollStudentToCourse(student,course)){
                System.out.println(student.getName() + " is successfully enrolled to "+ course.getName());
            }else System.out.println("Enrollment failed.");

        } catch (InputMismatchException e) {
            System.out.println("Please enter integers only.");
        }
        input.nextLine();
    }

    private void displayDeleteOfferedCourse() {
        try {
            System.out.println("Please enter the course ID that you wish to delete.");
            courseManager.displayOfferedCourses();
            System.out.println("----------------");

            int id = input.nextInt();
            input.nextLine();

            if (courseManager.deleteOfferedCourse(id)) System.out.println("Course successfully deleted");
            else System.out.println("No course matches that input.");

        } catch (InputMismatchException e) {
            System.out.println("Please enter integers only.");
        }
        input.nextLine();
    }

    private void displayAddOfferedCourse() {
        try {
            System.out.println("Please enter the course title");
            String title = input.nextLine();

            System.out.println("Please enter the course ID");
            int id = input.nextInt();
            input.nextLine();

            System.out.println("Please enter the number of units");
            int units = input.nextInt();
            input.nextLine();

            Course course = new Course(id, title, units);
            courseManager.addOfferedCourse(course);
            System.out.println("Successfully added course: "+course);
        } catch (InputMismatchException e) {
            System.out.println("Please enter integers only.");
        }
    }

    private void displayStudentsEnrolled() {
        for (Course course : courseManager.getOfferedCourses()) {
            courseManager.displayStudentsInCourse(course);
        }
    }

    private void studentManagement() {
        System.out.println("Student Management");
        studentManager.deserializeStudentsList();
        boolean wantsBack = false;
        while (!wantsBack) {
            System.out.println("""
                    Please select the number of your choice:
                    1 - View All Students
                    2 - Back
                    """);
            try {
                int selection = input.nextInt();
                input.nextLine();
                switch (selection) {
                    case 1:
                        studentManager.displayStudents();
                        break;
                    //TODO: implement UI for Student CRUD
                    case 2:
                        wantsBack = true;
                        studentManager.serializeStudentsList();
                        break;
                }
                input.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Please enter integers only.");
            }
        }
    }

    private void displayAdminUserManagementUI() {
        userManager.readUsersFromFile(SERIALIZED_USER_LIST);
        boolean wantsBack = false;
        while (!wantsBack) {
            System.out.println("""
                    Please select the number of your choice:
                    1 - Login
                    2 - Register as new User
                    3 - Display Users
                    4 - Back
                    """);
            try {
                int selection = input.nextInt();
                input.nextLine();
                switch (selection) {
                    case 1:
                        displayLoginUI();
                        break;
                    case 2:
                        displayRegistrationUI();
                        break;
                    case 3:
                        userManager.displayUsers();
                        break;
                    //TODO: Implement UI for User Management Update and Delete
                    case 4:
                        wantsBack = true;
                        userManager.saveUsersToFile(SERIALIZED_USER_LIST);
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter integers only.");
                input.nextLine();
            }
        }
    }

    private void displayStudentUserManagementUI() {
        userManager.readUsersFromFile(SERIALIZED_USER_LIST);
        boolean wantsBack = false;
        while (!wantsBack) {
            System.out.println("""
                    Please select the number of your choice:
                    1 - Login
                    2 - Register as new User
                    3 - Back
                    """);
            try {
                int selection = input.nextInt();
                input.nextLine();
                switch (selection) {
                    case 1:
                        displayLoginUI();
                        break;
                    case 2:
                        displayRegistrationUI();
                        break;
                    case 3:
                        wantsBack = true;
                        userManager.saveUsersToFile(SERIALIZED_USER_LIST);
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter integers only.");
                input.nextLine();
            }
        }
    }

    private void exitApp() {
        System.out.println("Goodbye!");
        input.close();
        quit = true;
    }

    private void displayRegistrationUI() {
        System.out.println("Please enter your username.");
        String userName = input.nextLine();
        System.out.println("Please enter your password.");
        String password = input.nextLine();
        System.out.println("Please select your role:");
        int ctr = 1;
        for (Role role : Role.values()) {
            System.out.println(ctr++ + "-" + role.getLabel());
        }
        int selection = input.nextInt();
        input.nextLine();

        if (userManager.registerNewUser(userName, password, Role.values()[selection - 1])) {
            System.out.println("You have successfully registered.");
        } else System.out.println("Please try again.");
        input.nextLine();
    }

    private void displayLoginUI() {
        System.out.println("Please enter your username.");
        String userName = input.nextLine();
        System.out.println("Please enter your password.");
        String password = input.nextLine();
        isLoggedIn = userManager.login(userName, password);

        if (isLoggedIn) {
            loggedUser = userManager.getUser(userName, password);
            loggedRole = loggedUser.getRole();
            System.out.println("You have successfully logged in as " + loggedRole.getLabel());
            System.out.println("Welcome " + userName);
        } else System.out.println("Sorry, incorrect username and/or password.");
        input.nextLine();
    }
}