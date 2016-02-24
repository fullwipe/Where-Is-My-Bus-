package fullwipe.ciriitc.whereismybus.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import fullwipe.ciriitc.whereismybus.ChiediDialog;
import fullwipe.ciriitc.whereismybus.InViaggio;
import android.location.LocationListener;

import fullwipe.ciriitc.whereismybus.InViaggio;

/**
 * Created by Giovanni on 25/04/2015.
 */
public class FermateService  extends Service {
    Boolean fermatasup = false;
    public static Boolean corsafinita = false;
    float results = 0, precresults = 1;
    int sp;
    String terminal;
    GPSTracker gps;
    Context context = this;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        Toast.makeText(this,"stopped",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart(Intent intent, int startid) {
        sp = 0;
        if (intent !=null && intent.getExtras()!=null) {
            sp = intent.getExtras().getInt("sp");
            terminal = intent.getExtras().getString("capolinea");
        }

//        while (!corsafinita) {
//
//            if (precresults < results)
//                Toast.makeText(getApplicationContext(), "Attenzione! Assicurati che sei salito sul pullman giusto", Toast.LENGTH_SHORT).show();
//
//            if (fermatasup) {
//                fermatasup = false;
//                sp++;
//            }
//
//            update(sp);
//        }
//
//        stopService(new Intent(this, FermateService.class));

//        Intent i = new Intent(getApplicationContext(), ChiediDialog.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(i);

        LocationListener locationListener = new MyLocationListener();
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, locationListener);

        /****
         * while (!corsafinita)
         *          fai i vari controlli
         *          update();
         */
    }

    private final class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            // called when the listener is notified with a location update from the GPS
//            Toast.makeText(getApplicationContext(),
//                        String.valueOf(location.getLatitude()) + ", " +
//                        String.valueOf(location.getLongitude()),
//                        Toast.LENGTH_SHORT).show();

            if (precresults < results)
            Toast.makeText(getApplicationContext(), "Attenzione! Assicurati che sei salito sul pullman giusto", Toast.LENGTH_SHORT).show();

            if (fermatasup) {
                fermatasup = false;
                sp++;
            }

            update(sp, location.getLatitude(), location.getLongitude());
        }

        @Override
        public void onProviderDisabled(String provider) {
            // called when the GPS provider is turned off (user turning off the GPS on the phone)
        }

        @Override
        public void onProviderEnabled(String provider) {
            // called when the GPS provider is turned on (user turning on the GPS on the phone)
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // called when the status of the GPS provider changes
        }
    }

    void update(int i, double yLat, double yLong) {
        float[] getres = new float[1];
        String fermata[] = InViaggio.fermateList.get(i);
        String str1 = fermata[7].replace(',','.');
        String str2 = fermata[8].replace(',','.');
        double fLat = Double.parseDouble(str1);
        double fLong = Double.parseDouble(str2);
        gps = new GPSTracker(FermateService.this);

        if(gps.canGetLocation()) {
            Location.distanceBetween(fLat, fLong, yLat, yLong, getres);
            results = getres[0];
            boolean isWithin20metres = results < 20;

            if (isWithin20metres) {
                if (fermata[2].equals(terminal)) {
                    Intent in = new Intent(getApplicationContext(), ChiediDialog.class);
                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(in);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Fermata " + fermata[2] + " superata", Toast.LENGTH_SHORT).show();
                    fermatasup = true;
                    InViaggio.fprec.setText(fermata[2]);
                    String prossFermata[] = InViaggio.fermateList.get(i+1);
                    InViaggio.info.setText(prossFermata[2]);
                }
            }
            else {
                InViaggio.info.setText(fermata[2] + "\na " + results + " metri");
                precresults = results;
            }
        }
        else gps.showSettingsAlert();
    }

//    void chiedi(){
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                this);
//
//        alertDialogBuilder.setTitle("Attenzione!");
//        alertDialogBuilder
//                .setMessage("Sei giunto al capolinea?")
//                .setCancelable(false)
//                .setPositiveButton("Sì",new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog,int id) {
//                        corsafinita = true;
//                    }
//                })
//                .setNegativeButton("No",new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog,int id) {
//                        dialog.cancel();
//                    }
//                });
//        AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialog.show();
//    }
}
