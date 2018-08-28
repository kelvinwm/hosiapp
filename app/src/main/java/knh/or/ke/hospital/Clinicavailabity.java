package knh.or.ke.hospital;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import knh.or.ke.hospital.Adapters.ClinicavailabilityAdapter;
import knh.or.ke.hospital.Setters.Allbookings;
import knh.or.ke.hospital.Setters.Bookedtime;
import knh.or.ke.hospital.Setters.Chosenclinic;
import knh.or.ke.hospital.Setters.ClinicAvailability;

public class Clinicavailabity extends AppCompatActivity {

    String clinname,editthisclinickey,reskey,resname;
    public static  String  daytoday,todayDate;
    TextView clinicname,todaytxt,availabilty;
    ImageView prev,next;

    List<ClinicAvailability> availableclinics;
    private DatabaseReference dbaseref;
    ProgressBar loaderavailability;
    RecyclerView recyclerClinicavailability;
    ClinicavailabilityAdapter clinicavailabilityAdapter;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String CurrentDate = "dateKey";
    public static final String ClinicJina = "clinicname";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinicavailabity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialize dates staff here
        final Calendar c = Calendar.getInstance();
        final Calendar ctoday = Calendar.getInstance();
        final Calendar dayofweek = Calendar.getInstance();
        final SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        final SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        todayDate = df.format(ctoday.getTime());
        daytoday = sdf.format(c.getTime());

        // got sharred preferences
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        editor.clear();

        // initialize textviews and imageview for dates update
        todaytxt=findViewById(R.id.today);
        prev=findViewById(R.id.prev);
        next=findViewById(R.id.next);
        availabilty=findViewById(R.id.availabilty);
        todaytxt.setText("Today");
        prev.setVisibility(View.GONE);

        // bar to include date navigator
        Intent intent = getIntent();
        clinicname=findViewById(R.id.clinicname);
        availableclinics = new ArrayList<>();
        clinicavailabilityAdapter = new ClinicavailabilityAdapter(Clinicavailabity.this, availableclinics);
        recyclerClinicavailability =findViewById(R.id.recyclerClinicavailability);

        if(!(intent.getStringExtra("clinicname")==null)){
            clinname = intent.getStringExtra("clinicname");
            clinicname.setText(clinname);
        }else {
            clinname = intent.getStringExtra("editthisclinic");
            editthisclinickey = intent.getStringExtra("editthisclinickey");
            clinicname.setText(clinname);
        }

        //set shared preferrences
        editor.putString(CurrentDate, daytoday+" "+todayDate);
        editor.putString(ClinicJina, clinicname.getText().toString());
        editor.commit();
        //initailize recycleview
        recyclerClinicavailability.setHasFixedSize(true);
        recyclerClinicavailability.setLayoutManager(new LinearLayoutManager(this));


        dbaseref= FirebaseDatabase.getInstance().getReference().child("availableclinics");
        loaderavailability = findViewById(R.id.loaderavailability);
        recyclerClinicavailability.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerClinicavailability, new CustomItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //to book clicked clinic
                final ClinicAvailability clickedclinic = availableclinics.get(position);

