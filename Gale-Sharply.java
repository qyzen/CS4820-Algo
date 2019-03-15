// Usage:
// java Framework [inputfile] [outputfile]
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class Framework
/**
 * A class to handle input and output for processing stable matching problems.
 */
{
	int n; // number of proposers and respondents

	int ProposerPrefs[][]; // preference list of proposers (n*n)
	int RespondentPrefs[][]; // preference list of respondents (n*n)

	ArrayList<MatchedPair> MatchedPairsList; // your output should fill this arraylist which is empty at start

	public class MatchedPair
	/**
	 * A class storing an individual pair of one proposer and one respondent.
	 */
	{
		int proposer; // proposer's number
		int respondent; // respondent's number

		public MatchedPair(int Proposer, int Respondent)
		{
			proposer=Proposer;
			respondent=Respondent;
		}

		public MatchedPair()
		{
		}
	}


	void input(String input_name)
	/**
	 * Method to handle the processing of the input file saved at @input_name.
	 * The first line of this file contains the number of proposers/respondents n.
	 * The following n lines give ordered lists of preferences for proposers 1-n,
	 * followed by another n lines of the ordered lists of preferences for
	 * respondents 1-n. This code should not be modified for this assignment.
	 */
	{
		File file = new File(input_name);
		BufferedReader reader = null;

		try
		{
			reader = new BufferedReader(new FileReader(file));

			// Initialize n
			String text = reader.readLine();
			String [] parts = text.split(" ");
			n=Integer.parseInt(parts[0]);

			ProposerPrefs=new int[n][n];
			RespondentPrefs=new int[n][n];

			// Initialize proposer preferences
			for (int i=0;i<n;i++)
			{
				text=reader.readLine();
				String [] mList=text.split(" ");
				for (int j=0;j<n;j++)
				{
					ProposerPrefs[i][j]=Integer.parseInt(mList[j]);
				}
			}

			// Initialize respondent preferences
			for (int i=0;i<n;i++)
			{
				text=reader.readLine();
				String [] wList=text.split(" ");
				for(int j=0;j<n;j++)
				{
					RespondentPrefs[i][j]=Integer.parseInt(wList[j]);
				}
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
	/**
	 * Writes output to the file @output_name with n lines.
	 * Each line contains the index of one proposer followed by
	 * the respondent to whom they are matched, separated by a space.
	 */
	{
		try
		{
			PrintWriter writer = new PrintWriter(output_name, "UTF-8");

			for(int i=0;i<MatchedPairsList.size();i++)
			{
				writer.println(MatchedPairsList.get(i).proposer+" "+MatchedPairsList.get(i).respondent);
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
		// Load in the problem information and preferences
		input(Args[0]);

		MatchedPairsList=new ArrayList<MatchedPair>(); // you should put the final stable matching in this array list

		/* NOTE
		 * if you want to declare that proposer x and respondent y will get matched in the matching, you can
		 * write a code similar to what follows:
		 * MatchedPair pair=new MatchedPair(x,y);
		 * MatchedPairsList.add(pair);
		 */

		//YOUR CODE STARTS HERE
		/** To calculate all matches **/
		int matchedMen=0;// this integer is for counting how many man are matched
		int[] manNotMatched;// this array is to label the status of men, 0-not match, 1-matched
		manNotMatched=new int[n];
		for (int p=0;p<n;p++)
			manNotMatched[p]=0;
		int[] womenPair;// this array is to record which woman matched to which man
		womenPair=new int[n];
		for (int q=0;q<n;q++)
			womenPair[q]=(n+1);


		while (matchedMen < n)//go into the loop when not every man are matched
		{
			/** go through men list, if find a man who is not matched, then break the search**/ 
			int i=0;
			for (; i< n; i++)
				if (manNotMatched[i]==0)//i is the man's number who is unmatched
					break;

			/** match the unmatched man with a woman, if all men are match, will not go into this loop **/
			for (int j= 0; j< n && manNotMatched[i]==0 ; j++)
			{
				int a;
				a = ProposerPrefs[i][j];
				/** a is the woman's number that the man prefers the most right now; 
				 * womanPair[] is a string which store the current man number who has matched with the woman **/
				/**match the woman with the man directly if the woman is unmatched**/
				if (womenPair[a]==(n+1))
				{
					womenPair[a] = i;
					manNotMatched[i] = 1;
					matchedMen++;

				}
				/** if the woman already has a pair, check the woman¡¯s preference **/
				else
				{
					int k= womenPair[a]; /** k is the one who matched with the woman right now**/
					/**check if the woman need to change the current pair**/
					int rank1=(n+1);
					int rank2=(n+1);
					for (int r = 0; r< n; r++)
					{
						if (RespondentPrefs[a][r]==k)
						{
							rank1=r;
						}
						if (RespondentPrefs[a][r]==i)
						{
							rank2=r;
						}
						if ( rank1>rank2)
						{
							/**change the current pair **/
							manNotMatched[k]=0;
							womenPair[a] = i;
							manNotMatched[i] = 1;
						}
					}
				}            
			}	
		}
		for (int u=0; u<n; u++)
		{
			MatchedPair pair=new MatchedPair(womenPair[u],u);
			MatchedPairsList.add(pair);
		}


		//YOUR CODE ENDS HERE

		// Output the stable matching in MatchedPairsList
		output(Args[1]);
	}

	public static void main(String [] Args) // Strings in Args are the name of the input file followed by the name of the output file
	{
		new Framework(Args);
	}
}