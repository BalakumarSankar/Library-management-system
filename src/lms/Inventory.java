package lms;

import lms.exceptions.BookException;
import lms.exceptions.ImproperReturnException;
import lms.exceptions.OutOfStockException;
import lms.exceptions.StudentException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Inventory {
    static private List<Book> listofbooks=new ArrayList<Book>();
    static private List<Author> listofauthor=new ArrayList<Author>();
    static private List<Student> lisofstudents=new ArrayList<Student>();

    private static Map<String,Queue<String >> serialno= new HashMap<String,Queue<String>>();
    private static Map<String,Map<String,ArrayList<String>>> lend=new HashMap<String,Map<String,ArrayList<String>>>();
    private static final List<Record> records = new ArrayList<>();
   // private static Random rand = new Random();
    public static void  borrowBook(String  sid, String bookid,int count) throws BookException ,StudentException , OutOfStockException {
        Book b=isbookExists(bookid);
        if(b==null){
            throw new BookException("book id does not exits");
        }
        if(studentExists(sid)==null){
            throw new StudentException("student id not found");
        }
        if(serialno.get(bookid).size()<count)
        {
            throw new OutOfStockException("the requested number of books are currently not available....TRY AGAIN LATER" +"\n the stock available is :"+serialno.get(bookid).size());
        }
        Map<String,serialStatus>  serialnumber=new HashMap<>();
        for(int i=0;i<count;i++)
        {
            serialStatus s=new serialStatus(Status.Borrowed,0,null);
            serialnumber.put(reduceStock(bookid,sid),s);
        }

            Record r=new Record(sid,bookid,serialnumber);
            records.add(r);
            System.out.println("BOOK SUCESSFULLY BORROWED!!!" +
                    "you borrowed book: "+b.getBook_name());
            System.out.println("the corresponding record of the borrow is :");
            System.out.println(r);

    }
    public static void calculateStudentFine(String sid)
    {
     List<Record> r=getRecord(sid);
     long fine=0;
     for(Record rec:r)
     {
       fine+=totalFineforRecord(rec.getRecord_id());
       rec.updateFine(fine);
     }
        System.out.println("the fine amount to be paid is :"+fine);
    }
    public static long totalFineforRecord(String rid)
    {
    Record r=records.stream().filter(r1-> Objects.equals(r1.getRecord_id(), rid)).findFirst().orElse(null);
    Map<String,serialStatus> s=r.getSerial_no();
    Iterator<Map.Entry<String, serialStatus>> itr = s.entrySet().iterator();
    long fine=0;

        while(itr.hasNext())
        {
            Map.Entry<String, serialStatus> entry = itr.next();
            LocalDateTime temp=entry.getValue().getReturndedDate();
            if(temp==null)
                temp=LocalDateTime.now();
            if(r.getenddate().compareTo(LocalDateTime.now())<1)
            {
                long l=calculateFine(r.getenddate(),LocalDateTime.now());
                entry.getValue().setFineForthisBook(l);
            }

           fine+=entry.getValue().getFineForthisBook();
        }
        return fine;
    }
    public static void updateFine()
    {

    }
    public static long calculateFine(LocalDateTime a, LocalDateTime b)
    {
        Duration duration = Duration.between(a, b);
        long diff = duration.toDays();
        //System.out.println(diff);
        if(diff<0)
            return 0;
        else return diff*10;

    }
    public static void returnBook(String bid,String sid,String rid,List<String> l,int count) throws BookException, ImproperReturnException,StudentException
    {
        Record r=searchRecord(rid);
        if(r==null)
        {
            throw new ImproperReturnException("no records found to return");
        }
        Book b=isbookExists(bid);
        if(b==null){
            throw new BookException("book id does not exits");
        }
        Student s=studentExists(sid);
        if(s==null){
            throw new StudentException("student id not found");
        }
        if(!Objects.equals(r.getStudent_id(), sid))
        {
            throw new StudentException("student id and record mismatch");
        }
        if(!Objects.equals(r.getBookid(),bid))
        {
            throw new BookException("book id and record mismatch");
        }
        if(count>lend.get(bid).get(sid).size())
        {
            throw new ImproperReturnException("cannot return more books");
        }

        for(String li :l)
        {
            if(r.getbookStatus(li)==Status.Borrowed)
            {
                r.updateReceivedCount(1);
                lend.get(bid).get(sid).remove(li);
                serialno.get(bid).add(li);
                long f=calculateFine(r.getenddate(),LocalDateTime.now());
                serialStatus ss=new serialStatus(Status.returned,f,LocalDateTime.now());
                r.updateSerialno(li,ss);
            }
            else{
                throw new ImproperReturnException("Cannot return book that is not borrowed or already returned");
            }

        }
        if(r.getReceivedcount()==r.getSerialnoCount())
            r.setStatus(Status.returned);
        else {
            r.setStatus(Status.partiallyReturned);
        }
        System.out.println("the book :"+b.getBook_name()+" is successfully returned by "+s.getStudent_name() +" on "+LocalDateTime.now());

    }

    private static String reduceStock(String b,String sid) throws NullPointerException{
        if(serialno.get(b).size()==0)
         return null;
        String t= serialno.get(b).poll();
        if(!lend.containsKey(b))
        {
            lend.put(b,new HashMap<>());
            lend.get(b).put(sid,new ArrayList<String>());
        }
        if(!lend.get(b).containsKey(sid))
        {
            lend.get(b).put(sid,new ArrayList<String>());
        }
            lend.get(b).get(sid).add(t);
        return t;
    }
    public static void addStock(String b,int stock) throws NullPointerException{

        Queue<String> t = serialno.get(b);
        Book book=isbookExists(b);
        int l=book.getLastsn();
            for (int i =1; i <= stock; i++) {
                t.add(Integer.toString(++l));
            }
           book.updatelastsn(l);
            //System.out.println(book.getLastsn());

    }
    public static Book isbookExists(String id){
      for (Book b:listofbooks)
      {
          if(Objects.equals(b.getBook_id(), id))
              return b;
      }
      return null;
    }

    public static Record searchRecord(String rid)
    {
     return records.stream().filter(r-> Objects.equals(r.getRecord_id(), rid) && r.getStatus()!=Status.returned).findFirst().orElse(null);
    }
    public static void addBook(Book b,int stock) throws NullPointerException
    {
        listofbooks.add(b);

            if (!serialno.containsKey(b.getBook_id()))
            {
                serialno.put(b.getBook_id(),new ArrayDeque<>());
            }
            addStock(b.getBook_id(),stock);




    }
    public static void getBorrowedDetails(String sid) throws StudentException
    {
        Student s=studentExists(sid);
        if(s==null){
            throw new StudentException("student id not found");
        }
        List<Record> l=records.stream().filter(r->r.getStudent_id().equals(sid) && r.getStatus()==Status.Borrowed).collect(Collectors.toList());
        if(l.size()==0)
            System.out.println("the list is empty");
        for(Record r:l)
        {
            System.out.println(r.toString());
        }
    }
    public static void getBorrowedDetails(){
        List<Record> l=records.stream().filter(r-> r.getStatus()==Status.Borrowed).collect(Collectors.toList());
        if(l.size()==0)
            System.out.println("the list is empty");
        for(Record r:l)
        {
            System.out.println(r.toString());
        }
    }
    public static void history(String sid) throws StudentException
    {
        Student s=studentExists(sid);
        if(s==null){
            throw new StudentException("student id not found");
        }
        List<Record> l=records.stream().filter(r-> Objects.equals(r.getStudent_id(), sid)).collect(Collectors.toList());
        if(l.size()==0)
            System.out.println("the student did not borrow ");
        for(Record r:l)
        {
            System.out.println(r.toString());
        }

    }

 public static void getStock() throws Exception
 {
  for(Book b :listofbooks)
  {
      getStock(b.getBook_id());
  }
 }
 public static void getStock(String bid) throws BookException
 {
     Book b=isbookExists(bid);
     if(b==null)
     {
         throw new BookException("book id does not exits");
     }
     if(serialno.get(bid)!=null)
     {
         System.out.println(b.getBook_name()+"--->"+serialno.get(bid).size());
     }
     else{
         System.out.println("current book :"+b.getBook_name()+"is OUT OF STOCK");

     }
 }

    public static List<Record> getRecord(String   s)
    {
        //String id=s.getStudent_id();
        List<Record> t=records.stream().filter(record -> record.getStudent_id().equals(s)).collect(Collectors.toList());
        return t;
    }
    public static List<Record> getRecord(Book b)
    {
        String id=b.getBook_id();
        List<Record> t=records.stream().filter(record -> record.getBookid().equals(id)).collect(Collectors.toList());
        return t;
    }
    public static List<Record> getRecord(String  id, LocalDateTime d1, LocalDateTime d2)
    {
        List<Record> t=records.stream().filter(record -> record.getStudent_id().equals(id) && record.getstartdate().compareTo(d1)>=0 && record.getenddate().compareTo(d2)<=0).collect(Collectors.toList());
        return t;
    }
    public static void viewlistofbooks(){
        System.out.println(listofbooks.size());
        for(Book book:listofbooks)
        {
            System.out.println(book.toString());
        }
    }
    public static String isNewAuthor(String name)
    {
        for(Author a:listofauthor)
        {
            if(a.getAuthor_name()==name)
                return a.getAuthor_id();
        }

        return createNewAuthor(name);
    }
    public static Student studentExists(String id)
    {

            for(Student s:lisofstudents)
            {
                if(Objects.equals(s.getStudent_id(), id))
                {
                    return s;

                }

            }
            return  null;

    }

    private static void createNewStudent() {

    }

    public static String createNewAuthor(String name) {
        Author a=new Author(name);
        listofauthor.add(a);
        return a.getAuthor_id();
    }
public static void createNewStudentAccount (String name,String id,String ph) throws NullPointerException
{
 Student s=new Student(id,name,ph);
 lisofstudents.add(s);
}

    public static void viewlistofAuthours(){
        for(Author auth:listofauthor)
        {
            System.out.println(auth.toString());
        }}
public static void viewlistofStudents() throws NullPointerException
    {
        for(Student s:lisofstudents)
        {
            System.out.println(s.toString());
        }
    }
}
