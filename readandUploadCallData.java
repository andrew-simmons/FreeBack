import java.io.*;
import java.util.*;
import java.sql.*;

public class readandUploadCallData {
	public static void main(String[] args) throws IOException {
		Map<Integer, ArrayList<String>> callData = readData();
		uploadDataToDatabase(callData);
		printResults(callData);
	}

	public static void uploadDataToDatabase(Map<Integer, ArrayList<String>> callData) {
		try {
			String host = "jdbc:derby://localhost:1527/Employees";
			String uName = "autoBob";
			String uPass= "password";
			Connection conn = DriverManager.getConnection(host, uName, uPass);
			Statement stmt = conn.createStatement();
			for (int key : callData.keySet()) {
				String sql = "INSERT INTO Registration " +
						"VALUES (" + callData.get(key).get(0) + ", " + callData.get(key).get(1) + ", "
						+ callData.get(key).get(2);
				stmt.executeUpdate(sql);
			}
		} catch (SQLException err) {
			System.out.println(err.getMessage( ));
		}
	}

	public static Map<Integer, ArrayList<String>> readData() throws IOException {
		Map<Integer, ArrayList<String>> map = new TreeMap<Integer, ArrayList<String>>();
		// Change to database later
		String responseCallDataFile = "C:\\Users\\Andre\\Desktop\\HACKTheMachine\\HACKTheMachine\\Sample Data_support_calls_sample.txt";
		File f = new File(responseCallDataFile);
		FileReader rf = new FileReader(f);
		BufferedReader br = new BufferedReader(rf);
		//
		String st = "";
		while ((st = br.readLine()) != null) {
			int i = 0;
			for (i = 0; i < st.length(); i++) {
				if (st.charAt(i) == '\"' && st.charAt(i + 4) == ':') {
					break;
				}
			}
			st = st.substring(i);
			String[] text = st.split(",");
			System.out.println(Arrays.toString(text));

			for (String data : text) {
				if (data.contains(":")) {
					data = data.replaceAll("\"", "");
					while (!Character.isDigit(data.charAt(0))) {
						data = data.substring(1);
					}
					String[] datas = data.split(":");
					int responseCall = Integer.parseInt(datas[0]);
					String type = datas[1];

					System.out.println(responseCall + " : " + type);

					if (!map.containsKey(responseCall)) {
						map.put(responseCall, new ArrayList<String>());
					}
					map.get(responseCall).add(type);
				}
			}	
		}
		return map;
	}

	public static void printResults(Map<Integer, ArrayList<String>> callData) {
		for (int key : callData.keySet()) {
			System.out.println();
		}
	}
}
