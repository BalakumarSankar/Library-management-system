import jdk.nashorn.internal.runtime.ECMAException;
import lms.Book;
import lms.exceptions.BookException;
import lms.exceptions.ImproperReturnException;
import lms.exceptions.OutOfStockException;
import lms.exceptions.StudentException;
import sun.lwawt.macosx.CSystemTray;

import java.awt.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import  lms.*;

import javax.crypto.spec.PSource;

import static lms.Inventory.*;

public class Main {
    static Scanner scanner=new Scanner(System.in);
    public static void homepage()
    {
        System.out.println("                WELCOME TO LIBRARY                  ");
        System.out.println("    enter 1 to add book");
        System.out.println("    enter 2 to view list of books");
        System.out.println("    enter 3 to borrow book");
        System.out.println("    enter 4 to return book");
        System.out.println("    enter 5 to view list of all authors");
        System.out.println("    enter 6 add stocks ");
        System.out.println("    enter 7 to create new student account");
        System.out.println("    enter 8 to view list of students");
        System.out.println("    enter 9 to view stock of all books");
        System.out.println("    enter 10 to view stock of required book");
        System.out.println("    enter 11 to get the list of all books currently borrowed by a student with due");
        System.out.println("    enter 12 to get the list of all books currently borrowed by everyone");
        System.out.println("    enter 13 to get the borrowed history of a student");
        System.out.println("    enter 0 to exit");
        System.out.println("    enter your choice");



    }
    public static void main(String[] args) {
         int ch=10;
        do {
            homepage();
            ch=scanner.nextInt();
            switch (ch) {
                case 1:
                    System.out.println("enter the book name :");
                    String name=scanner.next();
                    System.out.println("enter the author name");
                    String an=scanner.next();
                    String aid=isNewAuthor(an);
                    System.out.println("enter the publisher");
                    String pb=scanner.next();
                    System.out.println("enter the language");
                    String ln=scanner.next();
                    System.out.println("enter the genre");
                    String gr=scanner.next();
                    Book b=new Book(name,an,aid,pb,ln,gr);
                    System.out.println("the book is created ... now enter the number of stocks ");
                    int n=scanner.nextInt();
                    try{
                        addBook(b,n);
                    }catch (Exception e)
                    {

                    }

                    break;

                case 2:
                    viewlistofbooks();
                    break;

                case 3:
                    System.out.println("enter the student id");
                    String sid=scanner.next();
                    System.out.println("enter the id of the book that you need to borrow");
                    String bid = scanner.next();
                    System.out.println("enter the number of books you need to borrow");
                    int count=scanner.nextInt();
                    try{
                        borrowBook(sid,bid,count);

                    }
                    catch (BookException | StudentException | OutOfStockException e)
                    {
                        System.out.println(e.getMessage());
                    }

                    break;
                case 4:
                    System.out.println("enter the student id ");
                    sid=scanner.next();
                    System.out.println("enter the book id of the book you need to return");
                    bid=scanner.next();
                    System.out.println("enter the record id");
                    String rid=scanner.next();
                    System.out.println("enter the number of stocks you are returning");
                    count=scanner.nextInt();
                    List<String > l=new ArrayList<String>();
                    for(int i=0;i<count;i++)
                    {
                        System.out.println("Enter the serial number you need to return :");
                        String temp=scanner.next();
                        l.add(temp);
                    }
                    try{
                        returnBook(bid,sid,rid,l,count);
                    }
                    catch (BookException | StudentException | ImproperReturnException e)
                    {
                        System.out.println(e.getMessage());
                    }


                    break;

                case 5:
                    viewlistofAuthours();
                    break;
                case 6:
                    System.out.println("enter the book id for which you need to increase stock");
                     bid=scanner.next();
                        System.out.println("enter the number of books");
                        n = scanner.nextInt();
                        try{
                            addStock(bid, n);
                        }
                        catch (Exception e)
                        {
                            System.out.println("the entered book id does not exits");
                        }
                        break;

                case 7:
                    System.out.println("enter the student name :");
                    name=scanner.next();
                    System.out.println("enter the student id :");
                    sid=scanner.next();
                    System.out.println("enter the student phone number");
                    String ph=scanner.next();
                    try {
                    createNewStudentAccount(name,sid,ph);
                    }
                    catch (Exception e)
                    {
                        System.out.println("student account is not created");
                    }
                    break;
                case 8:
                    try{
                        viewlistofStudents();
                    }
                    catch (Exception e)
                    {
                        System.out.println("the list is empty");
                    }
                    break;
                case 9:
                    try {
                        getStock();
                    }
                    catch (Exception e)
                    {

                    }

                    break;
                case 10:
                    System.out.println("enter the book id ");
                    bid=scanner.next();
                    try{
                        getStock(bid);
                    }
                    catch (BookException e)
                    {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 11:
                    System.out.println("enter the student id ");
                    sid=scanner.next();
                    try{
                        getBorrowedDetails(sid);
                    }catch(StudentException  e)
                    {
                        System.out.println(e.getMessage());
                    }

                    break;
                case 12:
                    getBorrowedDetails();
                    break;
                case 13:
                    System.out.println("enter the student id ");
                    sid=scanner.next();
                    System.out.println(" history of the books borrowed  ");
                    try{
                        history(sid);
                    }catch(StudentException  e)
                    {
                        System.out.println(e.getMessage());
                    }

                    break;
                case 14:
                    System.out.println("enter the student id ");
                    sid=scanner.next();
                    calculateStudentFine(sid);
                    break;



            }
        }while(ch!=0);
        System.out.println("Hello world!");
    }
}