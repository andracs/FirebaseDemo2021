package dk.acsandras.firebasedemo1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText messageEditText;
    private Button button;

    private static final String TAG = "András";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageEditText = (EditText) findViewById(R.id.messageEditText);
        button = (Button) findViewById(R.id.button);

        // Write a message to the database
        // Husk at tilføje din unikke database URL fra Firebase Console til getInstance()
        FirebaseDatabase database = FirebaseDatabase.getInstance("SKRIV DIN EGEN URL HER");
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Hello, World!");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.setValue(messageEditText.getText().toString());
                //Log.d(TAG,"Besked er gemt i databasen: " + messageEditText.getText().toString());
            }
        });

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                messageEditText.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}