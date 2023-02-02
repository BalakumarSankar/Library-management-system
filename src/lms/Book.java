package lms;

import java.util.ArrayList;
import java.util.List;

public class Book {
    static private long counter=0;
   // static private ArrayList<Book> listofbooks=new ArrayList<Book>();
private  String book_id;
private String book_name;
private String book_lang;
private String author_name;
private String book_genre;
private String book_publ;
private String author_id;
private int lastsn=0;

    public int getLastsn() {
        return lastsn;
    }
    public void updatelastsn(int a)
    {
        lastsn=a;
    }

    public Book()
{
    book_genre="";
    book_name="";
    book_lang="";
    book_publ="";
}


    public String getAuthor_id(){
    return author_id;
    }
public  Book(String book_name,String author_name,String  author_id,String book_publ,String book_lang,String book_genre)
{
    book_id=String.valueOf(++counter);
    this.book_name=book_name;
    this.author_name=author_name;
    this.author_id=author_id;
    this.book_publ=book_publ;
    this.book_lang=book_lang;
    this.book_genre=book_genre;
}
public String getBook_id() {
        return book_id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public String getBook_name()
{
    return this.book_name;
}
public String getBook_lang()
{
    return this.book_lang;
}
public String getBook_genre()
{
    return this.book_genre;
}
public String getBook_publ()
{
    return this.book_publ;
}

    @Override
    public String toString() {
        return "Book{" +
                "book_id=" + book_id +
                ", book_name='" + book_name + '\'' +
                ", book_lang='" + book_lang + '\'' +
                ", author_id=" + author_id +
                ", book_genre='" + book_genre + '\'' +
                ", book_publ='" + book_publ + '\'' +
                '}';
    }


}
