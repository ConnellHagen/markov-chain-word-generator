import java.io.File;

public class Main{
    public static void main(String args[]){
        WordGenerator wordGen = new WordGenerator(new File("books/temp.dat"));
        System.out.println(wordGen.nextWord());
    }
}