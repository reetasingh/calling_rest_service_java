
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import service.InputData;



public class RequestG
{


	private final String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) throws Exception 
	{

		RequestG http = new RequestG();

		//System.out.println("Testing 1 - Send Http GET request");
		//http.sendGet();


		//System.out.println("\nTesting 2 - Send Http POST request");
		//http.sendPost();
		http.write_multiple();
		//http.delete("4");
		http.flush("10");

	}

	// HTTP GET request
	private void sendGet() throws Exception {
		System.out.println("In get");
		String url = "http://www.google.com/search?q=mkyong";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());

	}

	// HTTP POST request
	private void sendPost() throws Exception
	{

		String url = "http://localhost:8080/generic_logging_service/rest/logging/write";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		//con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		con.addRequestProperty("Accept", "application/json");


		// Send post request
		con.setDoOutput(true);
		con.setDoInput(true);


		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		//wr.writeBytes(urlParameters);
		String str = "{ \"payload\": \"<infra><name>5100_1</name><type>corridor</type><SEMANTIC_ENTITY>1</SEMANTIC_ENTITY><REGION_ID>1</REGION_ID></infra>\",\"logtype\" :\"EVENT\",\"appid\": \"4\"}";
		str.trim();
		System.out.println(str);

		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) parser.parse(str);
		System.out.println(jsonObject.toJSONString());
		System.out.println(jsonObject.get("payload"));
		System.out.println(jsonObject.get("logtype"));
		System.out.println(jsonObject.get("appid"));


		wr.write(jsonObject.toJSONString().getBytes());

		wr.flush();
		wr.close();


		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());

	}


	public void write() throws ParseException, IOException
	{
		System.out.println("In write");
		String csvFile = "sample_data_set.txt";
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

				//System.out.println(line);
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
		Date date = new Date();
		System.out.println(date.toGMTString());
		try{
			PrintWriter writer = new PrintWriter("date_write_start.txt", "UTF-8");
			writer.write("start time" + date.toGMTString());

			writer.close();
		} catch (IOException e) {
			// do something
		}
		System.out.println("sending request");
		for(String data: list)
		{
			String url = "http://localhost:8080/generic_logging_service/rest/logging/write";
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			//add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			//con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			con.addRequestProperty("Accept", "application/json");


			// Send post request
			con.setDoOutput(true);
			con.setDoInput(true);


			DataOutputStream wr = new DataOutputStream(con.getOutputStream());

			//JSONParser parser = new JSONParser();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("payload", data.trim());
			jsonObject.put("logtype", "EVENT");
			jsonObject.put("appid", "4");

			//System.out.println(jsonObject.toJSONString());

			try {
				wr.write(jsonObject.toJSONString().getBytes());
				wr.flush();
				wr.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			//System.out.println("Post parameters : " + urlParameters);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//print result
			System.out.println(response.toString());

		}
		Date date1 = new Date();
		System.out.println(date1.toGMTString());
		try{
			PrintWriter writer = new PrintWriter("date_write_end.txt", "UTF-8");
			writer.write("end time" + date1.toGMTString());

			writer.close();
		} catch (IOException e) {

		}
		System.out.println("write compleete");
	}


	public void write_multiple() throws ParseException, IOException
	{
		System.out.println("in write multiple");
		String csvFile = "sample_data_set.txt";
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

				//System.out.println(line);
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
		Date date = new Date();
		System.out.println(date.toGMTString());
		try{
			PrintWriter writer = new PrintWriter("date_w_multiple_start.txt", "UTF-8");
			writer.write("start time" + date.toGMTString());

			writer.close();
		} catch (IOException e) {
			// do something
		}
		
		//create jsonArrayList
		JSONArray jArray = new JSONArray();
		for(String data: list)
		{

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("payload", data.trim());
			jsonObject.put("logtype", "EVENT");
			jsonObject.put("appid", "4");
			jArray.add(jsonObject);

		}


		String url = "http://localhost:8080/generic_logging_service/rest/logging/write_multiple";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		//con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		con.addRequestProperty("Accept", "application/json");


		// Send post request
		con.setDoOutput(true);
		con.setDoInput(true);


		DataOutputStream wr = new DataOutputStream(con.getOutputStream());


		try {
			wr.write(jArray.toJSONString().getBytes());
			wr.flush();
			wr.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + urlParameters);
		//System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		//System.out.println(response.toString());


		Date date1 = new Date();
		System.out.println(date1.toGMTString());
		try
		{
			PrintWriter writer = new PrintWriter("date_w_multiple_end.txt", "UTF-8");
			writer.write("end time" + date1.toGMTString());

			writer.close();
		}
		catch (IOException e) {

		}
		System.out.println("write multiple compleete");
	}
	
	
	public void flush(String lsn) throws ParseException, IOException
	{
		System.out.println("in flush");
		Date date = new Date();
		System.out.println(date.toGMTString());
		try{
			PrintWriter writer = new PrintWriter("date_flush_start.txt", "UTF-8");
			writer.write("start time" + date.toGMTString());

			writer.close();
		} catch (IOException e) {
			// do something
		}
		
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("lsn",lsn );
			jsonObject.put("appid", "4");
			

		String url = "http://localhost:8080/generic_logging_service/rest/logging/flush";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("PUT");
		con.setRequestProperty("User-Agent", USER_AGENT);
		//con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		con.addRequestProperty("Accept", "application/json");


		// Send post request
		con.setDoOutput(true);
		con.setDoInput(true);


		DataOutputStream wr = new DataOutputStream(con.getOutputStream());


		try {
			wr.write(jsonObject.toJSONString().getBytes());
			wr.flush();
			wr.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + urlParameters);
		//System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		//System.out.println(response.toString());


		Date date1 = new Date();
		System.out.println(date1.toGMTString());
		try
		{
			PrintWriter writer = new PrintWriter("date_flush_end.txt", "UTF-8");
			writer.write("end time" + date1.toGMTString());

			writer.close();
		}
		catch (IOException e) {

		}
		System.out.println("flush compleete");
	}
	
	
	public void delete(String lsn) throws ParseException, IOException
	{
		
		Date date = new Date();
		System.out.println(date.toGMTString());
		try{
			PrintWriter writer = new PrintWriter("date_delete_start.txt", "UTF-8");
			writer.write("start time" + date.toGMTString());

			writer.close();
		} catch (IOException e) {
			// do something
		}
		
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("lsn",lsn );
			
			

		String url = "http://localhost:8080/generic_logging_service/rest/logging/delete";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("DELETE");
		con.setRequestProperty("User-Agent", USER_AGENT);
		//con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		con.addRequestProperty("Accept", "application/json");


		// Send post request
		con.setDoOutput(true);
		con.setDoInput(true);


		DataOutputStream wr = new DataOutputStream(con.getOutputStream());


		try {
			wr.write(jsonObject.toJSONString().getBytes());
			wr.flush();
			wr.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());


		Date date1 = new Date();
		System.out.println(date1.toGMTString());
		try
		{
			PrintWriter writer = new PrintWriter("date_delete_end.txt", "UTF-8");
			writer.write("end time" + date1.toGMTString());

			writer.close();
		}
		catch (IOException e) {

		}
	}
}

