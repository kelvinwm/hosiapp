package knh.or.ke.hospital;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import knh.or.ke.hospital.Adapters.ListclinicsAdapter;
import knh.or.ke.hospital.Setters.ClinicList;

public class Clinicslist extends AppCompatActivity {

    List<ClinicList> listofclinics;
    private DatabaseReference dbaseref;
    ProgressBar listloader;
    RecyclerView recyclerClinicList;
    ListclinicsAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinicslist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listofclinics = new ArrayList<>();
        listAdapter = new ListclinicsAdapter(Clinicslist.this, listofclinics);
        recyclerClinicList =findViewById(R.id.recyclerClinicList);

        recyclerClinicList.setHasFixedSize(true);
        recyclerClinicList.setLayoutManager(new LinearLayoutManager(this));

        dbaseref= FirebaseDatabase.getInstance().getReference().child("Listofclinics");
        listloader = findViewById(R.id.listloader);
        recyclerClinicList.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerClinicList, new CustomItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //to book clicked clinic
                final ClinicList clickedclinic=listofclinics.get(position);
                Intent clinicavailability= new Intent(Clinicslist.this,Clinicavailabity.class);
                clinicavailability.putExtra("clinicname", clickedclinic.getClinicname());
                startActivity(clinicavailability);
                finish();
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
                                        "Monday to Thursday, Fridays at Rahimtula"));
                        listofclinics.add(
                                new ClinicList(
                                        "ENT",
                                        "Emergency Treatment"));
                        listofclinics.add(
                                new ClinicList(
                                        "Diabetes",
                                        "Diabeties clinics"));
                        listofclinics.add(
                                new ClinicList(
                                        "Renal",
                                        "Renal clinics"));
//
//                        listAdapter.notifyDataSetChanged();
//                    }
//                }

                listloader.setVisibility(View.INVISIBLE);
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//                Toast.makeText(Clinicslist.this,"error updating",Toast.LENGTH_LONG).show();
//            }
//        });

        recyclerClinicList.setAdapter(listAdapter);
    }
    
}
