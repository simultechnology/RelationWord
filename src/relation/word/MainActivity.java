package relation.word;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
	
	private String word;
	private ListView listView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
		
        final EditText inputForm = (EditText) findViewById(R.id.input_form);
        
        listView = (ListView) findViewById(R.id.word_list);
        
        inputForm.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				word = inputForm.getText().toString();
				System.out.println("word.length()  : " + word.length());
				if (word.length() == 0) {
					listView.removeAllViewsInLayout();
				}
				else {
				
					try {
						TranslateAsyncTask task = new TranslateAsyncTask(MainActivity.this);
				        task.execute(null);
						//TransLateService.translateEnglish(word.toString());
					} catch (Exception e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}
				}
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO 自動生成されたメソッド・スタブ
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO 自動生成されたメソッド・スタブ
				
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