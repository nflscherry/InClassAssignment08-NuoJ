package com.example.android.inclassassignment08_nuoj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myReference;
    EditText keyField;
    EditText valueField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        keyField = findViewById(R.id.key_Text);
        valueField = findViewById(R.id.value_Text);
        database = FirebaseDatabase.getInstance();
    }
    public void writeToCloud(View view) {
        myReference = database.getReference(keyField.getText().toString());
        myReference.setValue(valueField.getText().toString());
    }

    public void readFromCloud(View view){
        myReference = database.getReference(keyField.getText().toString());
        myReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String loadedData = dataSnapshot.getValue(String.class);
                    valueField.setText(loadedData);
                }
                else{
                    valueField.setText(null);
                    Toast.makeText(MainActivity.this, "Cannot find the key!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Error loading Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
