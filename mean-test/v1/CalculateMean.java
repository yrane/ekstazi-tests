import java.lang.*;
public class CalculateMean
{
public float mean(int scores[])
{
	int sum = 0;
	int mean = 0;
     for (int k = 0; k < scores.length; k++) 
     {
         sum += scores[k];
     }
     mean = sum/scores.length;
     System.out.println("\nMean score: "+ mean);
	return mean;
}
}
