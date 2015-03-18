package bsd.gradebook;

import android.app.Application;
import android.content.SharedPreferences;

import com.securepreferences.SecurePreferences;

public class ApplicationWrapper extends Application {

    private static ApplicationWrapper instance;
    public static ApplicationWrapper getInstance() { return instance; }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    private static SharedPreferences sharedPrefs;
    public SharedPreferences getSharedPrefs() {
        if (sharedPrefs == null)
            sharedPrefs = new SecurePreferences(this);
        return sharedPrefs;
    }
}
