package knh.or.ke.hospital.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import knh.or.ke.hospital.R;
import knh.or.ke.hospital.Reschedule;
import knh.or.ke.hospital.Setters.Chosenclinic;

public class BookedclinicAdapter  extends RecyclerView.Adapter<BookedclinicAdapter.ClinicViewHolder> implements Filterable {


    //this context we will use to inflate the layout
    private Context mCtx;
    private BookedAdapterListener listener;
    private List<Chosenclinic> clinicList;
    private List<Chosenclinic> searchList;

    //getting the context and clinic list with constructor
    public BookedclinicAdapter(Context mCtx, List<Chosenclinic> clinicList){
        this.mCtx = mCtx;
        this.clinicList = clinicList;
        this.searchList = clinicList;
    }

    @Override
    public BookedclinicAdapter.ClinicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.bookinglistiem, null);

        return new BookedclinicAdapter.ClinicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BookedclinicAdapter.ClinicViewHolder holder, int position) {
        //getting the product of the specified position
        final Chosenclinic clinic = clinicList.get(position);


        //binding the data with the viewholder views
        holder.myclinic.setText(clinic.getClinicname());
        holder.mydate.setText(clinic.getClinicdate());
        holder.mytime.setText(clinic.getClinictime());

	holder.textViewOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(mCtx, holder.textViewOptions);
                //inflating menu from xml resource
                popup.inflate(R.menu.menu_list);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
                                //handle menu1 clic
                                new AlertDialog.Builder(mCtx)
                                        .setTitle("Reschedule Clinic")
                                        .setMessage("Do want to reschedule clinic?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                Intent clinicavailability= new Intent(mCtx,Reschedule.class);
                                                clinicavailability.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                                                clinicavailability.putExtra("Resclinicname", clinic.getClinicname());
                                                clinicavailability.putExtra("Resclinickey", clinic.getKeytouse());
                                                mCtx.getApplicationContext().startActivity(clinicavailability);
                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();

                                break;
                            case R.id.delete:
                                //handle menu2 click
                                new AlertDialog.Builder(mCtx)
                                        .setTitle("CANCEL CLINIC")
                                        .setMessage("Do want to unbook clinic?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                DatabaseReference dbaseref;
                                                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                                                dbaseref= FirebaseDatabase.getInstance().getReference().child("availableclinics");
                                                dbaseref.child("Patients").child(currentFirebaseUser.getUid().toString()).child(clinic.getKeytouse()).removeValue();
                                                dbaseref.child("Bookedtimes").child(clinic.getClinicname()).child(clinic.getKeytouse()).removeValue();
                                                Toast.makeText(mCtx, "Clinic canceled", Toast.LENGTH_SHORT).show();
                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();

                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return clinicList.size();
    }


    class ClinicViewHolder extends RecyclerView.ViewHolder {

        TextView myclinic, mydate,mytime,textViewOptions;

        public ClinicViewHolder(View itemView) {
            super(itemView);
            myclinic = itemView.findViewById(R.id.myclinic);
            mydate = itemView.findViewById(R.id.mydate);
            mytime = itemView.findViewById(R.id.mytime);
            textViewOptions = itemView.findViewById(R.id.textViewOptions);
        }

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    clinicList = searchList;
                } else {
                    List<Chosenclinic> filteredList = new ArrayList<>();
                    for (Chosenclinic row : clinicList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getClinicname().toLowerCase().contains(charString.toLowerCase())
                                ||row.getClinicdate().toLowerCase().contains(charString.toLowerCase())||
                                row.getClinictime().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    clinicList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = clinicList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                clinicList = (ArrayList<Chosenclinic>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface BookedAdapterListener {
        void onContactSelected(Chosenclinic contact);
    }

}
