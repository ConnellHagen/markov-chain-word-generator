import java.io.File;

public class Main{
    public static void main(String args[]){
        File book = new File("books/temp.txt");
        WordGenerator wordGen = new WordGenerator(book);
        for(int i = 0; i < 10; i++){
            System.out.println(wordGen.nextWord());
        }
    }
}