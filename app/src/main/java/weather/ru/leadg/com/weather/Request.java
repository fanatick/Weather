package weather.ru.leadg.com.weather;
import android.os.*;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Set;

import org.apache.http.ParseException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
public class Request extends AsyncTask<Integer,  Integer, JSONObject>{
    private URL url_obj;
    private Network parent;
    private boolean error = false;
    private JSONObject sending_data;
    private HttpURLConnection con;
	public Request(String url, Network nw)
	{
		parent = nw;
		try {
			url_obj = new URL(url);
		} catch(Exception e)
		{
			
		}
	}
	
public void changeURL(String url)
{
	try {
		url_obj = new URL(url);
	} catch(Exception e)
	{
		
	}
}
public void putSendingData(JSONObject params)
{
	sending_data = params;
}
public void closeConnection(){
	con.disconnect();
}
private JSONObject loadData()
{
	int responseCode = 1;
	try {
	con = (HttpURLConnection) url_obj.openConnection();
	con.setRequestMethod("GET");
	//con.setRequestProperty("User-Agent","Mozilla/5.0");
	responseCode = con.getResponseCode();
	
	BufferedReader in = new BufferedReader(
	        new InputStreamReader(con.getInputStream()));
	String inputLine;
	StringBuffer response = new StringBuffer();

	while ((inputLine = in.readLine()) != null) {
		response.append(inputLine);
	}
	in.close();
	con.disconnect();
	JSONParser parser = new JSONParser();
	JSONObject jsonObj = new JSONObject();
	try {
	Object obj = parser.parse(response.toString());
	jsonObj = (JSONObject) obj;
	}  catch (org.json.simple.parser.ParseException e) {
		// TODO Auto-generated catch block
		jsonObj = new JSONObject();
		error = true;
		jsonObj.put("string",response.toString());
		jsonObj.put("message", e.getMessage());
		jsonObj.put("code", "1");
	}
	return jsonObj;
	} catch (IOException e)
	{
		JSONObject obj = new JSONObject();
		
		error = true;
		return new JSONObject();
	}
}
public String getURLString()
{
	String result = "";
	Set<String> keys = sending_data.keySet();
	Iterator i = keys.iterator();
	while(i.hasNext())
	{
		try {
		String key = (String)i.next();
		result += key + "=" + URLEncoder.encode(sending_data.get(key).toString(), "UTF-8") + "&";
		} catch (Exception e)
		{
			
		}
	}
	return result;
}
private JSONObject sendPost() throws Exception {
	 
	JSONObject result = new JSONObject();
	JSONParser parser = new JSONParser();
	con = (HttpURLConnection) url_obj.openConnection();

	//add reuqest header
	con.setRequestMethod("POST");
	con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	
	String urlParameters = getURLString();

	// Send post request
	con.setDoOutput(true);
	DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	wr.writeBytes(urlParameters);
	wr.flush();
	wr.close();

	int responseCode = con.getResponseCode();
	

	BufferedReader in = new BufferedReader(
	        new InputStreamReader(con.getInputStream()));
	String inputLine;
	StringBuffer response = new StringBuffer();

	while ((inputLine = in.readLine()) != null) {
		response.append(inputLine);
	}
	in.close();
	 Log.i("Статус", response.toString());
	try {
		result = (JSONObject)parser.parse(response.toString());
	} catch (ParseException e)
	{

	 result = new JSONObject();
	 result.put("string",response.toString());
	 result.put("message", e.getMessage());
	 result.put("code", "1");
	
	}
	//print result
	return result;

}


	protected void onPostExecute(JSONObject param) {
	  if(!error)
	      parent.onComplete(param);
	  else
		 parent.onError(param);
	 error = false;
	    }



	@Override
	protected JSONObject doInBackground(Integer... params) {
		if(params[0] == 1)
		{
			return loadData();
		} else if(params[0] == 2)
		{
			try 
			{
			return sendPost();
			} catch(Exception e)
			{
				JSONObject data = new JSONObject();
				error = true;
				data.put("message", e.getMessage());
				return data;
			}
		}
		JSONObject data = new JSONObject();
		data.put("message","������!");
		data.put("code","1");
		return data;
	}

}
