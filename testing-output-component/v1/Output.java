import java.io.*;
import java.util.*;
public class Output {

    protected List<String> tokens = new ArrayList<String>();

    public void write(OutputStream output) throws IOException {
        for(int i=0; i<tokens.size(); i++){
            if(i>0){
                output.write(',');
            }
            output.write(tokens.get(i).getBytes());
       }
    }
}
