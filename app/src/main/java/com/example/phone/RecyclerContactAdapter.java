package com.example.phone;

import android.app.Activity;
import android.content.Context;
import android.graphics.ImageFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerContactAdapter extends RecyclerView.Adapter<RecyclerContactAdapter.ViewHolder>{
    Activity activity;
    ArrayList<ContactModel>arrContacts;
public RecyclerContactAdapter(Activity activity, ArrayList<ContactModel>arrContacts){
    this.activity = activity;
    this.arrContacts = arrContacts;
notifyDataSetChanged ();
}
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v =  LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.contact_row,parent,false );
        ViewHolder viewHolder = new ViewHolder ( v );
      return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    ContactModel model = arrContacts.get ( position );
//    holder.img.setImageResource ( arrContacts.get ( position ).img );
//    holder.txtName.setText ( arrContacts.get ( position ).name );
//    holder.txtNumber.setText ( arrContacts.get ( position ).number );

        holder.txtName.setText ( model.getName () );
        holder.txtNumber.setText ( model.getNumber () );
    }

    @Override
    public int getItemCount() {

    return arrContacts.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
    TextView txtName,txtNumber;
    ImageView img;

        public ViewHolder(View itemView)
        {
            super(itemView);
            txtName = itemView.findViewById ( R.id.txtName );
            txtNumber = itemView.findViewById ( R.id.txtNumber );
            img = itemView.findViewById ( R.id.img);
        }
    }
}
