import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class Framework
{
	int n; // number of candidates
	int k; // number of recruiters

	// provided data structures (already filled in)
	ArrayList<ArrayList<Integer>> neighbors;
	int[] recruiterCapacities;
	int[] preliminaryAssignment;

	// provided data structures (you need to fill these in)
	boolean existsValidAssignment;
	int[] validAssignment;
	int[] bottleneckRecruiters;

	// reading the input
	void input(String input_name)
	{
		File file = new File(input_name);
		BufferedReader reader = null;

		try
		{
			reader = new BufferedReader(new FileReader(file));

			String text = reader.readLine();
			String[] parts = text.split(" ");

			n = Integer.parseInt(parts[0]);
			k = Integer.parseInt(parts[1]);
			neighbors = new ArrayList<ArrayList<Integer>>(n+k);
			recruiterCapacities = new int[n+k];
			preliminaryAssignment = new int[n];

			for (int j = 0; j < n+k; j++) {
				neighbors.add(new ArrayList<Integer>());
			}
			for (int i = 0; i < n; i++) {
				text = reader.readLine();
				parts = text.split(" ");
				int numRecruiters = Integer.parseInt(parts[0]);
				for (int j = 0; j < numRecruiters; j++) {
					int recruiter = Integer.parseInt(parts[j+1]);
					neighbors.get(i).add(recruiter);
					neighbors.get(recruiter).add(i);
				}
			}

			text = reader.readLine();
			parts = text.split(" ");
			for (int j = 0; j < k; j++) {
				recruiterCapacities[n+j] = Integer.parseInt(parts[j]);
			}

			text = reader.readLine();
			parts = text.split(" ");
			for (int i = 0; i < n-1; i++) {
				preliminaryAssignment[i] = Integer.parseInt(parts[i]);
			}

			reader.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// writing the output
	void output(String output_name)
	{
		try
		{
			PrintWriter writer = new PrintWriter(output_name,
				"UTF-8");

			if (existsValidAssignment) {
				writer.println("Yes");
				for (int i = 0; i < n-1; i++) {
					writer.print(validAssignment[i] + " ");
				}
				writer.println(validAssignment[n-1]);
			} else {
				writer.println("No");
				for (int j = 0; j < n+k-1; j++) {
					writer.print(bottleneckRecruiters[j] + " ");
				}
				writer.println(bottleneckRecruiters[n+k-1]);
			}

			writer.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public Framework(String []Args)
	{
		input(Args[0]);

		// Fill these in as instructed in the problem statement.
		existsValidAssignment = false;
		validAssignment = new int[n];
		bottleneckRecruiters = new int[n+k];

		//YOUR CODE STARTS HERE
		int[] numbermatched;
		int a=0;
		int c=0;
		numbermatched=new int[n+k-1];
		int size=neighbors.get(n-1).size();
		for (a=0; a<n+k; a++)
			numbermatched[a]=0;
		// check how much space has already been use 
		for (a=0; a<n;a++)
			numbermatched[preliminaryAssignment[a]]=numbermatched[preliminaryAssignment[a]]+1;
		//if have vacancy, add.
		for (a=0; a<size; a++)
		{
			c=neighbors.get(n-1).get(a);
			if (numbermatched[c]<=recruiterCapacities[c])
					{
				    existsValidAssignment = true;
				    preliminaryAssignment[n-1]=c;
					}
		}
		//if all available are full

		boolean[] flag;
		flag= new boolean[n+k-1];
		for (a=0; a<n+k; a++)
			flag[a]=false;
		//compute total route m
		int m=0;
		for (a=0; a<n; a++)
			m=m+neighbors.get(a).size();
		for(int i=0; i<m; i++)
		{
			
		}
		//compute total capacity for recruiters
		int total=0;
		for(a=0; a<n+k; a++)
			total=total+recruiterCapacities[a];
		if (n>total)
			existsValidAssignment = false;
		//calculate bottleneckRecruiters
		for (a=0; a<n+k;a++)
			bottleneckRecruiters[a]=0;
		for(a=0;a<neighbors.get(n-1).size();a++)
			bottleneckRecruiters[neighbors.get(n-1).get(a)]=1;
		//populate valid assignment
		for (a=0; a<n; a++)
		{
			validAssignment[a]=preliminaryAssignment[a];
		}	
		//YOUR CODE ENDS HERE

		output(Args[1]);
	}

	// Strings in Args are the name of the input file followed by
	// the name of the output file
	public static void main(String [] Args) 
	{
		new Framework(Args);
	}
}
