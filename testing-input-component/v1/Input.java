import java.io.*;
import java.util.*;
public class Input {

    protected List<String> tokens = new ArrayList<String>();

    public void read(InputStream input) throws IOException {
        StringBuilder builder = new StringBuilder();
	
        int data = input.read();
	
	while(data != -1){
            if(((char)data) != ','){
                builder.append((char) data);
            } else {
                this.tokens.add(builder.toString().trim());
		//System.out.println(""+builder.length());                
		builder.delete(0, builder.length());
		
            }

            data = input.read();
        }
    }
}
