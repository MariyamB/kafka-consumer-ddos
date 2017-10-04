package kafka_consumer_ddos.kafka_consumer_ddos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DdosDetection {
	
	private int threashold=10;
	private static final String ipv4Pattern = "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])";
	private static final String ipv6Pattern = "([0-9a-f]{1,4}:){7}([0-9a-f]){1,4}";
	
	
	public void findDdosDetection(String record)
	{
		record = record.replace("(", "");
		 record = record.replace(")", "");		
		String [] recordArray = record.split(",");
		System.out.println("record string"+recordArray[0]+" "+recordArray[1]);
		// when the ip is found in the master list or the ip hit acount is greater than the threshold set
		if(findIpAddressValid(recordArray))
		{
		
		if (validateIpwithMasterList(recordArray[0])|| Integer.parseInt(recordArray[1])>=threashold)		
			writeResultToFile(recordArray);		
		}
		else
			System.out.println("Not an attacker");
		
	}
	
	/*
	 * validate the ip against a master list
	 */
	private boolean validateIpwithMasterList(String ip)
	{
		// for now creating a list, ideally read it from a database or a master list file. 
		List<String> iplist = new ArrayList<String>();
		iplist.add("129.192.143.166");
		iplist.add("129.192.143.167");
		iplist.add("129.192.143.168");
		iplist.add("129.192.143.169");
		
		if (iplist.contains(ip))	
			return true;
		else
			return false;
	}
	
	/*
	 * 
	 * Write the results to a file
	 */
	
	
	public void writeResultToFile(String [] recordArray) {

        PrintWriter outputStream = null;
       try{
           
           
           FileWriter fstream = new FileWriter("results.txt",true);
           BufferedWriter fbw = new BufferedWriter(fstream);
           fbw.write("IP:"+ recordArray[0]+" Count:"+recordArray[1]+"\n");
           fbw.newLine();
           fbw.close();
       }
       catch (IOException e){
           System.out.println("Error opening the file result.txt.");
           System.exit(0);
       } 
       

    }
	/*
	 * i think this check is not needed, as it is safe to assume that apache log has ip address and therefor kafka queue
	 */
	private  boolean findIpAddressValid(String[] args)
	{
		String record ="(129.192.143.166,2)";
		 
		Pattern patternV4 = Pattern.compile(ipv4Pattern);
		Pattern patternV6 = Pattern.compile(ipv6Pattern);
		Matcher matcherV4 = patternV4.matcher(record);
		Matcher matcherV6 = patternV4.matcher(record);
		if (matcherV4.find() ) {
		   return true;
		} else if(matcherV6.find() ) {
			return true;		
		}
		else
		{
			System.out.println( "0.0.0.0"); 
			return false;
			
		}
	}
	
	

}
