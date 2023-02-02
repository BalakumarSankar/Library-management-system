package lms;

import java.util.ArrayList;
import java.util.List;

import static lms.Book.*;

public class Student {
    private String student_id;
    private String student_name;
    private String student_phone;
    private int student_fine;
    Student(String student_id,String student_name,String student_phone)
    {
    this.student_id=student_id;
    this.student_name=student_name;
    this.student_phone=student_phone;
    this.student_fine=0;
    }

    public int getStudent_fine() {
        return student_fine;
    }
    void updateFine(int a)
    {
        student_fine+=a;
    }

    public String getStudent_id() {

        return student_id;
    }

    @Override
    public String toString() {
        return "Student{" +
                "student_id='" + student_id + '\'' +
                ", student_name='" + student_name + '\'' +
                ", student_phone='" + student_phone + '\'' +
                ", student_fine=" + student_fine +
                '}';
    }

    public String getStudent_name() {

        return student_name;
    }
}
