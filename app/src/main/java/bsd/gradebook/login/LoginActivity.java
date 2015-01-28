package bsd.gradebook.login;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import bsd.gradebook.ApplicationWrapper;
import bsd.gradebook.R;
import bsd.gradebook.gradebook.GradebookActivity;
import bsd.gradebook.util.Constants;

public class LoginActivity extends ActionBarActivity {

    private UserLoginTask mAuthTask = null;

    private EditText mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_send_feedback) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "skraman1999@gmail.com", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Gradebook App Feedback");
            startActivity(Intent.createChooser(emailIntent, "Send feedback"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final CheckBox mAutoLoginView = (CheckBox) findViewById(R.id.autologin);
        mAutoLoginView.setChecked(ApplicationWrapper.getInstance().getSharedPrefs().getBoolean(Constants.AUTOLOGIN, false));

        mUsernameView = (EditText) findViewById(R.id.email);
        mUsernameView.setText(ApplicationWrapper.getInstance().getSharedPrefs().getString(Constants.USERNAME, ""));

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setText(ApplicationWrapper.getInstance().getSharedPrefs().getString(Constants.PASSWORD, ""));
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin(mAutoLoginView.isChecked());
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin(mAutoLoginView.isChecked());
            }
        });

        mProgressView = findViewById(R.id.login_progress);

        if (ApplicationWrapper.getInstance().getSharedPrefs().getBoolean(Constants.AUTOLOGIN, false))
            attemptLogin(true);
    }

    public void attemptLogin(boolean autologin) {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
            showProgress(false);
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(username, password, autologin);
            mAuthTask.execute((Void) null);
        }
    }

    public void showProgress(final boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Object> {

        private final String mUsername;
        private final String mPassword;
        private final boolean mAutologin;

        UserLoginTask(String username, String password, boolean autologin) {
            mUsername = username;
            mPassword = password;
            mAutologin = autologin;
        }

        @Override
        protected LoginResult doInBackground(Void... params) {
            ConnectionManager.Response state = ConnectionManager.makeConnection(LoginActivity.this, "https://gradebook-web-api.herokuapp.com/?username=" + mUsername + "&password=" + mPassword);

            switch (state) {
                case BAD_CREDS:
                    return new LoginResult(false, getResources().getString(R.string.error_bad_creds));
                case SUCCESS:
                    ApplicationWrapper.getInstance().getSharedPrefs().edit()
                            .putString(Constants.USERNAME, mUsername)
                            .putString(Constants.PASSWORD, mPassword)
                            .putBoolean(Constants.AUTOLOGIN, mAutologin).commit();
                    return new LoginResult(true, null);
                case NETWORK_FAILURE:
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, R.string.error_network_failure, Toast.LENGTH_SHORT).show();
                        }
                    });
                    return new LoginResult(false, getResources().getString(R.string.error_network_failure));
                default:
                    return new LoginResult(false, getResources().getString(R.string.error_unknown));
            }
        }

        @Override
        protected void onPostExecute(final Object rawResult) {
            mAuthTask = null;
            showProgress(false);

            LoginResult result = (LoginResult) rawResult;
            if (result.success) {
                Intent intent = new Intent(LoginActivity.this, GradebookActivity.class);
                startActivity(intent);
            } else {
                mPasswordView.setError(getString(R.string.error_bad_creds));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }

        class LoginResult {
            boolean success;
            String message;

            LoginResult(boolean success, String message) {
                this.success = success;
                this.message = message;
            }
        }
    }
}