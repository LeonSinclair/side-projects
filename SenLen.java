import java.util.*;
import java.io.*;

public class SenLen
{
    public static void main (String[] args)
    {
        String fileName;
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
                wordSum++;
            }
            if(sentences == 0) sentences++;
            System.out.println("Number of sentences: " + sentences);
            System.out.println("Number of words: " + wordSum);
            System.out.println("Mean words per sentence: " + wordSum/sentences);
            
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Error: Specified File Not Found.");
            System.exit(1);
        }
        

    }
}