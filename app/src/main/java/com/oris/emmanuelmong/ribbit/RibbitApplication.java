package com.oris.emmanuelmong.ribbit;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by emmanuelmong on 1/22/15.
 */
public class RibbitApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "GhjUkLlL596WIv1JzGBza9y5jdd8L5ZULAtNE2No", "NDPAVid3frEOktjNWZbAt22J0kiO8eK0AYHzRgMS");

    }
}
