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
import mx.edu.utng.lajosefa.Entity.uploads.PromotionUpload;


/**
 * Created by Catherine on 3/21/2018.
 */

public class PromotionImageAdapter extends RecyclerView.Adapter<PromotionImageAdapter.ImageViewHolder>{

    private Context mContext;
    private List<PromotionUpload> mUploads;
    private OnItemClickListener mListener;

    public  PromotionImageAdapter(Context context, List<PromotionUpload> uploads){
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.promotion_image_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        PromotionUpload uploadCurrent = mUploads.get(position);
        holder.txvDescription.setText(uploadCurrent.getDescription());
        Picasso.with(mContext)
                .load(uploadCurrent.getUrlImage())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imvPromotion);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{
        public TextView txvDescription;
        public ImageView imvPromotion;

        public ImageViewHolder(View itemView) {
            super(itemView);

            txvDescription = itemView.findViewById(R.id.txv_description);
            imvPromotion = itemView.findViewById(R.id.imv_promotion);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mListener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            MenuItem delete = contextMenu.add(Menu.NONE, 1,1,R.string.delete);

            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if(mListener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    switch (menuItem.getItemId()){
                        case 1:
                            mListener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }

    public interface  OnItemClickListener{
        void onItemClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
}
