public class Calculation {  
  
    public static int findMax(int arr[]){  
        int maxim=arr[0];  
        for(int i=1;i<arr.length;i++){  
            if(maxim<arr[i])  
                maxim=arr[i];  
        }  
        return maxim;  
    }  
}  