                if (clickedclinic.getAvailability().equals("Booked")) {
                    return;
                }
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(Clinicavailabity.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(Clinicavailabity.this);
                    }
                    builder.setTitle("Book Clinic")
                            .setMessage("Do want you to book " + clinname+" on " + todaytxt.getText().toString() + " at " + clickedclinic.getAvailtime() + " ?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue
                                    Bookedtime bookedtime;
                                    Chosenclinic chosenclinic;
                                    Allbookings allbookings;
                                    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                                    String keyval = dbaseref.push().getKey();
                                    if (todaytxt.getText().toString().equals("Today")) {
                                        bookedtime = new Bookedtime(clickedclinic.getKeyvalue(), daytoday + " " + todayDate);
                                        chosenclinic = new Chosenclinic(clinicname.getText().toString(), daytoday + " " + todayDate,clickedclinic.getAvailtime(),keyval);
                                        allbookings = new Allbookings(clinicname.getText().toString(), daytoday + " " + todayDate , clickedclinic.getAvailtime(),currentFirebaseUser.getDisplayName(),"Pending");
                                    } else {
                                        bookedtime = new Bookedtime(clickedclinic.getKeyvalue(), todaytxt.getText().toString());
                                        chosenclinic = new Chosenclinic(clinicname.getText().toString(), todaytxt.getText().toString(),clickedclinic.getAvailtime(),keyval);
                                        allbookings = new Allbookings(clinicname.getText().toString(), todaytxt.getText().toString() , clickedclinic.getAvailtime(),currentFirebaseUser.getDisplayName(),"Pending");

                                    }
                                    //patient specific bookings
                                    dbaseref.child("Patients").child(currentFirebaseUser.getUid().toString()).child(keyval).setValue(chosenclinic);
                                    //all times booked
                                    dbaseref.child("Bookedtimes").child(clinicname.getText().toString()).child(keyval).setValue(bookedtime);
                                    dbaseref.child("Allpatients").child(keyval).setValue(allbookings);
                                    finish();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        /// GET PREV AND NEXT DATES

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.add(Calendar.DATE, -1);
                dayofweek.set(Calendar.WEEK_OF_MONTH, -1);
                String prevdate = df.format(c.getTime());
                String day = sdf.format(c.getTime());
                Date todaydate= null;
                Date predates= null;
                try {
                    todaydate = df.parse(todayDate);
                    predates = df.parse(prevdate);

                    if(todaydate.equals(predates)|| predates.before(todaydate)) {

                        Log.v("PREVIOUS DATE : ", prevdate);
                        todaytxt.setText("Today");
                        editor.clear();
                        editor.putString(CurrentDate, daytoday+" "+todayDate);
                        editor.putString(ClinicJina, clinicname.getText().toString());
                        editor.commit();
                        fetchData();
                        prev.setEnabled(false);
                        prev.setVisibility(View.GONE);
                    }else {
//                        STRING DATE TO TIMESTAMP
                        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                        Date date = (Date)formatter.parse(prevdate);
//                        date.getTime()- will generate the time stamp
                        todaytxt.setText(day+" "+prevdate);
                        editor.clear();
                        editor.putString(CurrentDate, todaytxt.getText().toString());
                        editor.putString(ClinicJina, clinicname.getText().toString());
                        editor.commit();
                        fetchData();

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dayofweek.set(Calendar.WEEK_OF_MONTH, 1);
                c.add(Calendar.DATE, 1);
                String formattedDate = df.format(c.getTime());
                String day = sdf.format(c.getTime());
                Log.v("NEXT DATE : ", formattedDate);
                todaytxt.setText(day+" "+formattedDate);
                prev.setEnabled(true);
                prev.setVisibility(View.VISIBLE);
                editor.clear();
                editor.putString(CurrentDate, todaytxt.getText().toString());
                editor.putString(ClinicJina, clinicname.getText().toString());
                editor.commit();
                fetchData();
            }
        });

        fetchData();
    }

    private void fetchData() {

        dbaseref.child("Time").child(clinicname.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

        // search for the other means of retrieval of data frm database
                if(dataSnapshot.exists())
                {
        availableclinics.clear();

        for(DataSnapshot postSnapShot:dataSnapshot.getChildren())
        {
            ClinicAvailability getClinic=postSnapShot.getValue(ClinicAvailability.class);
                availableclinics.add(
                    new ClinicAvailability(
                            getClinic.getAvailtime(),
                            getClinic.getKeyvalue()));
                clinicavailabilityAdapter.notifyDataSetChanged();

                    }
                }
                loaderavailability.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(),"error updating",Toast.LENGTH_LONG).show();
            }
        });

        recyclerClinicavailability.setAdapter(clinicavailabilityAdapter);
    }

}
