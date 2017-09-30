import java.util.*;
import java.io.*;

public class SenLen
{
    public static void main (String[] args)
    {
        String fileName;
        HashMap<String, Integer> occurenceMap = new HashMap<String, Integer>();
        int sentences = 0;
        int wordSum = 0;
        if(args.length > 0) fileName = args[0];
        else
        {
            System.out.println("Enter a filename please.");
            Scanner input = new Scanner(System.in);
            fileName = input.next();
        }
        try
        {
            Scanner inputFile = new Scanner(new FileReader(fileName));
            while(inputFile.hasNext())
            {
                String word = inputFile.next();
                if(word.contains(".") || word.contains("!") || word.contains("?"))
                {
                    sentences++;
                }
                word = word.toLowerCase();
                word = word.replaceAll("[^a-zA-Z]", "");
                int count;
                if(occurenceMap.containsKey(word))
                {
                    count = occurenceMap.get(word);
                    count++;
                }
                else
                {
                    count = 1;
                }
                occurenceMap.put(word, count);
                wordSum++;
            }
            //prevents divide by zero error
            //0 sentences doesn't really make sense so we make so there are at least 1
            if(sentences == 0) sentences++;
            System.out.println("Number of sentences: " + sentences);
            System.out.println("Number of words: " + wordSum);
            System.out.println("Mean words per sentence: " + wordSum/sentences);
            
            int max = Integer.MIN_VALUE;
            String key = null;
            Iterator it = occurenceMap.entrySet().iterator();
            while (it.hasNext()) 
            {
                Map.Entry pair = (Map.Entry)it.next();
                int value = (Integer)pair.getValue();
                if(value > max)
                {
                    max = value;
                    key = (String) pair.getKey();
                }
                it.remove(); // avoids a ConcurrentModificationException
            }
            System.out.println("The most common word in the text is \"" + key + "\" with " + max + " occurences.");
            
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Error: Specified File Not Found.");
            System.exit(1);
        }
        

    }
}