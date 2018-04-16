package mx.edu.utng.lajosefa.fragments;

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
import mx.edu.utng.lajosefa.Entity.uploads.UploadEvent;

public class ImageEventAdapterFragment extends RecyclerView.Adapter<ImageEventAdapterFragment.ImageViewHolder>  {
    private Context mContext;
    private List<UploadEvent> mUploads;

    private ImageEventAdapterFragment.OnItemClickListener mListener;

    public ImageEventAdapterFragment(Context context, List<UploadEvent>uploads){
        mContext = context;
        mUploads = uploads;
    }
    @Override
    public ImageEventAdapterFragment.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.fragment_item_event,parent, false );
        return new ImageEventAdapterFragment.ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageEventAdapterFragment.ImageViewHolder holder, int position) {
        UploadEvent uploadCurrent = mUploads.get(position);
        holder.txvCommentary.setText(uploadCurrent.getCommentary());
        Picasso.with(mContext)
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.drawable.la_josefa)
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{
        public TextView txvCommentary;
        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            txvCommentary = itemView.findViewById(R.id.txv_commentary);
            imageView = itemView.findViewById(R.id.imv_photo);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mListener != null){
                int position= getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem delete = menu.add(Menu.NONE,1,1,R.string.delete);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(mListener != null){
                int position= getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){

                    switch (item.getItemId()){
                        case 1:
                            mListener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }
    public  interface OnItemClickListener{
        void onItemClick(int position);

        void onWhatEverClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(ImageEventAdapterFragment.OnItemClickListener listener){
        mListener = listener;
    }

}
