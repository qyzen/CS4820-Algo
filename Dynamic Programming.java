import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;


public class Framework {
    int n; //number of cells. Cells are labeled from 1 to n
    int rewards[]; // the reward in each cell
    int total_reward[]; // total_reward[i] equals to the maximum score when Alice is starting using the subproblem of shifts [1, i] only
    boolean picked[]; //picked[i] means whether Alice has picked i or not
    
    //reading the input
    void input(String input_name){
        File file = new File(input_name);
        BufferedReader reader = null;
                
        try {
            reader = new BufferedReader(new FileReader(file));
            
            String text = reader.readLine();
            n = Integer.parseInt(text);

            rewards = new int[n + 1];
            total_reward = new int[n + 1];
            picked = new boolean[n + 1];

            text = reader.readLine();
            String parts[] = text.split(" ");

            for (int i = 1; i <= n; i++)
                rewards[i] = Integer.parseInt(parts[i - 1]);

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
            
            writer.println(total_reward[n]);
            for (int i = 1; i <= n; i++)
                if (picked[i]) writer.print("1 ");
                else writer.print("0 ");
            writer.println();

            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public Framework(String []Args){
        input(Args[0]);

        //YOUR CODE GOES HERE
        
        
        boolean[] list=new boolean[n+1];
        list[1]=true;
        
        if (rewards[1]>=rewards[2])
        {
        	total_reward[1]=rewards[1];
        	total_reward[2]=rewards[1];
        	
        	list[2]=false;
        	
        	
        }
        else
        {
        	total_reward[1]=rewards[1];
        	total_reward[2]=rewards[2];
        	
        	list[2]=true;
        }
        for(int i=3;i<=n;i++)
        {
        	if((rewards[i]+total_reward[i-2])>=total_reward[i-1])
        	{
        		total_reward[i]=rewards[i]+total_reward[i-2];
        		list[i]=true;
        	}
        	else
        	{
        		total_reward[i]=total_reward[i-1];
        		
        		list[i]=false;
        	}
        }
        picked[n]=list[n];
        int j=n;
        while (j>=0)
        {
        	if(list[j])
        	{
        		picked[j]=true;
        		j=j-2;
        	}
        	else
        	{
        		picked[j]=false;
        		j=j-1;
        	}	
        }
        //END OF YOUR CODE
        
        output(Args[1]);
    }
    
    public static void main(String [] Args) //Strings in Args are the name of the input file followed by the name of the output file
    {
        new Framework(Args);
    }
}
