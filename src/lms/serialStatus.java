package lms;

import java.time.LocalDateTime;

public class serialStatus {
    private Status status;
    private long fineForthisBook;
    private LocalDateTime returndedDate;
   public serialStatus(Status s, long f, LocalDateTime t)
    {
        status=s;
        fineForthisBook=f;
        returndedDate=t;
    }
    public void setStatus(Status s)
    {
        status=s;
    }
    public void setFineForthisBook(long a)
    {
        fineForthisBook=a;
    }
public void setReturndedDate(LocalDateTime t)
{
    returndedDate=t;
}
    public LocalDateTime getReturndedDate() {
        return returndedDate;
    }

    public long getFineForthisBook() {
        return fineForthisBook;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "serialNoStatus{" +
                "status=" + status +
                ", fineForthisBook=" + fineForthisBook +
                ", returndedDate=" + returndedDate +
                '}';
    }
}
