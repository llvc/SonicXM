package llvc.sonicxm.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import llvc.sonicxm.R;
import llvc.sonicxm.activity.main.MainActivity;
import llvc.sonicxm.activity.signup.SignupActivity;
import llvc.sonicxm.model.Config;


public class LoginActivity extends Activity implements View.OnClickListener {

    //signin layout
    private LinearLayout signinLayout;
    private TextView signinTextView;
    private ImageButton skipSigninButton;
    private TextView signupTextView;

    //welcome layout
    private LinearLayout welcomeLayout;
    private TextView switchTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        initView();
        setupAction();

        showSigninView();
    }

    public void initView() {
        //signin
        signinLayout = (LinearLayout) findViewById(R.id.signinLayout);
        signinTextView = (TextView) findViewById(R.id.signinButtonView);
        skipSigninButton = (ImageButton) findViewById(R.id.skipSigninButton);
        signupTextView = (TextView) findViewById(R.id.signupTextView);

        //welcome
        welcomeLayout = (LinearLayout) findViewById(R.id.welcomeLayout);
        switchTextView = (TextView) findViewById(R.id.switchTextView);
    }

    public void setupAction() {
        //signin
        signinTextView.setOnClickListener(this);
        skipSigninButton.setOnClickListener(this);
        signupTextView.setOnClickListener(this);

        //welcome
        switchTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == signinTextView) {
            Intent i = new Intent(getBaseContext(), MainActivity.class);
            i.putExtra(Config.VIEW, Config.DASHBOARD);
            startActivity(i);

            finish();
        } else if (v == skipSigninButton) {
            showWelcomeView();
        } else if (v == switchTextView) {
            showSigninView();
        } else if (v == signupTextView) {
            Intent i = new Intent(getBaseContext(), SignupActivity.class);
            startActivity(i);

            finish();
        }
    }

    private void showSigninView() {
        signinTextView.setText(R.string.login);
        signinLayout.setVisibility(View.VISIBLE);
        welcomeLayout.setVisibility(View.GONE);
    }

    private void showWelcomeView() {
        signinTextView.setText("Continue as John Smith");

        signinLayout.setVisibility(View.GONE);
        welcomeLayout.setVisibility(View.VISIBLE);
    }
}
