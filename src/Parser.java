import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class Parser {
	public String ReadFile(String path){
	    File file = new File(path);
	    BufferedReader reader = null;
	    String laststr = "";
	    try {
	     //System.out.println("Start reading line by line：");
	     reader = new BufferedReader(new FileReader(file));
	     String tempString = null;
	     int line = 1;
	     //until end of file
	     while ((tempString = reader.readLine()) != null) {
	      //display line number
	      System.out.println("line " + line + ": " + tempString);
	      laststr = laststr + tempString;
	      line++ ;
	     }
	     reader.close();
	    } catch (IOException e) {
	     e.printStackTrace();
	    } finally {
	     if (reader != null) {
	      try {
	       reader.close();
	      } catch (IOException e1) {
	      }
	     }
	    }
	    return laststr;
	}
	
	public void writeFile(String filePath, String sets) throws IOException {
	    FileWriter fw = new FileWriter(filePath);
	    PrintWriter out = new PrintWriter(fw);
	    out.write(sets);
	    out.println();
	    fw.close();
	    out.close();
	   }
	
	public List<Order> findOrders(String path) {
		String text = ReadFile(path);
		JSONObject jsonObj = JSONObject.fromObject(text);
		JSONArray array = jsonObj.getJSONArray("data");
		List<Order> list = new ArrayList<Order>();
		for (int i=0; i<array.size(); i++){
			Order obj = (Order)JSONObject.toBean((JSONArray.fromObject(array.toString()).getJSONObject(i)), Order.class);
			list.add(obj);
		}
		return list;
	}
}
