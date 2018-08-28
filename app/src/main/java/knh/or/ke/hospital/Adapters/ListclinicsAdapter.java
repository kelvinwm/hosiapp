package knh.or.ke.hospital.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import knh.or.ke.hospital.R;
import knh.or.ke.hospital.Setters.ClinicList;

public class ListclinicsAdapter extends RecyclerView.Adapter<ListclinicsAdapter.ClinicViewHolder> implements Filterable {


    //this context we will use to inflate the layout
    private Context mCtx;
    private ListclinicsAdapterListener listener;
    private List<ClinicList> clinicList;
    private List<ClinicList> searchList;

    //getting the context and clinic list with constructor
    public ListclinicsAdapter(Context mCtx, List<ClinicList> clinicList){
        this.mCtx = mCtx;
        this.clinicList = clinicList;
        this.searchList = clinicList;
    }

    @Override
    public ListclinicsAdapter.ClinicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.cliniclistitem, null);

        return new ListclinicsAdapter.ClinicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListclinicsAdapter.ClinicViewHolder holder, int position) {
        //getting the product of the specified position
        ClinicList clinic = clinicList.get(position);

        //binding the data with the viewholder views
        holder.clinicname.setText(clinic.getClinicname());
        holder.clinicdescription.setText(clinic.getClinicdescription());

    }

    @Override
    public int getItemCount() {
        return clinicList.size();
    }


    class ClinicViewHolder extends RecyclerView.ViewHolder {

        TextView clinicdescription, clinicname;


        public ClinicViewHolder(View itemView) {
            super(itemView);
            clinicname = itemView.findViewById(R.id.nameclinic);
            clinicdescription = itemView.findViewById(R.id.describeclinic);
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
                    List<ClinicList> filteredList = new ArrayList<>();
                    for (ClinicList row : clinicList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getClinicname().toLowerCase().contains(charString.toLowerCase())
                                ||row.getClinicdescription().toLowerCase().contains(charString.toLowerCase())) {
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
                clinicList = (ArrayList<ClinicList>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ListclinicsAdapterListener {
        void onContactSelected(ClinicList contact);
    }
}
