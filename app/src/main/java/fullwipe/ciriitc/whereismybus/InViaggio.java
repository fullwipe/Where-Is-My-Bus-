package fullwipe.ciriitc.whereismybus;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import fullwipe.ciriitc.whereismybus.dbfermate.CSVFile;
import fullwipe.ciriitc.whereismybus.utilities.FermateService;


public class InViaggio extends AppCompatActivity {
    Button scendo;
    public static TextView inf, info, fprec;
    Context context = this;
    List<String[]> scoreList;
    public static List<String[]> fermateList;
    int sp;
    int count;
    String codicelinea, coordX, coordY, terminal;
    Intent service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inviaggio);

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            codicelinea = extras.getString("inviaggio_linea");
            coordX = extras.getString("inviaggio_x");
            coordY = extras.getString("inviaggio_y");
            terminal = extras.getString("inviaggio_terminal");
        } else {
            codicelinea = "NA";
            coordX = "40,870317";
            coordY = "14,090757";
            terminal = "FERMATA 8 CAPOLINEA";
        }

        scendo = (Button) findViewById(R.id.iv_scendo);
        inf = (TextView) findViewById(R.id.iv_tv1);
        info = (TextView) findViewById(R.id.iv_tv2);
        fprec = (TextView) findViewById(R.id.iv_tv0);

        scendo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scendoDalBus();
            }
        });

        InputStream inputStream = getResources().openRawResource(R.raw.linee_sequenza_fermate);
        CSVFile csvFile = new CSVFile(inputStream);
        scoreList = csvFile.read();

        fermateList = new ArrayList<String[]>();
        count = 0;
        for (int i = 0; i < scoreList.size(); i++) {
            String[] fermata = scoreList.get(i);
            if (fermata[0].equals(codicelinea)) {
                fermateList.add(fermata);
                if (fermata[7].equals(coordX) && fermata[8].equals(coordY)) sp = count;
                count++;
            }
        }

        for (int i = sp; i < fermateList.size(); i++) {
            String[] fermata = fermateList.get(i);
        }

        //startService(new Intent(this, FermateService.class));

        service = new Intent(this, FermateService.class);
        service.putExtra("sp", sp+1);
        service.putExtra("capolinea", terminal);
        startService(service);


    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.credits) {
//
//            final Dialog dialog = new Dialog(this);
//            dialog.setContentView(R.layout.credits);
//            dialog.setTitle("Credits");
//            TextView link1 = (TextView)findViewById(R.id.tvlink1);
//            TextView link2 = (TextView)findViewById(R.id.tvlink2);
//
//
//
//            Button close = (Button) dialog.findViewById(R.id.closecredits);
//            // if button is clicked, close the custom dialog
//            close.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                }
//            });
//
//            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//            lp.copyFrom(dialog.getWindow().getAttributes());
//            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//
//            dialog.show();
//            dialog.getWindow().setAttributes(lp);
//
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    void scendoDalBus(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        alertDialogBuilder.setTitle("Attenzione!");
        alertDialogBuilder
                .setMessage("Vuoi veramente scendere dal bus?")
                .setCancelable(false)
                .setPositiveButton("Sì",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        stopService(service);
                        InViaggio.this.finish();
                        Intent intent = new Intent(getApplicationContext(),Main.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        scendoDalBus();
        //super.onBackPressed();
    }
}
