import java.util.*;

class Student {
    int id;
    String name;
    String email;
    List<Course> enrolledCourses = new ArrayList<>();

    public Student(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    void enroll(Course c) {
        enrolledCourses.add(c);
    }

    void viewCourses() {
        System.out.println("Courses Enrolled:");
        for (Course c : enrolledCourses) {
            System.out.println("- " + c.name);
        }
    }
}

class Course {
    String code;
    String name;
    boolean isTrained;

    public Course(String code, String name) {
        this.code = code;
        this.name = name;
        this.isTrained = false;
    }
}

class Exam {
    String courseCode;
    int score;

    public Exam(String courseCode, int score) {
        this.courseCode = courseCode;
        this.score = score;
    }
}

class Result {
    Student student;
    Map<String, Integer> courseScores = new HashMap<>();

    void addExam(Exam exam) {
        courseScores.put(exam.courseCode, exam.score);
    }

    void showResult() {
        System.out.println("Result:");
        for (String course : courseScores.keySet()) {
            System.out.println(course + ": " + courseScores.get(course));
        }
    }
}

class Admin {
    List<Student> students = new ArrayList<>();
    List<Course> courses = new ArrayList<>();
    Map<Integer, Result> results = new HashMap<>();

    void registerStudent(String name, String email) {
        int id = students.size() + 1;
        Student s = new Student(id, name, email);
        students.add(s);
        System.out.println("Registered: " + name + " with ID: " + id);
    }

    void offerCourse(String code, String name) {
        courses.add(new Course(code, name));
        System.out.println("Course Offered: " + name);
    }

    void trainStudent(int studentId, String courseCode) {
        Student s = findStudentById(studentId);
        Course c = findCourseByCode(courseCode);
        if (s != null && c != null) {
            c.isTrained = true;
            s.enroll(c);
            System.out.println("Student " + s.name + " trained in " + c.name);
        }
    }

    void conductExam(int studentId, String courseCode, int score) {
        Student s = findStudentById(studentId);
        if (s != null) {
            Result r = results.getOrDefault(s.id, new Result());
            r.student = s;
            r.addExam(new Exam(courseCode, score));
            results.put(s.id, r);
            System.out.println("Exam recorded for " + s.name);
        }
    }

    void viewResult(int studentId) {
        Result r = results.get(studentId);
        if (r != null) {
            r.showResult();
        } else {
            System.out.println("No result found.");
        }
    }

    Student findStudentById(int id) {
        for (Student s : students) {
            if (s.id == id) return s;
        }
        return null;
    }

    Course findCourseByCode(String code) {
        for (Course c : courses) {
            if (c.code.equalsIgnoreCase(code)) return c;
        }
        return null;
    }
}

public class StudentManagementSystem {
    public static void main(String[] args) {
        Admin admin = new Admin();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Student Management System ===");
            System.out.println("1. Register Student");
            System.out.println("2. Offer Course");
            System.out.println("3. Train Student");
            System.out.println("4. Conduct Exam");
            System.out.println("5. View Result");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    sc.nextLine();
                    System.out.print("Enter Student Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();
                    admin.registerStudent(name, email);
                    break;
                case 2:
                    sc.nextLine();
                    System.out.print("Enter Course Code: ");
                    String code = sc.nextLine();
                    System.out.print("Enter Course Name: ");
                    String cname = sc.nextLine();
                    admin.offerCourse(code, cname);
                    break;
                case 3:
                    System.out.print("Enter Student ID: ");
                    int sid = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Course Code: ");
                    String ccode = sc.nextLine();
                    admin.trainStudent(sid, ccode);
                    break;
                case 4:
                    System.out.print("Enter Student ID: ");
                    sid = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Course Code: ");
                    ccode = sc.nextLine();
                    System.out.print("Enter Score: ");
                    int score = sc.nextInt();
                    admin.conductExam(sid, ccode, score);
                    break;
                case 5:
                    System.out.print("Enter Student ID: ");
                    sid = sc.nextInt();
                    admin.viewResult(sid);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
            }
        } while (choice != 0);

        sc.close();
    }
}
