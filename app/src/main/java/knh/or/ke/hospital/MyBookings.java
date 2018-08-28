package knh.or.ke.hospital;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import knh.or.ke.hospital.Adapters.BookedclinicAdapter;
import knh.or.ke.hospital.Setters.Chosenclinic;
import knh.or.ke.hospital.content.Campigns;
import knh.or.ke.hospital.content.Direction;
import knh.or.ke.hospital.content.HealthTips;

public class MyBookings extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    List<Chosenclinic> clinicList;
    private DatabaseReference dbaseref;
    ProgressBar clincloader;
    RecyclerView recyclerClinicView;
    BookedclinicAdapter clinicadpter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.booknewclinicfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cliniclist=new Intent(MyBookings.this,Clinicslist.class);
                startActivity(cliniclist);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // initialization

        clinicList = new ArrayList<>();
        clinicadpter = new BookedclinicAdapter(MyBookings.this, clinicList);
        recyclerClinicView =findViewById(R.id.recyclerClinicView);

        recyclerClinicView.setHasFixedSize(true);
        recyclerClinicView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        dbaseref= FirebaseDatabase.getInstance().getReference().child("availableclinics").child("Patients").child(currentFirebaseUser.getUid().toString());
        clincloader = findViewById(R.id.clinicloader);

       fetchData();
    }

    private void fetchData() {
//
        dbaseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    clinicList.clear();

                    for(DataSnapshot postSnapShot:dataSnapshot.getChildren())
                    {
                        Chosenclinic getClinic=postSnapShot.getValue(Chosenclinic.class);
                        clinicList.add(
                                new Chosenclinic(
                                        getClinic.getClinicname().toString(),
                                        getClinic.getClinicdate().toString(),
                                        getClinic.getClinictime().toString(),
                                        getClinic.getKeytouse().toString()));
                        clinicadpter.notifyDataSetChanged();
                    }
                }
                clincloader.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(MyBookings.this,"error updating",Toast.LENGTH_LONG).show();
            }
        });

        recyclerClinicView.setAdapter(clinicadpter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_bookings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent cliniclist=new Intent(MyBookings.this,Clinicslist.class);
            startActivity(cliniclist);
            return true;
        }if (id == R.id.signout) {
            AuthUI.getInstance()
                    .signOut(MyBookings.this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                Intent launchac=new Intent(MyBookings.this,MainActivity.class);
                                startActivity(launchac);
                                finish();
                            }else{

                                Toast.makeText(MyBookings.this,"Logout Failed",Toast.LENGTH_LONG).show();
                            }

                        }
                    });

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add) {
            // Handle the camera action4\
//            Intent sampledata=new Intent(MyBookings.this,SampleData.class);
//            startActivity(sampledata);
            Intent cliniclist=new Intent(MyBookings.this,Clinicslist.class);
            startActivity(cliniclist);
        } else if (id == R.id.nav_tips) {
            Intent healthtips=new Intent(MyBookings.this,HealthTips.class);
            startActivity(healthtips);

        } else if (id == R.id.nav_charter) {

        } else if (id == R.id.nav_campaign) {

            Intent campigns=new Intent(MyBookings.this,Campigns.class);
            startActivity(campigns);
        } else if (id == R.id.nav_chat) {

        } else if (id == R.id.nav_call) {

        } else if (id == R.id.nav_direction) {

            Intent direction=new Intent(MyBookings.this,Direction.class);
            startActivity(direction);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
