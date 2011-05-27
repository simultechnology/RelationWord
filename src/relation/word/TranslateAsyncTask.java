package relation.word;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlrpc.android.XMLRPCClient;
import org.xmlrpc.android.XMLRPCException;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Base64;
import android.widget.ArrayAdapter;

public class TranslateAsyncTask extends AsyncTask<String, String, Void> {

	/** バックグラウンドスレッドとUIスレッドで応答するためのHandler */
	private Handler handler = new Handler();
	
    private static final String API_URL = "http://d.hatena.ne.jp/xmlrpc";
    private static final String API_METHOD_NAME = "hatena.getSimilarWord";
    //private static final String[] WORD_LIST = {"android"};

	MainActivity activity;

	public TranslateAsyncTask(MainActivity activity) {
		this.activity = activity;
	}

	@Override
	protected Void doInBackground(String... params) {
		System.out.println("doInBackground start!");



		XMLRPCClient client = new XMLRPCClient(API_URL);
		Object result = null;
		
		Map param = new HashMap();
		
        param.put("wordlist", activity.getWord());
		try {
			result = (Object) client.call(API_METHOD_NAME, param);
		} catch (XMLRPCException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		List<String> list = new ArrayList<String>();
		
	            if (result != null && result instanceof Map) {
	            	
	        		System.out.println(list.size());
	            	
	            	Object[] wordlist = (Object[]) ((Map) result).get("wordlist");

	                for (Object word : wordlist) {
	                	
	                	String newWord = ((Map) word).get("word").toString();
	                	System.out.println(newWord);
	                    list.add(newWord);

	                }

	            }
		
			final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					activity, R.layout.row, R.id.inner_text,
					list);

			
				handler.post(new Runnable() {
					@Override
					public void run() {
						activity.getListView().setAdapter(adapter);
					}
				});
		// while (true) {
		// if (tm.getTweet() != null) {
		// //Handlerに、UIスレッドへの値設定処理をPOSTする。
		// handler.post(new Runnable() {
		// public void run() {
		// if (list.size() > 15) {
		// list.remove(0);
		// }
		//
		// if (list.size() == 0) {
		// list.add(tm.getTweet());
		// }
		// else if (list.get(list.size() - 1) != null && !list.get(list.size() -
		// 1).equals(tm.getTweet())) {
		// list.add(tm.getTweet());
		// }
		//
		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
		// R.layout.row, R.id.inner_text, list);
		// listView.setAdapter(adapter);
		// }
		// });
		// }
		// }
		return null;
	}
}
