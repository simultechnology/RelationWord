package relation.word;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	/** 入力ワード */
	private String word;
	/** 表示リスト */
	private ListView listView;
	/** 検索単語を履歴として保持するリスト */
	private List<String> searchHistoryList = new ArrayList<String>();
	
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
				// 履歴に保持する
				if (searchHistoryList.size() == 0) {
					searchHistoryList.add(word);
				}
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
        
        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (searchHistoryList.size() > 0) {
					String backWord = searchHistoryList.remove(searchHistoryList.size() - 1);
					if (word != null && word.equals(backWord) && searchHistoryList.size() > 0) {
						backWord = searchHistoryList.remove(searchHistoryList.size() - 1);
					}
					inputForm.setText(backWord);
				}
			}
		});
            
	    Button searchButton = (Button) findViewById(R.id.google_search_button);
	    searchButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String url = String.format("http://www.google.co.jp/?q=%s", word);
				Uri uri = Uri.parse(url);
				Intent intent = new Intent(Intent.ACTION_VIEW,uri);
				startActivity(intent);
			}
		});
    }
    
    public String getWord() {
    	return this.word;
    }
    
    public ListView getListView() {
    	return this.listView;
    }
    
    public void setWordSearchHistory(String word) {
		this.searchHistoryList.add(word);
	}
    
}