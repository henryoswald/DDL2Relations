import java.io.*;
import java.util.*;
import java.util.regex.*;


public class Converter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			
			//use a buffer for perforamnce convert later
			StringBuffer sqlBuf = new StringBuffer();
			 
			Scanner inputFile = new Scanner(new File(args[0]));
			String sqlString ="";
			//put entire SQL into string buffer
			while(inputFile.hasNextLine()) {
				String next = inputFile.nextLine();
				
				if(!next.contains("-") && !next.isEmpty()) {

					
					//finds the table names
					Pattern tablePat = Pattern.compile("CREATE TABLE.*?\"(.*)?\"");
					Matcher tableMatcher = tablePat.matcher(next);
					while (tableMatcher.find()) {
						sqlBuf.append("\n\n"+tableMatcher.group(1)+" (");
						next=""; //this isn't good code but hack will do
					}
					
					
					//remove any non foreign key constriants
					if(next.contains("CONSTRAINT") && !next.contains("FOREIGN KEY ")){
						next="";
					}
					
					//finds the forgien key
					Pattern fkPat = Pattern.compile(".*?CONSTRAINT.*?[(](.*?)[)].*");
					Matcher fkMatcher = fkPat.matcher(next);
					while (fkMatcher.find()) {
						String foreignKeys = fkMatcher.group(1);
						foreignKeys= foreignKeys.replaceAll("\"", "");
						sqlBuf.append("Foreign Key "+foreignKeys+" ");
						next=""; //this isn't good code but hack will do
					}
					
					//finds the forgien key reference
					Pattern fkRefPat = Pattern.compile(".*?REFERENCES.*?\"(.*?)\".*");
					Matcher fkRefMatcher = fkRefPat.matcher(next);
					while (fkRefMatcher.find()) {

						sqlBuf.append("References "+fkRefMatcher.group(1)+"\n");
						next=""; //this isn't good code but hack will do
					}
					
					//finds the primary key
					Pattern primeKeyPat = Pattern.compile(".*?PRIMARY KEY\\s+?[(](.*)[)].*?");
					Matcher primeKeyMatcher = primeKeyPat.matcher(next);
					while (primeKeyMatcher.find()) {
						String primaryKeys = primeKeyMatcher.group(1);
						primaryKeys= primaryKeys.replaceAll("\"", "");
						sqlBuf.append(") \nPrimary Key "+primaryKeys+"\n");
						next=""; //this isn't good code but hack will do
					}
					

					//finds the fields
					Pattern fieldPat = Pattern.compile("\\s+\"(.*?)\"");
					Matcher fieldMatcher = fieldPat.matcher(next);
					while (fieldMatcher.find()) {
						sqlBuf.append(fieldMatcher.group(1)+", ");
						next=""; //this isn't good code but hack will do
					}
				}
			}
			
			sqlString = sqlBuf.toString();
			sqlString = sqlString.replace(", )", ")");
			
			
			System.out.println(sqlString);
			
			BufferedWriter writer = new BufferedWriter(new FileWriter("relations.txt"));

	        writer.append(sqlString);
	        writer.flush();
			
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
