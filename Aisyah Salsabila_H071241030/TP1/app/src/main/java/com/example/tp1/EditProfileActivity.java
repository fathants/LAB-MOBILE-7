package com.example.tp1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    EditText editName, editUsername, editBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editName = findViewById(R.id.editName);
        editUsername = findViewById(R.id.editUsername);
        editBio = findViewById(R.id.editBio);
    }

    public void saveData(View view){

        String nama = editName.getText().toString();
        String username = editUsername.getText().toString();
        String bio = editBio.getText().toString();

        Intent intent = new Intent();
        intent.putExtra("name", nama);
        intent.putExtra("username", username);
        intent.putExtra("bio", bio);

        setResult(RESULT_OK, intent);
        finish();
    }

    public void goBack(View view){
        finish();
    }
}