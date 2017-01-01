package tw.lan.myfireauthemailpass;

import android.content.Intent;
import android.graphics.LinearGradient;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("lan", "onAuthStateChanged:signed_in:" + user.getUid());
                    Toast.makeText(MainActivity.this, "自動登入",
                            Toast.LENGTH_SHORT).show();
                }else {
                    // User is signed out
                    startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), 1);
                    Log.d("lan", "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    @Override
    protected void onStart() {
        Log.d("lan", "onStart");
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lan", "onStop");
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAuth.signOut();
    }

    public void logout(View v) {
        if (mAuth != null) {
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Log.d("lan", String.valueOf(RESULT_OK));
            if (requestCode == RESULT_OK) {
                Toast.makeText(MainActivity.this, "登入成功",
                        Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(MainActivity.this, "登入失敗",
                        Toast.LENGTH_SHORT).show();
//                finish();
            }
        }
    }
}
