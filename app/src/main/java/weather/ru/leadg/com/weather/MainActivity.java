package weather.ru.leadg.com.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      list = (ListView)findViewById(R.id.weather_list);
        LoadWeather();
    }
    public void LoadWeather()
    {
        Network nw = new Network("http://api.openweathermap.org/data/2.5/forecast/daily?q=Kazan&mode=json&units=metric&cnt=14&appid=df5f9680024087caecf0b40f68bf07f9", this);
        nw.setCompleteListener(new IComplete() {
            @Override
            public void onComplite(JSONObject data) {
                Log.i("Данные", data.toJSONString());
                showData(data);
            }

            @Override
            public void onError() {
                 new ModalDialog(MainActivity.this).setOk().showDialog("Ошибка","Ошибка получения данных");
            }
        });
        nw.LoadData();
    }
    public void showData(JSONObject data)
    {
        JSONArray items = (JSONArray)data.get("list");
        String[] list = new String[items.size()];
        JSONObject item;
        for(int  i = 0;i < items.size();i ++)
        {
            item = (JSONObject)items.get(i);
            list[i] = "День:" + ((JSONObject)item.get("temp")).get("day").toString() + "\nночь:" + ((JSONObject)item.get("temp")).get("night").toString() + "\nУтро:" +
                    ((JSONObject)item.get("temp")).get("morn").toString() + "\nвечер:" +  ((JSONObject)item.get("temp")).get("eve").toString();
        }
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        this.list.setAdapter(adapter);
    }
}
