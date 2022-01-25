import java.util.Scanner;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.io.File;

public class WordGenerator {

    private HashSet<String> syllables;
    private HashMap<Integer, Integer> wordLength;

    private File book;

    public WordGenerator(File source) {

        this.book = source;

        syllables = new HashSet<String>();
        wordLength = new HashMap<Integer, Integer>();

        generateSyllables();
    }

    private void generateSyllables(){

        try{
            Scanner wordScanner = new Scanner(book);
            wordScanner.useDelimiter("[\\W\\d]+");

            while(wordScanner.hasNext()){

                String tempWord = wordScanner.next();
                int wordSize = tempWord.length();

                int syllablesInWord = 0;

                boolean inVowelString = false;
                String currentSyll = "";
                for(int i = 0; i < wordSize; i++){

                    currentSyll += tempWord.charAt(i);

                    //denotes the location of syllables
                    if(i == wordSize - 1){
                        break;
                    }
                    else if(isVowel(tempWord.charAt(i))){
                        
                        inVowelString = true;

                        //testing if there are any more vowels left (if not, the rest of the word is counted as the rest of the syllable)
                        String lettersLeft = tempWord.substring(i+1);
                        if(lettersLeft.matches("[^aeiouy]+")){
                            currentSyll += tempWord.substring(i);
                            break;
                        }

                    }
                    else if(inVowelString){
                        //adds the syllable to the set
                        syllablesInWord++;
                        syllables.add(currentSyll.toLowerCase());
                        currentSyll = "";
                        inVowelString = false;
                    }

                }
                //adds whatever the current syllable was upon `break` or the end of the word (vowel)
                if(!currentSyll.isEmpty()){
                    syllablesInWord++;
                    syllables.add(currentSyll.toLowerCase());
                }


                //adds the word length to the hash map
                if(wordLength.containsKey(syllablesInWord)){
                    int old = wordLength.get(syllablesInWord);
                    wordLength.replace(syllablesInWord, old, old + 1);
                }
                else{
                    wordLength.put(syllablesInWord, 1);
                }

            }
            wordScanner.close();

        }
        catch(Exception e){
            e.printStackTrace();
            return;
        }

    }

    private boolean isVowel(char let){
        return let == 'a' || let == 'e' || let == 'i' || let == 'o' || let == 'u' || let == 'y';
    }

    public String nextWord(){

        String tempWord = "";

        int numSylls = syllables.size();

        int tempSyllProb = 0;
        for(Map.Entry<Integer, Integer> set : wordLength.entrySet()){
            tempSyllProb += set.getValue();
        }

        int size_seed = (int)(tempSyllProb * Math.random());
        int size = 0;

        for(Map.Entry<Integer, Integer> set : wordLength.entrySet()){
            size_seed -= set.getValue();
            if(size_seed <= 0){
                size = set.getKey();
                break;
            }
        }

        for(int i = 0; i < size; i++){
            int seed = (int)(Math.random() * numSylls);
            Iterator<String> i_syllables = syllables.iterator();

            while(seed > 0){
                i_syllables.next();
                seed--;
            }

            tempWord += i_syllables.next();
        }

        //fixing doubled vowels that should not be happening in english
        tempWord = tempWord.replaceAll("[a]+", "a");
        tempWord = tempWord.replaceAll("[i]+", "i");
        tempWord = tempWord.replaceAll("[u]+", "u");
        tempWord = tempWord.replaceAll("[y]+", "y");
            
        return tempWord;
    }
    
}
