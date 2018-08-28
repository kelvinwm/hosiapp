package knh.or.ke.hospital.content;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import knh.or.ke.hospital.MyBookings;
import knh.or.ke.hospital.R;
import knh.or.ke.hospital.Setters.Chosenclinic;

public class BookClinic extends AppCompatActivity {

    Button submitchosenclinic;
    EditText chosenclinic,clinicdate,clinictime;
    private DatabaseReference dbaseref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_clinic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbaseref= FirebaseDatabase.getInstance().getReference().child("Bookclinics");
        chosenclinic=findViewById(R.id.chosenclinic);
        clinicdate=findViewById(R.id.clinicdate);
        clinictime=findViewById(R.id.clinictime);
        submitchosenclinic=findViewById(R.id.submitchosenclinic);

        submitchosenclinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clinicname=chosenclinic.getText().toString();
                String clinicdat=clinicdate.getText().toString();
                String clinictim=clinictime.getText().toString();
                final long timestamp= System.currentTimeMillis();
                String keytouse=String.valueOf(timestamp);

                /// check for intenet connection as well as display alert dialog to show user booking was successful....
                Chosenclinic newSpec= new Chosenclinic(clinicname.toString(),clinicdat.toString(),clinictim.toString(),keytouse);
                dbaseref.child(keytouse).setValue(newSpec);
                Intent mmybookings= new Intent(BookClinic.this,MyBookings.class);
                startActivity(mmybookings);
                chosenclinic.setText("");
                clinicdate.setText("");
                clinictime.setText("");
                finish();
            }
        });


    }

}
