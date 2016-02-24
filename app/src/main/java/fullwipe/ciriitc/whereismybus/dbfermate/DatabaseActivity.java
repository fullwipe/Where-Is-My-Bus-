//package fullwipe.ciriitc.whereismybus.dbfermate;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Parcelable;
//import android.widget.ListView;
//
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//
//import fullwipe.ciriitc.whereismybus.R;
//import fullwipe.ciriitc.whereismybus.utilities.FermateService;
//
//
//public class DatabaseActivity extends Activity {
//    private ListView listView;
//    public static ItemArrayAdapter itemArrayAdapter;
//    public static List<String[]> scoreList;
//    public static List<String[]> fermateList;
//    String codicelinea;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.database_activity);
//
//        listView = (ListView) findViewById(R.id.listView);
//        itemArrayAdapter = new ItemArrayAdapter(getApplicationContext(), R.layout.item_layout);
//
//        Parcelable state = listView.onSaveInstanceState();
//        listView.setAdapter(itemArrayAdapter);
//        listView.onRestoreInstanceState(state);
//
//        InputStream inputStream = getResources().openRawResource(R.raw.linee_sequenza_fermate);
//        CSVFile csvFile = new CSVFile(inputStream);
//        scoreList = csvFile.read();
//
//        fermateList = new ArrayList<String[]>();
//        codicelinea = "A";
//
//        for (int i = 0; i < scoreList.size(); i++) {
//            String[] fermata = scoreList.get(i);
//            if (fermata[0].equals("1")) fermateList.add(fermata);
//        }
//
//        startService(new Intent(this, FermateService.class));
//        //stopService(new Intent(this, FermateService.class));
//
////        for (int i = 0; i < scoreList.size(); i++) {
////            String[] fermata = scoreList.get(i);
////            if (fermata[0].equals(codicelinea)) fermateList.add(fermata);
////        }
////
////
////        for(String[] scoreData:fermateList ) {
////            itemArrayAdapter.add(scoreData);
////        }
//    }
//}