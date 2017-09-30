import java.util.*;
import java.io.*;

public class SenLen
{
    public static void main (String[] args)
    {
        String fileName;
        HashMap<String, Integer> occurenceMap = new HashMap<String, Integer>();
        HashMap<Integer, Integer> sentencesMap = new HashMap<Integer, Integer>();
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
            int currentSentenceLength = 0;
            while(inputFile.hasNext())
            {
                String word = inputFile.next();
                if(word.contains(".") || word.contains("!") || word.contains("?"))
                {
                    sentences++;
                    addtoMapIntInt(sentencesMap, currentSentenceLength);
                    currentSentenceLength = 0;
                }
                word = word.toLowerCase();
                word = word.replaceAll("[^a-zA-Z]", "");
                addtoMapStrInt(occurenceMap, word);
                wordSum++;
                currentSentenceLength++;
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
                int value = (int) pair.getValue();
                if(value > max)
                {
                    max = value;
                    key = (String) pair.getKey();
                }
                it.remove(); // avoids a ConcurrentModificationException
            }
            System.out.println("The most common word in the text is \"" + key + "\" with " + max + " occurences.");
            System.out.println("The modal sentence length is: " + highestMapKeyIntInt(sentencesMap));
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Error: Specified File Not Found.");
            System.exit(1);
        }

        
        

    }
    public static void addtoMapIntInt(Map map, int key )
    {
        int count;
        if(map.containsKey(key))
        {
            count = (Integer) map.get(key);
            count++;
        }
        else
        {
            count = 1;
        }
        map.put(key, count);
    }
    
    public static void addtoMapStrInt(Map map, String key )
    {
        int count;
        if(map.containsKey(key))
        {
            count = (Integer) map.get(key);
            count++;
        }
        else
        {
            count = 1;
        }
        map.put(key, count);
    }

    public static int highestMapKeyIntInt(Map map)
    {
        int max = Integer.MIN_VALUE;
        int key = 0;
        Iterator it = map.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry pair = (Map.Entry)it.next();
            int value = (int) pair.getValue();
            if(value > max)
            {
                max = value;
                key = (int) pair.getKey();
            }
            it.remove(); // avoids a ConcurrentModificationException
        }
        return key;
    }
}