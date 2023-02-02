package lms;

import java.util.ArrayList;
import java.util.List;

public class Author {
    static  int counter=0;
    private String author_id;
    private String author_name;
 public Author(String author_name)
 {
     this.author_id=String.valueOf(++counter);
     this.author_name=author_name;
 }

    public String getAuthor_id() {
        return author_id;
    }
    public String getAuthor_name()
    {
        return author_name;
    }

    @Override
    public String toString() {
        return "Author{" +
                "author_id=" + author_id +
                ", author_name='" + author_name +
                '}';
    }
}
