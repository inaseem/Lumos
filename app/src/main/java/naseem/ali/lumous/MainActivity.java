package naseem.ali.lumous;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView videoRecyclerView;
    private EditText toSearch;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private FloatingActionButton fab;
    private LanguageAdapter adapter;
    private VideoAdapter videoAdapter;
    private String numbers[]={"zero","one","two","three","four","five","six","seven","eight","nine"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SL.getInstance(this).initialize();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo32dp);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        createMe();
        /*
        AnimationDrawable animation = new AnimationDrawable();
animation.addFrame(getResources().getDrawable(R.drawable.image1), 10);
animation.addFrame(getResources().getDrawable(R.drawable.image2), 50);
animation.addFrame(getResources().getDrawable(R.drawable.image3), 30);
animation.setOneShot(false);

ImageView imageAnim =  (ImageView) findViewById(R.id.imageView);
imageAnim.setImageDrawable(animation);

// start the animation!
animation.start();
        * File imgFile = new  File("/sdcard/Images/test_image.jpg");

if(imgFile.exists()){

    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

    ImageView myImage = (ImageView) findViewById(R.id.imageviewTest);

    myImage.setImageBitmap(myBitmap);

}
        * */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Intent intent1=new Intent(MainActivity.this,AboutActivity.class);
                startActivity(intent1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createMe(){
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        videoRecyclerView=(RecyclerView)findViewById(R.id.videoRecyclerView);
        toSearch=(EditText)findViewById(R.id.toSearch);
        fab=(FloatingActionButton)findViewById(R.id.floatingActionButton);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager verticalLayoutManager=new LinearLayoutManager(this);
        verticalLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        videoRecyclerView.setLayoutManager(verticalLayoutManager);
        recyclerView.setLayoutManager(layoutManager);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
        toSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                int result = actionId & EditorInfo.IME_MASK_ACTION;
                switch(result) {
                    case EditorInfo.IME_ACTION_DONE:
                        // done stuff
                        showData(toSearch.getText().toString());
                        break;
                    case EditorInfo.IME_ACTION_NEXT:
                        // next stuff
                        showData(toSearch.getText().toString());
                        break;
                }
                return true;
            }
        });
        toSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().contains("\n")){
                    showData(s.toString().substring(0,s.length()-1));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak Something");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),"Speech Not Supported",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    showData(result.get(0));
                }
                break;
            }

        }
    }

    private void showData(String input){
        toSearch.setText(input);
        adapter=new LanguageAdapter(this,getLinks(input));
        recyclerView.setAdapter(adapter);
        videoAdapter=new VideoAdapter(this,getVideos(getModifiedString(input)));
        videoRecyclerView.setAdapter(videoAdapter);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(toSearch.getWindowToken(), 0);
    }

    private ArrayList<Vocab> getLinks(String sent){
        ArrayList<Vocab> vocab=new ArrayList<>();
        for(String word:getModifiedString(sent).split(" ")){
            if(word.trim().length()>0) {
                String link = SL.getInstance(this).getVocabImage(word);
                if (link == null) {
                    for (char c : word.toCharArray()) {
                            vocab.add(new Vocab(Character.toString(c),SL.getInstance(this).getVocabImage(Character.toString(c))));
                    }
                } else {
                    vocab.add(new Vocab(word,link));
                }
            }
        }
        return vocab;
    }

    public String getModifiedString(String str){
        str=str.toLowerCase();
        StringBuilder sb=new StringBuilder();
        for(Character c:str.toCharArray()){
            if(Character.isAlphabetic(c)){
                sb.append(c);
            }
            else if(Character.isDigit(c)){
                sb.append(numbers[Integer.parseInt(Character.toString(c))]);
            }
            else if(Character.isSpaceChar(c)){
                sb.append(" ");
            }
            else{
                sb.append("");
            }
        }
        return sb.toString();
    }

    public ArrayList<Vocab> getVideos(String text){
        ArrayList<Vocab> videos=new ArrayList<>();
        for(String word:text.split(" ")){
            if(word.trim().length()>0){
                if(SL.getInstance(this).getVideoExample(word)!=null){
                    videos.add(new Vocab(word, SL.getInstance(this).getVideoExample(word)));
                }
            }
        }
        return videos;
    }

    protected class Vocab{
        String word;
        String link;
        Vocab(String word,String link){
            this.word=word;
            this.link=link;
        }

        public String getLink() {
            return link;
        }

        public String getWord() {
            return word;
        }
    }
}
