package fullwipe.ciriitc.whereismybus;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import fullwipe.ciriitc.whereismybus.utilities.FermateService;

/**
 * Created by Kurtis on 06/06/15.
 */
public class ChiediDialog extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Attenzione!");
        alertDialog.setMessage("Sei giunto al capolinea?");
        alertDialog.setIcon(R.drawable.ic_launcher);

        alertDialog.setCancelable(false);
        alertDialog.setButton("Sì", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                stopService(new Intent(getApplicationContext(), FermateService.class));
                Intent intent = new Intent(getApplicationContext(),Main.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        alertDialog.setButton2("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }
}