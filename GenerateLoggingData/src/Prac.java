import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Prac
{
	 public static void main(String[] args)
	 {
	        String csvFile = "sample_sensor.txt";
	        BufferedReader br = null;
	        String line = "";
	        String cvsSplitBy = ",";
	        File file = new File(csvFile);
	        System.out.println(file.getAbsolutePath());
	        ArrayList<String> list = new ArrayList();
	        try
	        {
	            br = new BufferedReader(new FileReader(file));
	            while ((line = br.readLine()) != null) {

	                System.out.println(line);
	                list.add(line);
	            }
	        }
	        catch (FileNotFoundException e)
	        {
	            e.printStackTrace();
	        } catch (IOException e) 
	        {
	            e.printStackTrace();
	        } finally {
	            if (br != null) {
	                try {
	                    br.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	        
	        try{
	            PrintWriter writer = new PrintWriter("sample_sensor.txt", "UTF-8");
	            int k=0;
	            while(k<10000)
	            {
	            	
	            	for(int i=0;i<list.size();i++)
	            	{
	            		k++;
	            		writer.write(list.get(i));
	            		writer.write("\n");
	            	}
	            }
	            
	            writer.close();
	        } catch (IOException e) {
	           // do something
	        }
	        
	        
	        
	  }
}
