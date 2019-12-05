package encryptdecrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

interface EncDecSelectionAlgorithm
{
    String select(MessageArguments arguments);
}

class UnicodeAlgorithm implements EncDecSelectionAlgorithm
{
    @Override
    public String select(MessageArguments arguments)
    {
        if(arguments.getOperation().equals("dec"))
        {
            arguments.setKey(-arguments.getKey());
        }
        char[] outputMessage = new char[arguments.getInputMessage().length()];
        char ch = ' ';
        for (int i = 0; i < arguments.getInputMessage().length(); i++)
        {
            ch = arguments.getInputMessage().charAt(i);
            ch += arguments.getKey();
            outputMessage[i] = ch;
            ch = ' ';
        }
        String output = new String(outputMessage);
        return output;
    }
}

class ShiftAlgorithm implements EncDecSelectionAlgorithm
{
    @Override
    public String select(MessageArguments arguments)
    {
        char a = 'a';
        char z = 'z';
        char A = 'A';
        char Z = 'Z';
        int size = 26;

        char[] outputMessage = new char[arguments.getInputMessage().length()];
        char ch = ' ';

        for (int i = 0; i < arguments.getInputMessage().length(); i++)
        {
            ch = arguments.getInputMessage().charAt(i);

            if(ch >= a && ch <= z && arguments.getOperation().equals("enc"))
            {
                outputMessage[i] = (char)(((ch - a + arguments.getKey()) % size) + a);
            }
            else if(ch >= a && ch <= z && arguments.getOperation().equals("dec"))
            {
                outputMessage[i] = (char)((z - (z - ch + arguments.getKey()) % size));
            }
            else if(ch >= A && ch <= Z && arguments.getOperation().equals("enc"))
            {
                outputMessage[i] = (char)(((ch - A + arguments.getKey()) % size) + A);
            }
            else if(ch >= A && ch <= Z && arguments.getOperation().equals("dec"))
            {
                outputMessage[i] = (char)((Z - (Z - ch + arguments.getKey()) % size));
            }
            else
                {
                    outputMessage[i] = ch;
                }
           }
        String output = new String(outputMessage);
        return output;
    }
}

class SelectionAlgorithm
{
    private EncDecSelectionAlgorithm algorithm;

    public void setAlgorithm(EncDecSelectionAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public EncDecSelectionAlgorithm getAlgorithm() {
        return algorithm;
    }

    public String select(MessageArguments arguments)
    {
        return this.algorithm.select(arguments);
    }

    public void outputTheMessage (MessageArguments arguments, String message)
    {
        if(arguments.isOut())
        {
            File inputFile = new File(arguments.getPathToWrite());
            try(FileWriter writer = new FileWriter(arguments.getPathToWrite()))
            {
                writer.write(message);
            }
            catch (IOException e)
            {
                System.out.printf("An exception occurs %s", e.getMessage());
            }
        }
        else
        {
            System.out.print(message);
        }
    }
}

class MessageArguments
{
    private String operation = "enc";
    private String inputMessage = "";
    private int key = 0;
    private boolean isOut = false;
    private boolean isData = false;
    private boolean isIn = false;
    private boolean isDataAndIn = false;
    private String pathToWrite;
    private String algorithm = "shift";

    public String getAlgorithm() {
        return algorithm;
    }

    public String getOperation() {
        return operation;
    }

    public String getInputMessage() {
        return inputMessage;
    }

    public int getKey() {
        return key;
    }

    public boolean isOut() {
        return isOut;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getPathToWrite() {
        return pathToWrite;
    }

    public void setArgs(String[] args) {

        for(int i = 0; i < args.length; i++)
        {
            if (args[i].equals("-out"))
            {
                isOut = true;
                pathToWrite = args[i+1];
            }
            if (args[i].equals("-data"))
            {
                isData = true;
            }
            if (args[i].equals("-in"))
            {
                isIn = true;
            }
            if(args[i].equals("-mode"))
            {
                operation = args[i+1];
            }
            if(args[i].equals("-key"))
            {
                key = Integer.parseInt(args[i+1]);
            }
            if(args[i].equals("-alg"))
            {
                algorithm = args[i+1];
            }
        }

        if (isData && isIn)
        {
            isData = true;
            isIn = false;
        }

        for (int i = 0; i < args.length; i++)
        {
            if(args[i].equals("-data") && isData)
            {
                inputMessage = args[i+1];
            }
            //Reading from a file
            if(args[i].equals("-in") && isIn)
            {
                String pathToRead = args[i+1];
                File inputFile = new File(pathToRead);
                try(Scanner scanner = new Scanner(inputFile))
                {
                    String str = "";
                    while(scanner.hasNext())
                    {
                        str = scanner.next();
                        inputMessage = inputMessage + str + " ";
                    }
                    inputMessage = inputMessage.substring(0, inputMessage.length() - 1);
                }
                catch (FileNotFoundException e)
                {
                    System.out.println("No file found: " + pathToRead);
                }
            }
        }
   }
}

public class Main {
    public static void main(String[] args)
    {
        MessageArguments arguments = new MessageArguments();
        arguments.setArgs(args);
        SelectionAlgorithm algorithm = new SelectionAlgorithm();

        if(arguments.getAlgorithm().equals("unicode"))
        {
            algorithm.setAlgorithm(new UnicodeAlgorithm());
        }
        if(arguments.getAlgorithm().equals("shift"))
        {
            algorithm.setAlgorithm(new ShiftAlgorithm());
        }
        //algorithm.select(arguments);
        algorithm.outputTheMessage(arguments, algorithm.select(arguments));
    }
}
