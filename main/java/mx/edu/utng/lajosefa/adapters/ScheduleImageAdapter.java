package mx.edu.utng.lajosefa.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import mx.edu.utng.lajosefa.R;
import mx.edu.utng.lajosefa.Entity.uploads.ScheduleUpload;

/**
 * Created by Catherine on 3/5/2018.
 */

public class ScheduleImageAdapter extends RecyclerView.Adapter<ScheduleImageAdapter.ImageViewHolder> {

    private Context context;
    private List<ScheduleUpload> scheduleUploads;
    private OnItemClickListener listener;

    public ScheduleImageAdapter(Context context, List<ScheduleUpload> scheduleUploads){
        this.context = context;
        this.scheduleUploads = scheduleUploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_item_schedule, parent,
                false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        ScheduleUpload scheduleUploadCurrent = scheduleUploads.get(position);
        holder.txvDayName.setText(scheduleUploadCurrent.getDayName());
        holder.txvOpen.setText(scheduleUploadCurrent.getOpenHour());
        holder.txvClose.setText(scheduleUploadCurrent.getCloseHour());
        Picasso.with(context)
                .load(scheduleUploadCurrent.getUrlImage())
                .fit().centerCrop().into(holder.imvImageDay);
    }

    @Override
    public int getItemCount() {
        return scheduleUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{

        public TextView txvDayName;
        public TextView txvOpen;
        public TextView txvClose;
        public ImageView imvImageDay;

        public ImageViewHolder(View itemView) {
            super(itemView);

            txvDayName = itemView.findViewById(R.id.txv_day);
            txvOpen = itemView.findViewById(R.id.txv_open);
            txvClose = itemView.findViewById(R.id.txv_close);
            imvImageDay = itemView.findViewById(R.id.imv_day);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View view) {
            if(listener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    listener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            MenuItem delete = contextMenu.add(Menu.NONE,1,1, R.string.delete);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            if(listener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    switch (menuItem.getItemId()){
                        case 1:
                            listener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);

        void onWhatEverClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}