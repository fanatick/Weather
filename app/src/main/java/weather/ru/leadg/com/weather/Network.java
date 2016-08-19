package weather.ru.leadg.com.weather;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;


public class Network {
	private Request req;
	private Activity main;
	private IComplete listener;
	private   ConnectivityManager cm;
	private ModalDialog dialog;
    private String server_url;
  public Network(String url, Activity root)
  {
      server_url = url;
	  req = new Request(url, this);
	  main = root;
	  dialog = new ModalDialog(main);
	  dialog.setOk();
	  cm = (ConnectivityManager) main
              .getSystemService(Context.CONNECTIVITY_SERVICE);
	  
  }
  public void changeURL(String url)
  {
	  req.changeURL(url);
  }
  public void setCompleteListener(IComplete complete)
  {
	  listener = complete;
  }
  public Boolean sendData(JSONObject data)
  {
	  Boolean result = false;
	  NetworkInfo inf = cm.getActiveNetworkInfo();
	  if(inf != null && inf.isConnected())
	  {
	  req.putSendingData(data);
	  req.execute(2);
	  } else {
          listener.onError();
		  dialog.showDialog("Ошибка!", "Нет соединения с интернетом!");
	  }

	  return result;
	  
  }

  public void LoadData()
  {
	  req.execute(1);
  }
  public void closeConnection(){
	  req.closeConnection();
  }
  public void onComplete(JSONObject  code)
  {
	
	 listener.onComplite(code);
  }
  public void onError(JSONObject result)
  {
	 //Log.i("Статус", result.toString());
	  listener.onError();
  }
  public Boolean login(String login, String password)
  {
	  Boolean result = false;
	  return result;
  }
}
