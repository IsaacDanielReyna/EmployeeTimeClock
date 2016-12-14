package com.isaacreyna.employeetimeclock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.isaacreyna.employeetimeclock.interfaces.Service;
import com.isaacreyna.employeetimeclock.models.Company.Result;
import com.isaacreyna.employeetimeclock.models.Register;
import com.isaacreyna.employeetimeclock.models.User;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    private SharedPreferences Settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //Setup Button Listeners
        Button button = (Button) findViewById(R.id.button_register);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Validate();
            }
        });
        Settings = getApplicationContext().getSharedPreferences("Settings", MODE_PRIVATE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    public void Validate() {
        Boolean error = false;
        EditText username = (EditText) findViewById(R.id.username);
        EditText email = (EditText) findViewById(R.id.email);
        EditText password1 = (EditText) findViewById(R.id.password1);
        EditText password2 = (EditText) findViewById(R.id.password2);

        if (username.getText().toString().isEmpty()) {
            username.setError("Required");
            error = true;
        }

        if (email.getText().toString().isEmpty()) {
            email.setError("Required");
            error = true;
        }

        if (password1.getText().toString().isEmpty()) {
            password1.setError("Required");
            error = true;
        }

        if (password2.getText().toString().isEmpty()) {
            password2.setError("Required");
            error = true;
        }

        if (!error) {
            Log.i("loginAPI", username.getText() + ", " + password1.getText()  + ", "  + password2.getText() + ", "  + email);
            Register("", username.getText().toString(), password1.getText().toString(), password2.getText().toString(), email.getText().toString());
        }

    }

    public void Register(String token, final String username, String password1, String password2, String email){
        HashMap<String, String> fields = new HashMap<String, String>();
        fields.put("option","user");
        fields.put("task","register");
        fields.put("username", username);
        fields.put("password1", password1);
        fields.put("password2", password2);
        fields.put("email", email);

        Service.Factory.getInstance().register(fields).enqueue((new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if (response.body().result){
                    User user = new User();
                    user.username = username;
                    user.token = response.body().token;
                    SharedPreferences.Editor editor = Settings.edit();
                    String json = new Gson().toJson(user);
                    editor.putString("user", json);
                    editor.putBoolean("IsLoggedIn", true);
                    editor.commit();
                    StartMainActivity(user);
                }else{
                    Toast.makeText(RegistrationActivity.this, response.body().messages.toString(), Toast.LENGTH_LONG).show();
                }

                Log.i("registerAPI", "Message: " + response.body().messages.toString());
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Log.i("API", "onFailure");
            }
        }));
    }

    public void StartMainActivity(User u)
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        //finish();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
