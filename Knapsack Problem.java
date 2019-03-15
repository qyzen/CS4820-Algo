import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class Framework {
    int n;
    int values[];
    int weights[];
    int weight_limit;
    boolean picked[];
    void input(String input_name){
        File file = new File(input_name);
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));

            String text = reader.readLine();
            String parts[];
            parts=text.split(" ");
            n=Integer.parseInt(parts[0]);
            weight_limit=Integer.parseInt(parts[1]);
            values=new int[n];
            weights=new int[n];
            picked=new boolean[n];
            for (int i=0;i<n;i++){
                text=reader.readLine();
                parts=text.split(" ");
                values[i]=Integer.parseInt(parts[0]);
                weights[i]=Integer.parseInt(parts[1]);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //writing the output
    void output(String output_name)
    {
        try{
            PrintWriter writer = new PrintWriter(output_name, "UTF-8");

            int total_value=0;
            for (int i=0;i<n;i++)
              if (picked[i])
                total_value += values[i];
            writer.println(total_value);
            for (int i=0;i<n;i++)
              if (picked[i])
                writer.println("1");
              else 
                writer.println("0");

            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Framework(String []Args){
        input(Args[0]);

        // YOUR CODE STARTS HERE
        // YOUR CODE ENDS HERE

        output(Args[1]);
    }

    public static void main(String [] Args) //Strings in Args are the name of the input file followed by the name of the output file
    {
        new Framework(Args);
    }
}
