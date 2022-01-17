import java.io.File;
import java.util.Scanner;

public class WordGenerator {

    private float[] firstLetters;
    private float[][] nextLetters;
    private File source;

    public WordGenerator(File source) {

        this.source = source;
        //A-Z
        firstLetters = new float[26];
        //A-Z, " " where " " includes punctuation, and all non-letter characters
        nextLetters = new float[26][27];

        generateProbs();
    }

    //figures out the probabilities of each letter being followed by another
    private void generateProbs(){

        int[] firstLetFreq = new int[26];
        int[][] nextLetFreq = new int[26][27];
        for(int i = 0; i < firstLetFreq.length; i++){
            firstLetFreq[i] = (int)(Math.random() * 100);
        }
        for(int i = 0; i < nextLetFreq.length; i++){
            for(int j = 0; j < nextLetFreq[i].length; j++){
                nextLetFreq[i][j] = (int)(Math.random() * 100);
            }
        }

        System.out.println("were here");
        /*
        try{
            Scanner lineScanner = new Scanner(source);
            while(lineScanner.hasNextLine()){
                Scanner wordScanner = new Scanner(lineScanner.nextLine());
                while(wordScanner.hasNext("\\w*\\W")){

                    String tempWord = wordScanner.next("\\w*\\W");
                    for(int i = 0; i < tempWord.length(); i++){
                        if(i == 0){
                            firstLetFreq[alphaToInt(tempWord.charAt(0))] += 1;
                        }
                        else if(i == tempWord.length() - 1){
                            nextLetFreq[alphaToInt(tempWord.charAt(i - 1))][26] += 1;
                        }
                        else{
                            nextLetFreq[alphaToInt(tempWord.charAt(i - 1))][alphaToInt(tempWord.charAt(i))] += 1;
                        }
                    }

                }
                wordScanner.close();
            }
            lineScanner.close();
        }
        catch(Exception e){
            e.printStackTrace();
            return;
        }
        */

        int firstLetTotal = 0;
        for(int i = 0; i < firstLetFreq.length; i++){
            //taking count for the first letters
            firstLetTotal += firstLetFreq[i];

            int nextLetTotal = 0;
            //taking count for the subsequent letters
            for(int j = 0; j < nextLetFreq.length; j++){
                nextLetTotal += nextLetFreq[i][j];
                System.out.println(nextLetTotal);
            }

            //copying over probabilities for subsquent letters
            for(int j = 0; j < nextLetters.length; j++){
                nextLetters[i][j] = ((float)nextLetFreq[i][j]) / nextLetTotal;
            }
        }

        //copying over probabilities for first letters
        for(int i = 0; i < firstLetters.length; i++){
            firstLetters[i] = ((float)firstLetFreq[i]) / firstLetTotal;
        }

    }

    private int alphaToInt(char let){
        switch(let){
            case 'a': case 'A': return 0;
            case 'b': case 'B': return 1;
            case 'c': case 'C': return 2;
            case 'd': case 'D': return 3;
            case 'e': case 'E': return 4;
            case 'f': case 'F': return 5;
            case 'g': case 'G': return 6;
            case 'h': case 'H': return 7;
            case 'i': case 'I': return 8;
            case 'j': case 'J': return 9;
            case 'k': case 'K': return 10;
            case 'l': case 'L': return 11;
            case 'm': case 'M': return 12;
            case 'n': case 'N': return 13;
            case 'o': case 'O': return 14;
            case 'p': case 'P': return 15;
            case 'q': case 'Q': return 16;
            case 'r': case 'R': return 17;
            case 's': case 'S': return 18;
            case 't': case 'T': return 19;
            case 'u': case 'U': return 20;
            case 'v': case 'V': return 21;
            case 'w': case 'W': return 22;
            case 'x': case 'X': return 23;
            case 'y': case 'Y': return 24;
            case 'z': case 'Z': return 25;
            case ' ': case '.': case ',': case '?': case '!': return 26;

            default: return -1;
        }
    }

    private char intToAlpha(int num, boolean isUppercase){
        switch(num){
            case 0: return (isUppercase ? 'A' : 'a');
            case 1: return (isUppercase ? 'B' : 'b');
            case 2: return (isUppercase ? 'C' : 'c');
            case 3: return (isUppercase ? 'D' : 'd');
            case 4: return (isUppercase ? 'E' : 'e');
            case 5: return (isUppercase ? 'F' : 'f');
            case 6: return (isUppercase ? 'G' : 'g');
            case 7: return (isUppercase ? 'H' : 'h');
            case 8: return (isUppercase ? 'I' : 'i');
            case 9: return (isUppercase ? 'J' : 'j');
            case 10: return (isUppercase ? 'K' : 'k');
            case 11: return (isUppercase ? 'L' : 'l');
            case 12: return (isUppercase ? 'M' : 'm');
            case 13: return (isUppercase ? 'N' : 'n');
            case 14: return (isUppercase ? 'O' : 'o');
            case 15: return (isUppercase ? 'P' : 'p');
            case 16: return (isUppercase ? 'Q' : 'q');
            case 17: return (isUppercase ? 'R' : 'r');
            case 18: return (isUppercase ? 'S' : 's');
            case 19: return (isUppercase ? 'T' : 't');
            case 20: return (isUppercase ? 'U' : 'u');
            case 21: return (isUppercase ? 'V' : 'v');
            case 22: return (isUppercase ? 'W' : 'w');
            case 23: return (isUppercase ? 'X' : 'x');
            case 24: return (isUppercase ? 'Y' : 'y');
            case 25: return (isUppercase ? 'Z' : 'z');
            default: return ' ';
        }

    }

    public String nextWord(){
        String tempWord = "";
        boolean isWordComplete = false;

        char previousChar = ' ';

        while(!isWordComplete){

            float seed = (float)Math.random();

            if(previousChar == ' '){

                int index = 0;
                float accumulator = firstLetters[0];

                while(accumulator < seed){
                    index++;
                    accumulator += firstLetters[index];
                }

                tempWord += intToAlpha(index, true);
                previousChar = intToAlpha(index, false);
            }
            else{

                int index = 0;
                int prev = alphaToInt(previousChar);
                float accumulator = nextLetters[prev][0];

                while(accumulator < seed){
                    index++;
                    accumulator += nextLetters[prev][index];
                }

                if(index >= 26){
                    isWordComplete = true;
                }
                else{
                    tempWord += intToAlpha(index, false);
                    previousChar = intToAlpha(index, false);
                }


            }

        }

        return tempWord;
    }


}
