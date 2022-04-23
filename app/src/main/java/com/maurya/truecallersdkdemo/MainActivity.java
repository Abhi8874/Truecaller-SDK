package com.maurya.truecallersdkdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.truecaller.android.sdk.ITrueCallback;
import com.truecaller.android.sdk.TrueError;
import com.truecaller.android.sdk.TrueProfile;
import com.truecaller.android.sdk.TruecallerSDK;
import com.truecaller.android.sdk.TruecallerSdkScope;

public class MainActivity extends AppCompatActivity {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TruecallerSdkScope trueScope = new TruecallerSdkScope.Builder(MainActivity.this, sdkCallback)
                        .consentMode(TruecallerSdkScope.CONSENT_MODE_BOTTOMSHEET)
                        .buttonColor(Color.YELLOW)
                        .buttonTextColor(Color.BLACK)
                        .loginTextPrefix(TruecallerSdkScope.LOGIN_TEXT_PREFIX_TO_GET_STARTED)
                        .loginTextSuffix(TruecallerSdkScope.LOGIN_TEXT_SUFFIX_PLEASE_VERIFY_MOBILE_NO)
                        .ctaTextPrefix(TruecallerSdkScope.CTA_TEXT_PREFIX_USE)
                        .buttonShapeOptions(TruecallerSdkScope.BUTTON_SHAPE_ROUNDED)
                        .privacyPolicyUrl("https://docs.truecaller.com/")
                        .termsOfServiceUrl("https://docs.truecaller.com/")
                        .footerType(TruecallerSdkScope.FOOTER_TYPE_NONE)
                        .consentTitleOption(TruecallerSdkScope.SDK_CONSENT_TITLE_LOG_IN)
                        .sdkOptions(TruecallerSdkScope.SDK_OPTION_WITHOUT_OTP)
                        .build();

                TruecallerSDK.init(trueScope);

                verify(button);
            }
        });
    }
    private final ITrueCallback sdkCallback = new ITrueCallback(){

        @Override
        public void onSuccessProfileShared(@NonNull TrueProfile trueProfile) {
            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
            intent.putExtra("name", trueProfile.firstName);
            startActivity(intent);

        }

        @Override
        public void onFailureProfileShared(@NonNull TrueError trueError) {
            Toast.makeText(MainActivity.this, ""+trueError.getErrorType(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVerificationRequired(TrueError trueError) {

        }
    };

    public void verify(View view){
        if (TruecallerSDK.getInstance().isUsable()){
            TruecallerSDK.getInstance().getUserProfile(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            TruecallerSDK.getInstance().onActivityResultObtained(MainActivity.this, requestCode, resultCode, data);
    }

}