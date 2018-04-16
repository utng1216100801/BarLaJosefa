package mx.edu.utng.lajosefa.adapters;

import android.content.Context;
import android.content.Intent;
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
import mx.edu.utng.lajosefa.activities.SecondActivity;
import mx.edu.utng.lajosefa.Entity.uploads.UploadGallery;

/**
 * Created by Dell Latitude on 13/03/2018.
 */

public class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.ImageViewHolder>  {
    private Context mContext;
    private List<UploadGallery> mUploads;
    private OnItemClickListener mListener;
    private ImageView selectedImage;



    public ImageGalleryAdapter(Context context, List<UploadGallery> uploads){
        mContext = context;
         mUploads = uploads;

    }




    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item_gallery,parent,false);
        ImageViewHolder vh  = new ImageViewHolder (v);
        return  vh;
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, final int position) {
        final UploadGallery uploadCurrent = mUploads.get(position);
        //holder.textName.setText(uploadCurrent.getmName());
       //holder.imageView.setImageResource (ImageUri.get(position));
        Picasso.with(mContext)
                .load(uploadCurrent.getmImageUrl())
                .placeholder(R.mipmap.josefabar)
                .fit()
                .centerCrop()
               .into(holder.imageView);


           holder.itemView.setOnClickListener( new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(mContext, SecondActivity.class );
                    intent.putExtra(mContext.getString(R.string.image), position); // put image data in Intent
                    mContext.startActivity(intent); // start Intent
                }
            });
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder implements
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{
        public TextView textName;
        public ImageView imageView;
        public ImageViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_view);
            //itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }



        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            MenuItem delete = contextMenu.add(Menu.NONE,1,1,R.string.delete);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (mListener!= null){
                int position =getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
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
        public interface  OnItemClickListener {
        void onItemClick(int position);

        void onWhatEverClick(int position);

        void onDeleteClick(int position);

        }

        public void setOnItemClickListener (OnItemClickListener listener){
            mListener = listener;
        }



}
