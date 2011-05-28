package relation.word;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.xmlrpc.android.XMLRPCClient;
import org.xmlrpc.android.XMLRPCException;

import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class RelationSearchAsyncTask extends AsyncTask<String, String, Void> {

	/** バックグラウンドスレッドとUIスレッドで応答するためのHandler */
	private Handler handler = new Handler();

	/** はてなキーワード連想語APIのURL */
	private static final String API_URL = "http://d.hatena.ne.jp/xmlrpc";
	/** APIで使用するメソッド名 */
	private static final String API_METHOD_NAME = "hatena.getSimilarWord";

	private MainActivity activity;

	public RelationSearchAsyncTask(MainActivity activity) {
		this.activity = activity;
	}

	@Override
	protected Void doInBackground(String... params) {

		XMLRPCClient client = new XMLRPCClient(API_URL);
		Object result = null;
		
		String searchWord = activity.getWord();
		if (searchWord == null || searchWord.length() == 0) {
			return null;
		}
		
		// リクエストに含めるパラメータの設定
		Map<String, String> param = new HashMap<String, String>();
		param.put("wordlist", searchWord);
		try {
			// RPCをコールする
			result = (Object) client.call(API_METHOD_NAME, param);
		} catch (XMLRPCException e) {
			e.printStackTrace();
		}

		List<String> list = new ArrayList<String>();

		if (result != null && result instanceof Map) {
			// 連想語の取得
			Object[] arrayWord = (Object[]) ((Map) result).get("wordlist");
			
			for (Object word : arrayWord) {
				String newWord = ((Map) word).get("word").toString();
				list.add(newWord);
			}
		}
		
		// ListViewに表示するアダプタを作成
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
				R.layout.row, R.id.inner_text, randomOrder(list));

		handler.post(new Runnable() {
			@Override
			public void run() {
				ListView listView = activity.getListView();
				listView.setAdapter(adapter);
				listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> adapterView, View view,
							int position, long id) {
							LinearLayout linearView = (LinearLayout)view;
							TextView textview = (TextView) linearView.getChildAt(0);
							EditText inputForm = (EditText) activity.findViewById(R.id.input_form);
							inputForm.setText((String) textview.getText());
							inputForm.setFocusable(false);
							inputForm.setFocusableInTouchMode(true);
							
					}
				});
			}
		});
		return null;
	}
	
	/**
	 * 引数のリストの要素をランダムに並び替える
	 *
	 * @param list
	 */
    public static List<String> randomOrder(List<String> list) {

        List<String> tmpList = new ArrayList<String>();
        Random random = new Random();
        
        while (list.size() > 0) {
            int r = random.nextInt( list.size() );
            tmpList.add( list.remove(r) );
        }

        for (String str : tmpList) {
            list.add(str);
        }
        return list;
    }
}
