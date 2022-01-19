import java.util.Scanner;
import java.util.Stack;
import java.io.File;

public class WordGenerator {

    private float[] lastLetters;
    private float[][] prevLetters;
    private float[] wordLength;
    private File book;

    public WordGenerator(File source) {

        this.book = source;
        //A-Z
        lastLetters = new float[26];
        //A-Z, " " where " " includes punctuation, and all non-letter characters
        prevLetters = new float[26][27];
        //length 1-19
        wordLength = new float[20];

        generateProbs();
    }

    //figures out the probabilities of each letter being followed by another
    private void generateProbs(){

        //frequency counters - not probability
        int[] t_lastLetters = new int[26];
        int[][] t_prevLetters = new int[26][27];
        int[] t_wordLength = new int[20];

        try{
            Scanner wordScanner = new Scanner(book);
            wordScanner.useDelimiter("[\\W]+");

            while(wordScanner.hasNext()){

                String tempWord = wordScanner.next();

                t_wordLength[tempWord.length() - 1] += 1;
                
                for(int i = tempWord.length() - 1; i >= 0; i--){
                    if(i == tempWord.length() - 1){
                        //[current]
                        int currentIndex = alphaToInt(tempWord.charAt(tempWord.length() - 1));
                        if(currentIndex == -1) continue;

                        t_lastLetters[currentIndex] += 1;
                    }
                    else{
                        //[current, next]
                        int currentIndex = alphaToInt(tempWord.charAt(i));
                        int nextIndex = alphaToInt(tempWord.charAt(i + 1));
                        if(nextIndex == -1 || currentIndex == -1) continue;

                        //[prev, current]
                        t_prevLetters[nextIndex][currentIndex] += 1;
                    }
                }

            }
            wordScanner.close();
        }
        catch(Exception e){
            e.printStackTrace();
            return;
        }

        int lastLetTotal = 0;
        for(int i = 0; i < t_lastLetters.length; i++){
            //taking count for the first letters
            lastLetTotal += t_lastLetters[i];

            int prevLetTotal = 0;
            //taking count for the subsequent letters
            for(int j = 0; j < t_prevLetters.length; j++){
                prevLetTotal += t_prevLetters[i][j];
            }

            //copying over probabilities for subsquent letters
            for(int j = 0; j < t_prevLetters.length; j++){
                prevLetters[i][j] = ((float)t_prevLetters[i][j] / prevLetTotal);
            }
        }

        //copying over probabilities for first letters
        for(int i = 0; i < lastLetters.length; i++){
            lastLetters[i] = ((float)t_lastLetters[i]) / lastLetTotal;
        }

        //convert wordlengths to probabilities
        int lengthTotal = 0;
        for(int i = 0; i < t_wordLength.length; i++){
            lengthTotal += t_wordLength[i];
        }
        for(int i = 0; i < wordLength.length; i++){
            wordLength[i] = ((float)t_wordLength[i] / lengthTotal);
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
        Stack<Character> wordStack = new Stack<Character>();
        char previousChar = ' ';

        //length selector
        float lengthSeed = (float)Math.random();
        float lengthAccumulator = wordLength[0];
        int lengthIndex = 0;
        while(lengthAccumulator < lengthSeed){
            lengthIndex++;
            lengthAccumulator += wordLength[lengthIndex];
        }

        for(int i = lengthIndex; i >= 0; i--){

            float seed = (float)Math.random();
            int index = 0;

            if(i == lengthIndex){

                float accumulator = lastLetters[0];

                while(accumulator < seed){
                    index++;
                    accumulator += lastLetters[index];
                }

                wordStack.push(intToAlpha(index, false));
                previousChar = intToAlpha(index, false);

            }
            else if(i == 0){

                float accumulator = prevLetters[alphaToInt(previousChar)][0];

                while(accumulator < seed){
                    index++;
                    accumulator += prevLetters[alphaToInt(previousChar)][index];
                }

                wordStack.push(intToAlpha(index, true));

            }
            else{
                
                float accumulator = prevLetters[alphaToInt(previousChar)][0];

                while(accumulator < seed){
                    index++;
                    accumulator += prevLetters[alphaToInt(previousChar)][index];
                }

                wordStack.push(intToAlpha(index, false));
                previousChar = intToAlpha(index, false);
            }
        }

        while(!wordStack.empty()){
            tempWord += wordStack.pop();
        }

        return tempWord;
    }
}
