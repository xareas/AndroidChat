package ni.gob.marena.androidchat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.edittxtEmail)
    EditText inputEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnSignIn)
    public void handleSignIn(){
        Log.e("androchat se dio click",inputEmail.getText().toString());
        Toast.makeText(getApplicationContext(),inputEmail.getText().toString(),Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btnSignUp)
    public void  handleSignUp(){
        Log.e("androchat se dio click",inputEmail.getText().toString());
        Toast.makeText(getApplicationContext(),inputEmail.getText().toString(),Toast.LENGTH_SHORT).show();
    }
}
