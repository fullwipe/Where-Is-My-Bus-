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
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import fullwipe.ciriitc.whereismybus.utilities.GPSTracker;

public class Getrealtimedata extends AppCompatActivity implements
TextToSpeech.OnInitListener {
	
	String fermata,linea,cX,cY,terminal,url;
	TextView tv;
	private TextToSpeech tts;
	ImageButton refresh,salgo;
	ProgressBar pb;
    GPSTracker gps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.getrealtimedata_root);

        refresh = (ImageButton)findViewById(R.id.aggiorna_grtd);
        salgo = (ImageButton)findViewById(R.id.salgo_grtd);
		pb = (ProgressBar)findViewById(R.id.progressbar);
		
		refresh.setEnabled(false);
        salgo.setEnabled(false);
		pb.setVisibility(View.VISIBLE);
		
		tts = new TextToSpeech(this, this);
		tv = (TextView) findViewById(R.id.tvresponso);
		Bundle extras = getIntent().getExtras();
		if(extras !=null) {
		    fermata = extras.getString("FERMATA");
		    linea = extras.getString("LINEA");
            cX = extras.getString("COORDX");
            cY = extras.getString("COORDY");
            terminal = extras.getString("TERMINAL");
		}
		
		url = "http://137.204.107.67/tper/BusApp/getRealTimeData.php?fermata="+fermata+"&linea="+linea;
		grtd();
		
		refresh.setOnClickListener(new OnClickListener() {
	 		public void onClick(View v) {
	 			refresh.setEnabled(false);
                salgo.setEnabled(false);
	 			pb.setVisibility(View.VISIBLE);
	 			tv.setText("Richiesta in corso...");
	 			speakOut(tv.getText().toString());
	 			grtd();
	 			}
	 		});

        salgo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                gps = new GPSTracker(Getrealtimedata.this);
                if(gps.canGetLocation()) {
                    Intent intent = new Intent(getApplicationContext(), InViaggio.class);
                    intent.putExtra("inviaggio_linea", linea);
                    intent.putExtra("inviaggio_x", cX);
                    intent.putExtra("inviaggio_y", cY);
                    intent.putExtra("inviaggio_terminal", terminal);
                    startActivity(intent);
                }
                else gps.showSettingsAlert();

            }
        });
	}
	
	void grtd(){
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url,new AsyncHttpResponseHandler() {
	        @Override
	        public void onSuccess(String response) {
	                try {

	                    JSONObject jsonObject = new JSONObject(response);
	                    String responso = jsonObject.getString("response");
                        responso = responso.replace("DaSatellite", "");
                        String[] separated = responso.split(",");
                        String responso_finale = "";

                        for (int i = 0; i < separated.length; i++) {
                            String r = "Linea" + separated[i].substring(0, 3);
                            String r2 = separated[i].replace(separated[i].substring(0, 4), "");
                            if (r2.contains("Previsto")) r2 = r2.replace("Previsto", " previsto alle ore");
                            else r2 = " alle ore" + r2;

                            if (!responso_finale.equals("")) responso_finale = responso_finale + ",\n" + r + r2;
                            else responso_finale = r + r2;


                        }

	                    speakOut(responso_finale);
	                    tv.setText(responso_finale);
	                    refresh.setEnabled(true);
                        salgo.setEnabled(true);
	                    pb.setVisibility(View.GONE);

	                } catch (JSONException e) {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
	                }
	           
	        }
            @Override
            public void onFailure(Throwable e,String response) {
               
               Log.d("AREQ", "http GET request failed");
				String responso = "Impossibile contattare il server. Per favore, riprova.";
				speakOut(responso);
				tv.setText(responso);
				refresh.setEnabled(true);
				pb.setVisibility(View.GONE);
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
				speakOut(tv.getText().toString());
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
