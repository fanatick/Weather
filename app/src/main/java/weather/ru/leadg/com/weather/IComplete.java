package weather.ru.leadg.com.weather;

import org.json.simple.JSONObject;

public interface IComplete {
 public void onComplite(JSONObject data);
 public void onError();
}
