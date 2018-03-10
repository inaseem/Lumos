package naseem.ali.lumous;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutActivity extends AppCompatActivity {

    private LinearLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        container=(LinearLayout)findViewById(R.id.container);
        Element element=new Element();
        element.setTitle("Made With \u2764 In India");
        element.setGravity(Gravity.CENTER_HORIZONTAL);
        Element mitra=new Element("Shouvik Mitra",R.drawable.ic_person_black_24dp);
        mitra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.iamshouvikmitra.blogspot.com"));
                startActivity(browserIntent);
            }
        });
        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.logo2)
                .setDescription("An Android Application Which Is Designed To Help People Communicate With The Differently Abled, Thus Making This World A More Connected Place. ")
                .addGroup("Connect with us")
                .addEmail("thisismenaseem@gmail.com")
                .addWebsite("https://naseemali925.github.io/")
                //.addFacebook("the.medy")
                //.addTwitter("medyo80")
                //.addYoutube("UCdPQtdWIsg7_pi4mrRu46vA")
                //.addPlayStore("com.ideashower.readitlater.pro")
                .addGitHub("naseemali925/Lumos")
                .addGroup("Developers")
                .addItem(new Element("Naseem Ali",R.drawable.ic_person_black_24dp))
                .addItem(mitra)
                //.addInstagram("medyo80")
                .create();

        container.addView(aboutPage,0);
    }
}
