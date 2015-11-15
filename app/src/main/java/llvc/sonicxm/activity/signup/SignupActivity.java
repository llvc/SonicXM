package llvc.sonicxm.activity.signup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import llvc.sonicxm.R;
import llvc.sonicxm.activity.login.LoginActivity;
import llvc.sonicxm.activity.main.MainActivity;
import llvc.sonicxm.model.Config;

/**
 * Created by a1 on 11/7/2015.
 */
public class SignupActivity extends Activity implements View.OnClickListener {

    private ImageButton backButton;
    private TextView nextStepButton;
    private ImageButton closeButton;

    private ImageView firstRegStep;
    private ImageView secondRegStep;
    private ImageView thirdRegStep;

    private LinearLayout phoneRegistrationLayout;

    private LinearLayout nameAndMailRegistrationLayout;

    private LinearLayout photoRegistrationLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        initView();

        setupAction();

        showPhoneRegView();
    }

    private void initView() {
        backButton = (ImageButton) findViewById(R.id.backButton);
        nextStepButton = (TextView) findViewById(R.id.nextStepButton);
        closeButton = (ImageButton) findViewById(R.id.closeRegistrationButton);

        firstRegStep = (ImageView) findViewById(R.id.firstRegStep);
        secondRegStep = (ImageView) findViewById(R.id.secondRegStep);
        thirdRegStep = (ImageView) findViewById(R.id.thirdRegStep);

        phoneRegistrationLayout = (LinearLayout) findViewById(R.id.phoneRegistrationLayout);

        nameAndMailRegistrationLayout = (LinearLayout) findViewById(R.id.nameAndMailRegistrationLayout);

        photoRegistrationLayout = (LinearLayout) findViewById(R.id.photoRegistrationLayout);
    }

    private void setupAction() {
        backButton.setOnClickListener(this);
        nextStepButton.setOnClickListener(this);
        closeButton.setOnClickListener(this);
    }

    private void showPhoneRegView() {
        phoneRegistrationLayout.setVisibility(View.VISIBLE);
        nameAndMailRegistrationLayout.setVisibility(View.GONE);
        photoRegistrationLayout.setVisibility(View.GONE);

        nextStepButton.setText(R.string.next_step);

        firstRegStep.setBackgroundResource(R.drawable.blue_dot);
        secondRegStep.setBackgroundResource(R.drawable.white_dot);
        thirdRegStep.setBackgroundResource(R.drawable.white_dot);
    }

    private void showNameAndMailRegView() {
        phoneRegistrationLayout.setVisibility(View.GONE);
        nameAndMailRegistrationLayout.setVisibility(View.VISIBLE);
        photoRegistrationLayout.setVisibility(View.GONE);

        nextStepButton.setText(R.string.next_step);

        firstRegStep.setBackgroundResource(R.drawable.white_dot);
        secondRegStep.setBackgroundResource(R.drawable.blue_dot);
        thirdRegStep.setBackgroundResource(R.drawable.white_dot);
    }

    private void showPhotoRegView() {
        phoneRegistrationLayout.setVisibility(View.GONE);
        nameAndMailRegistrationLayout.setVisibility(View.GONE);
        photoRegistrationLayout.setVisibility(View.VISIBLE);

        nextStepButton.setText(R.string.create_account);

        firstRegStep.setBackgroundResource(R.drawable.white_dot);
        secondRegStep.setBackgroundResource(R.drawable.white_dot);
        thirdRegStep.setBackgroundResource(R.drawable.blue_dot);
    }

    @Override
    public void onClick(View v) {
        if (v == backButton) {
            if (phoneRegistrationLayout.getVisibility() == View.VISIBLE) {
                Intent i = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(i);

                finish();
            } else if (nameAndMailRegistrationLayout.getVisibility() == View.VISIBLE) {
                showPhoneRegView();
            } else if (photoRegistrationLayout.getVisibility() == View.VISIBLE) {
                showNameAndMailRegView();
            }
        } else if (v == nextStepButton) {
            if (phoneRegistrationLayout.getVisibility() == View.VISIBLE) {
                showNameAndMailRegView();
            } else if (nameAndMailRegistrationLayout.getVisibility() == View.VISIBLE) {
                showPhotoRegView();
            } else if (photoRegistrationLayout.getVisibility() == View.VISIBLE) {
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                i.putExtra(Config.VIEW, Config.DASHBOARD);
                startActivity(i);

                finish();
            }
        } else if (v == closeButton) {
            Intent i = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(i);

            finish();
        }
    }
}
