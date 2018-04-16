package mx.edu.utng.lajosefa;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.edu.utng.lajosefa.Entity.ReceiveMessage;

/**
 * Created by Catherine on 2/26/2018.
 */

public class AdapterMessages extends RecyclerView.Adapter<HolderMessage> {

    List<ReceiveMessage> lstMessage = new ArrayList<>();
    private Context c;

    public AdapterMessages(Context c) {
        this.c = c;
    }

    public void addMessage(ReceiveMessage message){
        lstMessage.add(message);
        notifyItemInserted(lstMessage.size());
    }

    @Override
    public HolderMessage onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.card_view_message,
                parent,false);
        return new HolderMessage(v);
    }

    @Override
    public void onBindViewHolder(HolderMessage holder, int position) {
        holder.getTxvUsername().setText(lstMessage.get(position).getName());
        holder.getTxvMessage().setText(lstMessage.get(position).getMessage());
        if(lstMessage.get(position).getTypeMessage().equals("2")){
            holder.getImvPicture().setVisibility(View.VISIBLE);
            holder.getTxvMessage().setVisibility(View.VISIBLE);
            Glide.with(c).load(lstMessage.get(position).getUrlPhoto()).into(holder.getImvPicture());
        }else if(lstMessage.get(position).getTypeMessage().equals("1")){
            holder.getImvPicture().setVisibility(View.GONE);
            holder.getTxvMessage().setVisibility(View.VISIBLE);
        }

        if(lstMessage.get(position).getProfilePhoto().isEmpty()){
            holder.getCivPhoto().setImageResource(R.drawable.josefa);
        }else{
            Glide.with(c).load(lstMessage.get(position).getUrlPhoto()).into(holder.getCivPhoto());
            //Log.i("Photo: ",lstMessage.get(position).getUrlPhoto());
        }

        Long hourCode = lstMessage.get(position).getHour();
        Date d = new Date(hourCode);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        holder.getTxvHour().setText(sdf.format(d));
    }

    @Override
    public int getItemCount() {
        return lstMessage.size();
    }
}

