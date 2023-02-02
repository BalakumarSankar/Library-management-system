package lms;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

enum Status{Borrowed,returned,partiallyReturned, notFound;
};
public class Record {
    private static int counter=0;
    private String record_id;
    private String student_id;
    private int receivedcount;
    private String book_id;
    private Map<String,serialStatus> serial_no=new HashMap<String,serialStatus>();
    private LocalDateTime startdate;
    private LocalDateTime enddate ;
    private Status status;
    private long totalfine;
    private String  returnedDate;


   public String getStudent_id()
    {
        return this.student_id;
    }
    public String getRecord_id(){
       return this.record_id;
    }
    public int getReceivedcount()
    {
        return receivedcount;
    }
    Record(String s,String b,Map<String,serialStatus> sn)
    {
        record_id=String.valueOf(++counter);
        returnedDate="Not Yet Returned";
        student_id=s;
        book_id=b;
        serial_no=sn;
        receivedcount=0;
        startdate= LocalDateTime.now();
        enddate=LocalDateTime.now().minusDays(10);
       status=Status.Borrowed;
       totalfine=0;

    }
public void setTotalfine(long l)
{

}
    public void setStatus(Status status) {
        this.status = status;
        this.returnedDate=LocalDateTime.now().toString();
    }
    public void updateSerialno(String id,serialStatus s)
    {
      this.serial_no.put(id,s);
    }
   public void setReturnedDate(LocalDateTime date)
   {
       returnedDate=date.toString();
   }
    public String getBookid() {
        return book_id;
    }

    public LocalDateTime getstartdate() {
        return startdate;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getenddate() {
        return enddate;
    }
    public Map<String,serialStatus> getSerial_no()
    {
        return serial_no;
    }

    @Override
    public String toString() {
        return "Record{" +
                "record_id='" + record_id + '\'' +
                ", student_id='" + student_id + '\'' +
                ", receivedcount=" + receivedcount +
                ", book_id='" + book_id + '\'' +
                ", serial_no=" + serial_no +
                ", startdate=" + startdate +
                ", enddate=" + enddate +
                ", status=" + status +
                ", Totalfine=" + totalfine +
                ", returnedDate='" + returnedDate + '\'' +
                '}';
    }

    public Status getbookStatus(String l) {
       if(serial_no.containsKey(l))
       {
        //   System.out.println(serial_no.get(l));
           return serial_no.get(l).getStatus();
       }
       return Status.notFound;
    }
 public void updateFine(long l)
 {
     totalfine+=l;
 }
    public int getSerialnoCount() {
       return serial_no.size();
    }

    public void updateReceivedCount(int i) {
      ++receivedcount;
    }
}
