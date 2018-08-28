package knh.or.ke.hospital.content;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import knh.or.ke.hospital.Adapters.DirectionAdapter;
import knh.or.ke.hospital.CustomItemClickListener;
import knh.or.ke.hospital.R;
import knh.or.ke.hospital.RecyclerTouchListener;
import knh.or.ke.hospital.Setters.ClinicList;

public class Direction extends AppCompatActivity {

    List<ClinicList> listofclinics;
    private DatabaseReference dbaseref;
    ProgressBar directionloader;
    RecyclerView recyclerdirection;
    DirectionAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listofclinics = new ArrayList<>();
        listAdapter = new DirectionAdapter(Direction.this, listofclinics);
        recyclerdirection =findViewById(R.id.recyclerdirection);

        recyclerdirection.setHasFixedSize(true);
        recyclerdirection.setLayoutManager(new LinearLayoutManager(this));

        dbaseref= FirebaseDatabase.getInstance().getReference().child("Listofclinics");
        directionloader = findViewById(R.id.directionloader);
        recyclerdirection.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerdirection, new CustomItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //to book clicked clinic
                final ClinicList clickedclinic = listofclinics.get(position);
                findDirection(clickedclinic.getClinicdescription());
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));


        fetchData();

    }

    private void fetchData() {

//        dbaseref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {

        // search for the other means of retrieval of data frm database
//                if(dataSnapshot.exists())
//                {
        listofclinics.clear();
//
//                    for(DataSnapshot postSnapShot:dataSnapshot.getChildren())
//                    {
        //ClinicList getClinic=postSnapShot.getValue(ClinicList.class);
        listofclinics.add(
                new ClinicList(
                        "Cancer Treatment Center",
                        "cancer+treatment+centre+Kenyatta+Hospital,+Nairobi"));
        listofclinics.add(
                new ClinicList(
                        "ENT",
                        "ENT+Kenyatta+Hospital, +Nairobi"));
        listofclinics.add(
                new ClinicList(
                        "Diabetes",
                        "Diabetic+Clinic+kenyatta+Hospital, +Nairobi+City"));
        listofclinics.add(
                new ClinicList(
                        "Renal",
                        "Renal+Unit+kenyatta+Hospital,+Nairobi"));
//
//                        listAdapter.notifyDataSetChanged();
//                    }
//                }

        directionloader.setVisibility(View.INVISIBLE);
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//                Toast.makeText(Clinicslist.this,"error updating",Toast.LENGTH_LONG).show();
//            }
//        });

        recyclerdirection.setAdapter(listAdapter);
    }

private void findDirection(String dirname){

    Uri gmmIntentUri = Uri.parse("google.navigation:q="+dirname);
    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
    mapIntent.setPackage("com.google.android.apps.maps");
    startActivity(mapIntent);
}

}
