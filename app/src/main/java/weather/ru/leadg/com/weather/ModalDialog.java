package weather.ru.leadg.com.weather;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;

public class ModalDialog {
 private Activity context;
 private AlertDialog dialog;
 private AlertDialog.Builder builder;
 private OnClickListener ok = null, cancel = null;
public ModalDialog(Activity context)
 {
	 builder = new AlertDialog.Builder(context);
	 
	 
 }
public ModalDialog showDialog(String title, String message)
 {
	builder.setTitle(title);

	builder.setMessage(message);
	dialog = builder.create();
	dialog.show();
	return this;
}
public ModalDialog canClose(boolean trig){
	builder.setCancelable(trig);
	return this;
}
public ModalDialog setView(View v)
{
	builder.setView(v);
	return this;
}
public ModalDialog setOkListener(OnClickListener listener)
{
	ok = listener;
	return this;
}
public ModalDialog showDialog(String title){
	builder.setTitle(title);
	dialog = builder.create();
	dialog.show();
	return this;
}
public ModalDialog setCancelListener(OnClickListener listener)
{
	cancel = listener;
	return this;
}
public ModalDialog setOk(String title)
{
	builder.setPositiveButton(title,new DialogInterface.OnClickListener(){

		@Override
		public void onClick(DialogInterface dialog, int which) {
			if(ok != null)
			{
				ok.onClick(null);
			}
			
		}
		
	});
	return this;
}

public ModalDialog setOk()
{
	builder.setPositiveButton("Ок",new DialogInterface.OnClickListener(){

		@Override
		public void onClick(DialogInterface dialog, int which) {
			if(ok != null)
			{
				ok.onClick(null);
			}
			
		}
		
	});
	return this;
}
public  ModalDialog setCancel()
{
	builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {

			if (cancel != null) {
				cancel.onClick(null);
			}
		}
	});
	return this;
}
	public  ModalDialog setCancel(String title)
	{
		builder.setNegativeButton(title, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				if(cancel != null)
				{
					cancel.onClick(null);
				}
			}
		});
		return this;
	}
public void close()
{
	dialog.cancel();
}
}
