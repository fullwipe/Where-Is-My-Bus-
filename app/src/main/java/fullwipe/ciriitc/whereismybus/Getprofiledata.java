package fullwipe.ciriitc.whereismybus;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import fullwipe.ciriitc.whereismybus.dbprofili.Data;
import fullwipe.ciriitc.whereismybus.utilities.Getprofiledata_customadapter;

public class Getprofiledata extends AppCompatActivity implements TextToSpeech.OnInitListener {
	
	String num,address,timemax,profilename,type,url,noresults="Attenzione! Nessun risultato trovato!";
	Boolean ok=true;
	int i=0;
	LinearLayout llnoresults;
	Button backtoMain;
	private TextToSpeech tts;
	ProgressBar bar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.data_root);
		
		tts = new TextToSpeech(this, this);
		llnoresults = (LinearLayout)findViewById(R.id.llnoresults);
		llnoresults.setVisibility(View.GONE);
		bar = (ProgressBar)findViewById(R.id.progressbar2);
		bar.setVisibility(View.VISIBLE);
		
		Bundle extras = getIntent().getExtras();
		if(extras !=null) {
			//profilename = extras.getString("PASSNOMEPROFILO");
		    address = extras.getString("PASSINDIRIZZO");
		    timemax = extras.getString("PASSTEMPOMAX");
		    type = extras.getString("PASSTYPE");
		}
		
		if(type.equals("address")){
			address = address.replace(' ','+');
		}
		
		url = "http://137.204.107.67/tper/BusApp/getDataProfile.php?type=" +type+ "&location=" +address+ "&time=" +timemax;
		final ListView listadata = (ListView) findViewById(R.id.listadata);
		final List<Data> datalist = new LinkedList <Data>();
		
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url,new AsyncHttpResponseHandler() {
	        @Override
	        public void onSuccess(String response) {
	            ok=true;
	            i=1;
	            num = Integer.toString(i);
	            while(ok==true){
	                try {

	                    JSONObject jsonObject = new JSONObject(response);
	                    JSONObject object = jsonObject.getJSONObject(num);

	                    String codicefermata = object.getString("codice_fermata");
	    				String denominazione = object.getString("denominazione");
	    				String ubicazione = object.getString("ubicazione");
	    				String codicelinea = object.getString("codice_linea");
	    				String denominazionecapolinea = object.getString("denominazione_capolinea");
                        String latitudine = object.getString("latitudine");
                        String longitudine = object.getString("longitudine");

	                    // Save strings to your list
	                    datalist.add(new Data(codicefermata,
				                    		denominazione,
				                    		ubicazione,
				                    		codicelinea,
				                    		denominazionecapolinea,
                                            latitudine,
                                            longitudine));

	                    i++;
	                    num = Integer.toString(i);
	                    bar.setVisibility(View.GONE);
	                } catch (JSONException e) {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
	                    ok=false;
	                }
	            }
	            if(datalist.isEmpty()){
	            	speakOut(noresults);
	            	setContentView(R.layout.data_nores);
	            	bar.setVisibility(View.GONE);
	        		llnoresults.setVisibility(View.VISIBLE);
	        		listadata.setVisibility(View.GONE);
	        		backtoMain = (Button)findViewById(R.id.backtomain);
	            	backtoMain.setOnClickListener(new OnClickListener() {
	        	 		public void onClick(View v) {
	        	 			Intent i = new Intent(getBaseContext(), Main.class);
	        	 			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        	 			startActivity(i);
	        	 			}
	        	 		});
	            }
	            else {
	            // When the data loop is over create the adapter and set it to your ListView
	            Getprofiledata_customadapter adapter = new Getprofiledata_customadapter(Getprofiledata.this,
	                    R.layout.data_riga, datalist);
	            listadata.setAdapter(adapter);
	            }
	        }
            @Override
            public void onFailure(Throwable e,String response) {
               
               Log.d("AREQ","http GET request failed");
            }
         });
		
		
		
	}

//	@Override
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
//			final Dialog dialog = new Dialog(this);
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
				//speakOut(tv.getText().toString());
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
