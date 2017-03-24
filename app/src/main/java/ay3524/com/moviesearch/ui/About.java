package ay3524.com.moviesearch.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.vansuita.materialabout.builder.AboutBuilder;
import com.vansuita.materialabout.views.AboutView;

import ay3524.com.moviesearch.R;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame);

        AboutView view = AboutBuilder.with(this)
                .setPhoto(R.mipmap.profile_picture)
                .setCover(R.mipmap.profile_cover)
                .setName(getString(R.string.name))
                .setSubTitle(getString(R.string.desig))
                .setBrief(getString(R.string.breif))
                .setAppIcon(R.mipmap.ic_launcher)
                .setAppName(R.string.app_name)
                .addGooglePlayStoreLink(getString(R.string.playstore_dev_id))
                .addGitHubLink(getString(R.string.github_id))
                .addEmailLink(getString(R.string.email))
                .setVersionNameAsAppSubTitle()
                .setWrapScrollView(true)
                .setLinksAnimated(true)
                .setShowAsCard(true)
                .build();
        frameLayout.addView(view);

    }
}
