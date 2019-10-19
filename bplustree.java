import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.util.ArrayList;
import java.io.FileReader;
import java.util.List;
import java.io.IOException;
import java.util.Collections;


public class bplustree {
	private static final String OUTPUT_FILENAME = "output";

	/** The Constant OUTPUT_FILEEXTENSION. */
	private static final String OUTPUT_FILEEXTENSION = ".txt";
	
	public static void main(String[] args) throws FileNotFoundException, IOException{
		
		String line=null;
		String[] arg;
		BufferedReader br=new BufferedReader(new FileReader(args[0]));
		BTree bplus=new BTree();
		BufferedWriter bw = openNewFile();
		//BufferedWriter bw=new BufferedWriter(new FileWriter("output_file.txt",true));
		String initializeString = br.readLine().trim();
		String[] initializeStrings = initializeString.split("\\(");
		initializeString = initializeStrings[1].replace(")","");
		Integer initialize = Integer.parseInt(initializeString);
		List<Pair<Double,List<Double>>> map;

		bplus.initialize(initialize);
		List<Double> values;// string before
		while((line=br.readLine())!=null){		
			if (line.charAt(0)=='I'){
				arg=line.split("\\(");
				
				String[] s=arg[1].split(",");
				String keyString = s[0];
				String valueString = s[1].replace(")", "");
				Double key = Double.parseDouble(keyString);
				Double value = Double.parseDouble(valueString);
				
				bplus.insert(key, value);
				continue;
			}else if (line.charAt(0)=='S'){

				if(line.indexOf(",")==-1){
					arg=line.split("\\(");
					
					String keyString = arg[1].replace(")", "");
					Double key = Double.parseDouble(keyString);
					
					values=bplus.search(key);		
					formatSearchOutput(bw,values);
					continue;
				}
				else{
					arg=line.split("\\(");
					String[] s=arg[1].split(",");
					map=bplus.search(Double.parseDouble(s[0]),Double.parseDouble(s[1].substring(0, s[1].length()-1)));
					formatRangeSearchOutput(bw,map);
					continue;
				}
			}
			else if(line.charAt(0) == 'D') {
				arg=line.split("\\(");
				String keyString = arg[1].replace(")", "");
				Double key = Double.parseDouble(keyString);

				bplus.remove(key);
			}
				
		}
	
		br.close();
		bw.close();
}

	private static BufferedWriter openNewFile() throws IOException {
	// Creating a new file to write output to (output_file.txt)
	File file = new File(OUTPUT_FILENAME + OUTPUT_FILEEXTENSION);
	BufferedWriter bw = new BufferedWriter(new FileWriter(file));
	return bw;
}	
	
	public static void formatSearchOutput(BufferedWriter bw, List<Double> values){
		try {
		if(values ==null || values.size() == 0){
			bw.write("Null");
			bw.newLine();
			return;
		}
		int i;
		for( i=0;i<values.size();i++){
			
				String value = values.get(i).toString();
				bw.write(value);
				if(i+1<values.size())
					bw.write(", ");
		}
		bw.newLine();
		} catch (IOException e) {
				
				e.printStackTrace();
			}
}

	public static void formatRangeSearchOutput(BufferedWriter bw, List<Pair<Double,List<Double>>> map){
		try{
		for(int i=0;i<map.size();i++){
			for (int j=0;j<map.get(i).getValue().size();j++){
				bw.write((map.get(i).getValue().get(j)).toString());
				if(j+1<(map.get(i).getValue()).size() && (map.get(i).getValue().get(j+1))!=null)
					bw.write(", ");
			}
			if(i+1<map.size() &&map.get(i+1)!=null)
				bw.write(", ");
		}
		bw.newLine();
		}
		catch(Exception e){
			e.getStackTrace();
		}
	}
	
}

