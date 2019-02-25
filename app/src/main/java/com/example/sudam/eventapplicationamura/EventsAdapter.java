package com.example.sudam.eventapplicationamura;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

/**
 * Created by Sudam Chole on 22/02/19.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.CustomViewHolder> {

    Context context;
    public static ArrayList<Events> events;

    public EventsAdapter(Context context, ArrayList<Events> events) {
        this.context = context;
        this.events = events;
    }


    @Override
    public EventsAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_view_items, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final EventsAdapter.CustomViewHolder holder, int position) {
        Events currentEvents = events.get(events.size() - position - 1);


        holder.eventName.setText(currentEvents.eventName);
        holder.eventAgenda.setText(currentEvents.eventAgenda);
        holder.eventDate.setText(currentEvents.eventDate);
        holder.eventTime.setText(currentEvents.eventTime);


    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {


        TextView eventName;
        TextView eventAgenda;
        TextView eventDate;
        TextView eventTime;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            eventName = (TextView) itemView.findViewById(R.id.txtEventName);
            eventAgenda = (TextView) itemView.findViewById(R.id.txtEventAgenda);
            eventDate = (TextView) itemView.findViewById(R.id.txtEvenDate);
            eventTime = (TextView) itemView.findViewById(R.id.txtEvenTime);

        }
    }
}
