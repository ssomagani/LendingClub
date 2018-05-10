import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.voltdb.VoltTable;
import org.voltdb.VoltType;
import org.voltdb.client.Client;
import org.voltdb.client.ClientConfig;
import org.voltdb.client.ClientFactory;
import org.voltdb.client.ClientResponse;
import org.voltdb.client.NullCallback;

public class LoanClient {

	private static Client client;
	private static final String EMPTY_STRING = "";

	public static void main(String[] args) throws IOException {

		ClientConfig config = new ClientConfig();
//		config.setMaxTransactionsPerSecond(100);
		client = ClientFactory.createClient(config);
		client.createConnection("localhost");

		String filename = args[0];
		File csvFile = new File(filename);
		
		BufferedReader reader = new BufferedReader(new FileReader(csvFile));
		CSVParser parser = CSVParser.parse(reader, CSVFormat.RFC4180);
		Iterator<CSVRecord> iter = parser.iterator();
		
		int iterCount = 1;
		while(iter.hasNext() && iterCount != 0) {
			insertRecord(iter.next());
			iterCount--;
		}
//		if(iter.hasNext())
//			insertRecord(iter.next());
		parser.close();
	}
	
	private static void insertRecord(CSVRecord record) {
		int id = Integer.parseInt(record.get(1));
		String[] procArgs = new String[151];
		for(int i=0,j=1; j<152; i++,j++) {
			String value = record.get(j);
			
			if(i == 1 || i == 24 || i == 32 || i == 38 || i==55 || i== 60 || i == 67 
					|| i == 69 || i == 70 || i == 73 || i == 80 || 
					i == 81 || i == 84 || i == 89 || i== 93 || i == 103 || i == 108) {
				if(value.equals(EMPTY_STRING))
					procArgs[i] = "0";
			} else if(i == 27 || i == 48 || i == 51) {
				procArgs[i] = parseDate(value);
			} else if(i == 36) {
				procArgs[i] = parsePercent(value);
			} else {
				procArgs[i] = value;
			}
		}
		callProc(id, procArgs);
	}
	
	private static void callProc(int id, String[] procArgs) {
		try {
//			client.callProcedure(new NullCallback(), "NewLoan_ML_SP", id, procArgs);
//			ClientResponse response = client.callProcedure("NewLoan_ML", id, procArgs);
			Object[] args = (Object[]) procArgs;
			ClientResponse response = client.callProcedure("NewLoan", args);
			
			VoltTable result = response.getResults()[0];
			if(result.advanceRow())
			System.out.println(result.get(0, VoltType.BIGINT));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static SimpleDateFormat sdf = new SimpleDateFormat("MMM-yyyy");
	
	private static String parseDate(String str) {
		try {
			double epochTimestamp = (double) sdf.parse(str).getTime();
			return new BigDecimal(epochTimestamp).toPlainString();
		} catch (ParseException e) {
//			e.printStackTrace();
			return "0";
		}
	}
	
	private static String parsePercent(String str) {
		str = str.trim();
		if(str.contains("%")) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}
}
