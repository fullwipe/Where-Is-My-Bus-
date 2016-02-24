package fullwipe.ciriitc.whereismybus.utilities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import fullwipe.ciriitc.whereismybus.Main;
import fullwipe.ciriitc.whereismybus.R;
import fullwipe.ciriitc.whereismybus.dbprofili.Data;
import fullwipe.ciriitc.whereismybus.dbprofili.DatabaseHandler;

//import fullwipe.ciriitc.whereisbybus.Main.ViewAdapter;

public class Getprofiledata_customadapter extends ArrayAdapter<Data>{
	
	Variables2 gv;
	 DatabaseHandler db;
	 
	
    public Getprofiledata_customadapter(Context context, int textViewResourceId,
            List <Data> objects) {
        super(context, textViewResourceId, objects);

        
        db = new DatabaseHandler(getContext());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
             .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.data_riga, null);
        final TextView fermata = (TextView)convertView.findViewById(R.id.tv1);
        TextView denominazione = (TextView)convertView.findViewById(R.id.tv2);
        TextView ubicazione = (TextView)convertView.findViewById(R.id.tv3);
        final TextView linea = (TextView)convertView.findViewById(R.id.tv4);
        TextView capolinea = (TextView)convertView.findViewById(R.id.tv5);
        
        gv = Variables2.getInstance();
        
        Button add = (Button)convertView.findViewById(R.id.aggiungi);
        
        final Data c = getItem(position);
        fermata.setText(c.getFermata());
        denominazione.setText(c.getDenominazione());
        ubicazione.setText(c.getUbicazione());
        linea.setText(c.getLinea());
        capolinea.setText(c.getCapolinea());
        
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent i = new Intent(getContext(), Main.class);
            	//i.putExtra("PASSFERMATA",fermata.getText().toString());
            	//i.putExtra("PASSLINEA",linea.getText().toString());
            	
            	db.adddata(getContext(), gv.getNomeprof2(), gv.getInd2(), gv.getTmax2(), c.getFermata(), c.getLinea(), c.getCoordX(), c.getCoordY(), c.getCapolinea());
            	Toast.makeText(getContext(), "Profilo salvato", Toast.LENGTH_LONG).show();
            	i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            	getContext().startActivity(i);
            	//getContext().finish();
            }
           });
        
        return convertView;
    }

}
