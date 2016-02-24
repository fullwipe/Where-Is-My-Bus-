package fullwipe.ciriitc.whereismybus;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import fullwipe.ciriitc.whereismybus.dbprofili.DatabaseHandler;
import fullwipe.ciriitc.whereismybus.dbprofili.ProfiliList;

public class Profili extends AppCompatActivity implements
TextToSpeech.OnInitListener {
 ProfiliList profList = new ProfiliList();
 Context context = this;
 DatabaseHandler db;
 ListView listView;
 List<ProfiliList> profiliList;
 //LinearLayout layout;
 int numprofili;
 TextView numofprofili;
 String gfermata,glinea,prmem = "Profili memorizzati";
 private TextToSpeech tts;
 @Override
 public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  setContentView(R.layout.profili);
		  
		  /*Bundle extras = getIntent().getExtras();
		  if(extras !=null) {
		      gfermata = extras.getString("FERMATA");
		      glinea = extras.getString("LINEA");
		  }*/
		
		  
		  tts = new TextToSpeech(this, this);
		  //speakOut(prmem);
		  
		  //layout = (LinearLayout)findViewById(R.id.layout);
		  listView = (ListView)findViewById(R.id.listView);
		  numofprofili = (TextView)findViewById(R.id.numprofili);
		  db = new DatabaseHandler(this);
		  
		  profiliList = db.getProfList();
		  listView.setAdapter(new ViewAdapter());
		  
		  numprofili = db.getCount();
		  numofprofili.setText(Integer.toString(numprofili));

 }


 public class ViewAdapter extends BaseAdapter {

	  LayoutInflater mInflater;
	
	  public ViewAdapter() {
	   mInflater = LayoutInflater.from(context);
	  }
	
	  @Override
	  public int getCount() {
	   return profiliList.size();
	  }
	
	  @Override
	  public Object getItem(int position) {
	   return null;
	  }
	
	  @Override
	  public long getItemId(int position) {
	   return position;
	  }
	
	  @Override
	  public View getView(final int position, View convertView, ViewGroup parent) {
	
	   if (convertView == null) {
	    convertView = mInflater.inflate(R.layout.profili_riga,null);
	   }
	
	   final TextView nameText = (TextView) convertView.findViewById(R.id.nomeText);
	   nameText.setText(profiliList.get(position).getNome());
	   final TextView addressText = (TextView) convertView.findViewById(R.id.indirizzoText);
	   addressText.setText(profiliList.get(position).getIndirizzo());
	   final TextView fermataText = (TextView) convertView.findViewById(R.id.fermataText);
	   fermataText.setText(getResources().getString(R.string.fermprof)+" "+
			   		profiliList.get(position).getFermatasc()+" "+
			   		getResources().getString(R.string.tmaxprof)+" "+
			   		profiliList.get(position).getTempomax()+" "+
			   		getResources().getString(R.string.tmaxprof2));
	   final TextView lineaText = (TextView) convertView.findViewById(R.id.lineaText);
	   lineaText.setText(" "+profiliList.get(position).getLineasc());
	   
	   final Button getrealtimeinfo = (Button) convertView.findViewById(R.id.btn_inizia);
	   getrealtimeinfo.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
	    	Intent intent = new Intent(getBaseContext(), Getrealtimedata.class);
	    	intent.putExtra("FERMATA",profiliList.get(position).getFermatasc());
	    	intent.putExtra("LINEA",profiliList.get(position).getLineasc());
            intent.putExtra("COORDX",profiliList.get(position).getCoorX());
            intent.putExtra("COORDY",profiliList.get(position).getCoorY());
            intent.putExtra("TERMINAL",profiliList.get(position).getTerminal());
	    	startActivity(intent);
	    }
	   });
	   
	   return convertView;
	  }
 }
 
// @Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.credits) {
//
//			final Dialog dialog = new Dialog(context);
//			dialog.setContentView(R.layout.credits);
//			dialog.setTitle("Credits");
//			TextView link1 = (TextView)findViewById(R.id.tvlink1);
//			TextView link2 = (TextView)findViewById(R.id.tvlink2);
//
//
//
//			Button close = (Button) dialog.findViewById(R.id.closecredits);
//			// if button is clicked, close the custom dialog
//			close.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					dialog.dismiss();
//				}
//			});
//
//			WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//		    lp.copyFrom(dialog.getWindow().getAttributes());
//		    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//		    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//
//			dialog.show();
//			dialog.getWindow().setAttributes(lp);
//
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
	
	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub

		if (status == TextToSpeech.SUCCESS) {

			int result = tts.setLanguage(Locale.ITALY);

			// tts.setPitch(5); // set pitch level

			// tts.setSpeechRate(2); // set speech speed rate

			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TTS", "Language is not supported");
			} else {
				//speakOut(prmem);
			}

		} else {
			Log.e("TTS", "Initilization Failed");
		}

	}
	
	@Override
	public void onDestroy() {
		// Don't forget to shutdown!
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}
		super.onDestroy();
	}
	
	private void speakOut(String saythis) {

		tts.speak(saythis, TextToSpeech.QUEUE_FLUSH, null);
	}
}