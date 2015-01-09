package bsd.gradebook;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ApplicationWrapper extends Application {

    private static ApplicationWrapper instance;
    public static ApplicationWrapper getInstance() { return instance; }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public SharedPreferences getSharedPrefs() {
        return PreferenceManager.getDefaultSharedPreferences(this);
    }
}
