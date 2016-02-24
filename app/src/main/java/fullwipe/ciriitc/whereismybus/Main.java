package fullwipe.ciriitc.whereismybus;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//import fullwipe.ciriitc.whereismybus.dbfermate.DatabaseActivity;
import fullwipe.ciriitc.whereismybus.dbprofili.DatabaseHandler;
import fullwipe.ciriitc.whereismybus.utilities.GPSTracker;
import fullwipe.ciriitc.whereismybus.utilities.Variables2;

public class Main extends AppCompatActivity implements TextToSpeech.OnInitListener {
	 protected static final int NUMPROFILI=1,RESULT_SPEECH = 2;
	 Button avanti,indietro,infotempomax,annullatutto;
	 ImageButton aggiungiprofilo,microfono,profili,cancellaprofili;
	 Context context = this;
	 DatabaseHandler db;
	 int numprofili,selected=0;
	 String gfermata,glinea,aggpr = "Aggiungi un profilo",mic = "Parla ora",type,gotAddress;
	 Variables2 gv;
	 private TextToSpeech tts;
	 EditText etnome,ettempomax;
	 AutoCompleteTextView etindirizzo;
	 RadioGroup sceltaGroup;
	 RadioButton scelta;
	 boolean withGps,nogps_m;
   	 GPSTracker gps;
   	 Geocoder geocoder;
     List<Address> addresses;
     LinearLayout lladd1,lladd2,llnogps;
     List<Address> listAddress;
     Spinner selType,selTown;
     ArrayAdapter<String> adapter;
 @Override
 public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  setContentView(R.layout.main_root);
		  
		  /*Bundle extras = getIntent().getExtras();
		  if(extras !=null) {
			  nogps_m = extras.getBoolean("NOGPS");
		  }
		  else nogps_m = false;*/
		  
