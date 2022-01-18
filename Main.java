import java.io.File;

public class Main{
    public static void main(String args[]){
        File book = new File("books/temp.txt");
        WordGenerator wordGen = new WordGenerator(book);
        System.out.println(wordGen.nextWord());
    }
}