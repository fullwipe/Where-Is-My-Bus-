//package fullwipe.ciriitc.whereismybus;
//
//import java.util.HashMap;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.SimpleCursorAdapter;
//import android.widget.TextView;
//
//public class Database_CursorAdapter extends SimpleCursorAdapter {
//
//	private HashMap<String, String> 	headers;
//	private HashMap<String, Integer> 	headersIndex = new HashMap<String, Integer>();
//	private Cursor 						cursor;
//	private int 						fieldIndex;
//	private int 						headerId;
//	/**
//	 * @param headers 	: 	hashmap che contiene come chiavi i possibili valori del campo da usare per
//	 * 						la suddivisione (V,F,A,C per questo tutorial) e come value la descrizione da
//	 * 						visualizzare nell'header.
//	 * @param fieldName	: 	nome del campo presente nel cursore da usare per la suddivisione
//	 * 						(TYPE per questo tutorial)
//	 * @param headerId	: 	id della TextView del custom layout utilizzato per la lista
//	 * 						(R.id.simple_header per questo tutorial)
//	 */
//	public Database_CursorAdapter(Context arg0, int arg1, Cursor cursor, String[] arg3, int[] arg4, HashMap<String, String> headers, String fieldName, int headerId) {
//		super(arg0, arg1, cursor, arg3, arg4);
//		this.headers 	= headers;
//		this.cursor		= cursor;
//		this.fieldIndex = cursor.getColumnIndex(fieldName); // Ottengo l'indice del campo del cursore
//		this.headerId	= headerId;
//	}
//
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		cursor.moveToPosition(position);
//		/**
//		 * L'hashmap headersIndex viene valorizzato con la coppia
//		 * 	chiave/valore "tipo/posizione header da visualizzare"
//		 * se il tipo non � presente come chiave nell'hashmap significa che stiamo incontrando il primo
//		 * oggetto di questa categoria e che a questa posizione � necessario rendere visibile il titolo.
//		 * Per tutti gli altri oggetti della stessa categoria la condizione sar� false e il titolo non verr�
//		 * visualizzato.
//		 * Perch� questa logica funzioni correttamente il cursore deve essere ordinato per categoria.
//		 **/
//		if (!headersIndex.containsKey(cursor.getString(fieldIndex))) {
//			headersIndex.put(cursor.getString(fieldIndex), position);
//		}
//		View 		view 		= super.getView(position, convertView, parent);
//		// Ottengo la TestView del titolo
//		TextView 	textView 	= (TextView) view.findViewById(headerId);
//		/**
//		 * Se questa posizione � contenuta nell'HashMap headersIndex il titolo deve essere visibile
//		 */
//		if (headersIndex.containsValue(position)) {
//			// Il titolo viene reso visibile
//			textView.setVisibility(View.VISIBLE);
//			// Viene reperita la descrizione del titolo e settata nella TextView
//			textView.setText(headers.get(cursor.getString(fieldIndex)));
//		} else {
//			// Il titolo viene reso nascosto
//			textView.setVisibility(View.GONE);
//			// Rest del titolo
//			textView.setText("");
//		}
//		return view;
//	}
//
//	/*
//	 * Il metodo refreshHeaders va richiamato nel caso in cui vengano eliminati o aggiunti oggetti
//	 * alla lista, in quanto svuotanto l'hashMap si richiede un nuovo calcolo della posizione dei titoli
//	 */
//	public void refreshHeaders() {
//		headersIndex = new HashMap<String, Integer>();
//	}
//
//
//}
