package gs.mygift.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import gs.mygift.R;
import gs.mygift.mainActivity.MainActivity;

public class Login extends AppCompatActivity {
    //For debugging
    private static final String TAG = "GiftDebug";
    /**
     * Google
     */
    //Google Sign in button
    private SignInButton btnGoogleSignin;
    //Google sign in constant for activity result
    private static final int RC_SIGN_IN = 13;
    /**
     * Facebook
     */
    //Facebook Login
    private LoginButton btnFacebookLogin;
    private CallbackManager callbackManager;
    private static final int FACEBOOK_RQ = 64206;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



    initLayout();
    initEvents();



    }

    private void initEvents() {
        btnGoogleSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            googleSignInFirebase();
            }
        });
        btnFacebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG,"Facebook:Cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG,"Facebook:Error" + error.getMessage());
            }
        });
    }



    private void initLayout() {
        btnGoogleSignin = (SignInButton) findViewById(R.id.btnGoogleSignin);
        btnFacebookLogin = (LoginButton) findViewById(R.id.btnFacebookLogin);
        btnFacebookLogin.setReadPermissions("email","public_profile","user_friends");
        callbackManager = CallbackManager.Factory.create();

    }

    /**
     * OnActivity result for facebook & google
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       //if everything went good...
       if (resultCode == RESULT_OK){
           //google sign in
           if (requestCode == RC_SIGN_IN){
               GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
               if (result.isSuccess()){
                   //We take account from result
                   GoogleSignInAccount account = result.getSignInAccount();
                   fireBaseResultWithGoogle(account);
               }
               //Facebook Sign In
           }else if (requestCode == FACEBOOK_RQ){
               callbackManager.onActivityResult(requestCode,resultCode,data);

           }

       }
    }

    /**
     * Facebook Login
     */

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG,"HandleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult>   task) {

                        if (task.isSuccessful()){
                            //we have a current User
                            //Move to main Activity
                            startActivity(new Intent(Login.this,MainActivity.class));
                        }else{
                            Log.d(TAG,"signInWithCredential" +  task.getException().getMessage());
                            Toast.makeText(Login.this, "Error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }



    /**
     * Google Sign in with firebase
     */
    private void googleSignInFirebase() {
        //google sign in option -> we request the Email details + the web application server
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_must_have_web_app))
                .requestEmail()
                .build();

        //we sign in with api client + proccessing the GSO we just created
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                    //if we have error with connection show something
                        if (!connectionResult.isSuccess()){
                            Toast.makeText(Login.this, "Error:" + connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
                            //Todo:replace the Toast with alert dialog
                        }
                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        //Intent to activity Result
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,RC_SIGN_IN);

    }

    /**
     * Proccessing account to FireBase after result is successful
     * if task successful we have a current user !!!
     * @param account this account is coming from onActivityResult
     */
    private void fireBaseResultWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //WE HAVE A SAVED USER IN FIREBASE
                            //We can do intent to the mainActivity
                            //We might save our users in realtime database
                            Toast.makeText(Login.this, "We have a user", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this, MainActivity.class));

                        }else{
                            Log.d("GuyDebug", task.getException().getMessage());
                            Toast.makeText(Login.this, "Error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

}
