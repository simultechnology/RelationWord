package relation.word;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	/** 入力ワード */
	private String word;
	/** 表示リスト */
	private ListView listView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        listView = (ListView) findViewById(R.id.word_list);
        final EditText inputForm = (EditText) findViewById(R.id.input_form);
        // 入力フォームに対してリスナーを設定
        inputForm.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// 入力フォームの値を取得入
				word = inputForm.getText().toString();
				try {
					// 非同期でAPI検索を呼出す
					RelationSearchAsyncTask task = new RelationSearchAsyncTask(MainActivity.this);
			        task.execute(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
		});
    }
    
    public String getWord() {
    	return this.word;
    }
    
    public ListView getListView() {
    	return this.listView;
    }
}