		  String[] streets = getResources().getStringArray(R.array.streets);
		  adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, streets);
		  

		  gps = new GPSTracker(Main.this);	
		  tts = new TextToSpeech(this, this);
		  gv = Variables2.getInstance();
		  
		  aggiungiprofilo = (ImageButton)findViewById(R.id.topleft);
		  profili = (ImageButton)findViewById(R.id.topright);
		  cancellaprofili = (ImageButton)findViewById(R.id.bottomleft);
		  microfono = (ImageButton)findViewById(R.id.microphone);
		  
		  aggiungiprofilo.setOnClickListener(aggiungiprofiloOnClick);
		  profili.setOnClickListener(profiliOnClick);
		  cancellaprofili.setOnClickListener(cancellaOnClick);
		  microfono.setOnClickListener(microfonoOnClick);
		  
		  db = new DatabaseHandler(this);
		  
		  numprofili = db.getCount();
		  //profili.setText("Profili memorizzati: " + String.valueOf(numprofili));
		  
		  /*if(gps.canGetLocation()){
			  geocoder = new Geocoder(this, Locale.getDefault());
	        }else{
	        	gps.showSettingsAlert();
	        	if(gps.canGetLocation()){
	        		geocoder = new Geocoder(this, Locale.getDefault());
		        }
	        	else { 
	        		Toast.makeText(getApplicationContext(), "Segnale GPS non trovato", Toast.LENGTH_SHORT).show();
	        	}
	        }*/
		  
		  /*if(nogps_m==true){
			  gps.showSettingsAlert();
			  nogps_m=false;
		  }*/
		  
		  
 }
 
 @Override
 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
  super.onActivityResult(requestCode, resultCode, data);
  
  switch (requestCode) {
		  case NUMPROFILI: {
			  if(resultCode==RESULT_OK){
			     Intent refresh = new Intent(this, Main.class);
			     startActivity(refresh);
			     this.finish();
			     break;
			  }
		  }
		
		  case RESULT_SPEECH: {
				if (resultCode == RESULT_OK && null != data) {

					ArrayList<String> text = data
							.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

					if(text.get(0).equals("profili")||text.get(0).equals("elenco profili")||text.get(0).equals("elenco")){
						Intent i = new Intent(getBaseContext(), Profili.class);
						   i.putExtra("FERMATA",gfermata);
					       i.putExtra("LINEA",glinea);
					       startActivity(i);
					}
					else if(text.get(0).equals("cancella")||text.get(0).equals("cancella profili")||text.get(0).equals("cancella profilo")){
						Intent i = new Intent(getBaseContext(), Cancella.class);
						   startActivityForResult(i, NUMPROFILI);
					}
				}
				break;
			}

  }
 }
 

 OnClickListener aggiungiprofiloOnClick = new OnClickListener() {
  @Override
  public void onClick(View v) {
   gps = new GPSTracker(Main.this);
   //speakOut(aggpr);
   final Dialog dialog = new Dialog(context);
   dialog.setContentView(R.layout.dialog_aggiungi);
   dialog.setTitle("Aggiungi un nuovo profilo");
   lladd1 = (LinearLayout)dialog.findViewById(R.id.lladd1);
   lladd2 = (LinearLayout)dialog.findViewById(R.id.lladd2);
   llnogps = (LinearLayout)dialog.findViewById(R.id.llnogps);
   avanti = (Button)dialog.findViewById(R.id.agg_cont);
   indietro = (Button)dialog.findViewById(R.id.agg_ind);
   infotempomax = (Button)dialog.findViewById(R.id.infotm);
   annullatutto = (Button)dialog.findViewById(R.id.annullatutto);
   
   lladd1.setVisibility(View.VISIBLE);
   lladd2.setVisibility(View.GONE);
   llnogps.setVisibility(View.GONE);
   
   selType = (Spinner)dialog.findViewById(R.id.selType);
   selTown = (Spinner)dialog.findViewById(R.id.selTown);
   
   etnome = (EditText) dialog.findViewById(R.id.nome);
   etindirizzo = (AutoCompleteTextView) dialog.findViewById(R.id.indirizzo);
   ettempomax = (EditText) dialog.findViewById(R.id.tempomax);
   etindirizzo.setEnabled(false);
   
   sceltaGroup = (RadioGroup)dialog.findViewById(R.id.sceltaGroup);
   selected = sceltaGroup.getCheckedRadioButtonId();
   scelta = (RadioButton)dialog.findViewById(selected);
   withGps=true;
   
   
   etindirizzo.setAdapter(adapter);
   
   sceltaGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
       public void onCheckedChanged(RadioGroup arg0, int id) {
           switch (id) {
           case -1:
             //Log.v(TAG, "Choices cleared!");
             break;
             
           case R.id.radio_gps:
           		etindirizzo.setEnabled(false);
           		withGps=true;
           		llnogps.setVisibility(View.GONE);
             break;
             
           case R.id.radio_ind:
           		etindirizzo.setEnabled(true);
           		withGps=false;
           		llnogps.setVisibility(View.VISIBLE);
             break;
             
           default:
             break;
           }
         }
       });
   
   avanti.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
	    	if(ettempomax.getText().toString() != null && ettempomax.getText().toString().length() >0 ){
		    	if(withGps==false){
		    		if(etindirizzo.getText().toString() != null && etindirizzo.getText().toString().length() >0 ){
			    		lladd1.setVisibility(View.GONE);
				    	lladd2.setVisibility(View.VISIBLE);
		    		}else{
	    			       Toast.makeText(getApplicationContext(), "Inserisci l'indirizzo", Toast.LENGTH_LONG).show(); 
	    			      }
		    	}
		    	else{
		    		lladd1.setVisibility(View.GONE);
			    	lladd2.setVisibility(View.VISIBLE);
		    	}
	    	}else{
	    	       Toast.makeText(getApplicationContext(), "Inserisci il tempo massimo", Toast.LENGTH_LONG).show(); 
	    	        }
			    }
		  });
   
   indietro.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
	    	lladd1.setVisibility(View.VISIBLE);
	    	lladd2.setVisibility(View.GONE);
			    }
		  });
   
   infotempomax.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
	    	Toast.makeText(getApplicationContext(), "Digita quanti minuti di cammino sei disposto a fare per raggiungere una fermata partendo dal punto indicato.", Toast.LENGTH_LONG).show();
			    }
		  });
   
   annullatutto.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
	    	dialog.dismiss();
			    }
		  });
   
   Button addprofile = (Button) dialog.findViewById(R.id.add2);
   addprofile.setText("Aggiungi");
   addprofile.setOnClickListener(new OnClickListener() {
    @Override
    public void onClick(View v) {

    	
    	if(etnome.getText().toString() != null && etnome.getText().toString().length() >0 ){
    	 
    		 
    			 if(withGps){
			       //db.adddata(context, etnome.getText().toString(), etindirizzo.getText().toString(), ettempomax.getText().toString());
    			   Intent i = new Intent(getBaseContext(), Getprofiledata.class);
			       numprofili = db.getCount();
			       double latitude=0,longitude=0;
			       
			       if(gps.canGetLocation()){
			        	latitude = gps.getLatitude();
			        	longitude = gps.getLongitude();	
			        	geocoder = new Geocoder(dialog.getContext(), Locale.getDefault());
			        	try {
							addresses = geocoder.getFromLocation(latitude, longitude, 1);
							gotAddress = addresses.get(0).getAddressLine(0);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									gotAddress = "NULL";
								}
			        	
			        	String address = latitude + "%2C+" + longitude;
					       String timemax = ettempomax.getText().toString();
					       type = "geocoding";
					       
					       gv.setNomeprof2(etnome.getText().toString());
					       gv.setInd2(gotAddress);
					       gv.setTmax2(timemax);
					       i.putExtra("PASSINDIRIZZO",address);
					       i.putExtra("PASSTEMPOMAX",timemax);
					       i.putExtra("PASSTYPE",type);
					       startActivity(i);
					       dialog.dismiss();
			        	
			        }else{
			        	//boolean nogps = true;
		        		Toast.makeText(getApplicationContext(), "Segnale GPS non trovato", Toast.LENGTH_SHORT).show();
		        		//i = new Intent(getBaseContext(), Main.class);
		        		//i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        		//i.putExtra("NOGPS",nogps);
		        		dialog.dismiss();
		        		gps.showSettingsAlert();
			        }
			       
			       
    		}
    		else if(withGps==false){
    			if(etindirizzo.getText().toString() != null && etindirizzo.getText().toString().length() >0 ){
    			   numprofili = db.getCount();
    			   geocoder = new Geocoder(dialog.getContext(), Locale.getDefault());
    			   
    			   String stype = selType.getSelectedItem().toString();
    			   String stown = selTown.getSelectedItem().toString();
    			   String getaddress = etindirizzo.getText().toString();
    			   String address = stype + " " + getaddress + " " + stown;
 			       String timemax = ettempomax.getText().toString();
			       type = "geocoding";
			       float gotlat=0,gotlng=0;
			       
			       try {
						listAddress = geocoder.getFromLocationName(address, 5);
						if(listAddress != null && listAddress.size() > 0) {  
				            gotlat = (float) (listAddress.get(0).getLatitude());  
				            gotlng = (float) (listAddress.get(0).getLongitude());  
						 	}
					   }
			       catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
			       String addressToPass = gotlat + "%2C+" + gotlng;
 			       Intent i = new Intent(getBaseContext(), Getprofiledata.class);
 			       gv.setNomeprof2(etnome.getText().toString());
 			       gv.setInd2(address);
 			       gv.setTmax2(timemax);
 			       i.putExtra("PASSINDIRIZZO",addressToPass);
 			       i.putExtra("PASSTEMPOMAX",timemax);
 			       i.putExtra("PASSTYPE",type);
 			       startActivity(i);
 			       dialog.dismiss();
    			}else{
    			       Toast.makeText(getApplicationContext(), "Inserisci l'indirizzo", Toast.LENGTH_LONG).show(); 
    			      }
    		}
    			 
    			 
      
    		 }else{
  	      Toast.makeText(getApplicationContext(), "Inserisci il Nome del profilo", Toast.LENGTH_LONG).show(); 
  	     }
     
    }
   });
    dialog.setCancelable(false);
    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
    lp.copyFrom(dialog.getWindow().getAttributes());
    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

	dialog.show();
	dialog.getWindow().setAttributes(lp); 
  }
 };
 
 
 OnClickListener profiliOnClick = new OnClickListener() {
	  @Override
	  public void onClick(View v) {
		   Intent i = new Intent(getBaseContext(), Profili.class);
		   //i.putExtra("FERMATA",gfermata);
	       //i.putExtra("LINEA",glinea);
	       startActivity(i);
	  }
	 };
	 
	 OnClickListener cancellaOnClick = new OnClickListener() {
		  @Override
		  public void onClick(View v) {
			   Intent i = new Intent(getBaseContext(), Cancella.class);
			   startActivityForResult(i, NUMPROFILI);
		  }
		 };	
		 
	 OnClickListener microfonoOnClick = new OnClickListener() {
			  @Override
			  public void onClick(View v) {
				  //speakOut(mic);
				  Intent intent = new Intent(
							RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

					intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "it");

					try {
						startActivityForResult(intent, RESULT_SPEECH);
					} catch (ActivityNotFoundException a) {
						Toast t = Toast.makeText(getApplicationContext(),
								"Ops! Your device doesn't support Speech to Text",
								Toast.LENGTH_SHORT);
						t.show();
					}
			  }
			 };		 
	 
	 
	 @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// Handle action bar item clicks here. The action bar will
			// automatically handle clicks on the Home/Up button, so long
			// as you specify a parent activity in AndroidManifest.xml.
			int id = item.getItemId();
			if (id == R.id.credits) {

				AlertDialog.Builder builder =
						new AlertDialog.Builder(this);

				View view = this.getLayoutInflater().inflate(R.layout.credits, null);
				builder.setView(view);

				builder.setTitle("Credits");
				builder.setIcon(R.drawable.ic_info_outline_black_36dp);
				builder.setPositiveButton("Chiudi", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
				AppCompatDialog dialog = builder.create();
				dialog.show();
				
				
	 
//				Button close = (Button) dialog.findViewById(R.id.closecredits);
//				// if button is clicked, close the custom dialog
//				close.setOnClickListener(new OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						dialog.dismiss();
//					}
//				});
				
//				WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//			    lp.copyFrom(dialog.getWindow().getAttributes());
//			    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//			    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//
//				dialog.show();
//				dialog.getWindow().setAttributes(lp);
				
				return true;
			}
			
//			if (id == R.id.db) {
//				Intent i = new Intent(getBaseContext(), InViaggio.class);
//				   startActivity(i);
//			}
			return super.onOptionsItemSelected(item);
		}
		
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