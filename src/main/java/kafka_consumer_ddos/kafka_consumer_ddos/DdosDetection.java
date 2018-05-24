package kafka_consumer_ddos.kafka_consumer_ddos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DdosDetection {

	private int threashold = 10; // Threshold of IP hit count

	/**
	 * @Function This function is to detect the DDos attacker Ips based on the
	 *           threshold set for the IP hit count.
	 * @param The
	 *            record containing the IP and number of hits
	 * @return Attacker Ips
	 */

	public boolean findDdosDetection(String record) {
		record = record.replace("(", "");
		record = record.replace(")", "");
		String[] recordArray = record.split(",");
		System.out.println("record string" + recordArray[0] + " "
				+ recordArray[1]);
		// when the ip is found in the master list or the ip hit acount is
		// greater than the threshold set
		if (findIpAddressValid(recordArray)) {
//
//			if (validateIpwithMasterList(recordArray[0])
//					|| Integer.parseInt(recordArray[1]) >= threashold)
				if ( Integer.parseInt(recordArray[1]) >= threashold)
			{
					writeResultToFile(recordArray);
					return true;
			}
			else
				return false;
		} else
		{
			System.out.println("Not an attacker");
			return false;
		}

	}

	/**
	 * @function validate the IP against a master list if any
	 * @param Ips
	 *            in the log file
	 * @return Boolean value of if IP is in the master list of attacker Ips
	 */

	public boolean validateIpwithMasterList(String ip) {
		// for now creating a list, ideally read it from a database or a master
		// list file.
		List<String> iplist = new ArrayList<String>();
		iplist.add("129.192.143.166");
		iplist.add("129.192.143.167");
		iplist.add("129.192.143.168");
		iplist.add("129.192.143.169");
		/*
		 * FileReader fileReader = new FileReader(fileName); BufferedReader
		 * bufferedReader = new BufferedReader(fileReader); while((line =
		 * bufferedReader.readLine()) != null) { System.out.println(line);
		 */

		if (iplist.contains(ip))
			return true;
		else
			return false;
	}

	/**
	 * @function Write the results to a results file
	 * @param Array
	 *            of attacker Ips and number of hits
	 * @result Results file with the list of attacker Ips and hit count
	 */

	public void writeResultToFile(String[] recordArray) {

		try {

			FileWriter fstream = new FileWriter("results.txt", true);
			BufferedWriter fbw = new BufferedWriter(fstream);
			fbw.write("IP:" + recordArray[0] + " Count:" + recordArray[1]
					+ "\n");
			fbw.newLine();
			fbw.close();
		} catch (IOException e) {
			System.out.println("Error opening the file result.txt.");
			System.exit(0);
		}

	}

	/**
	 * @function This is a data check to see that all records in the log file
	 *           has an IP.
	 * @param Array
	 *            containing Ips from the log file and hit count
	 * @return Boolean value of if an IP is valid or not
	 */
	private static boolean findIpAddressValid(String[] records) {

		String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
				+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
				+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
				+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
		Pattern PATTERN = Pattern.compile(IPADDRESS_PATTERN);
		Matcher matcherV4 = PATTERN.matcher(records[0]);
		if (matcherV4.find()) {
			return true;
		}

		else {
			System.out.println("0.0.0.0");
			return false;

		}
	}

}
