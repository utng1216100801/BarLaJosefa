package mx.edu.utng.lajosefa;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Catherine on 2/26/2018.
 */

public class HolderMessage extends RecyclerView.ViewHolder {

    private TextView txvUsername;
    private TextView txvMessage;
    private TextView txvHour;
    private CircleImageView civPhoto;
    private ImageView imvPicture;

    public HolderMessage(View itemView) {
        super(itemView);
        txvUsername = itemView.findViewById(R.id.txv_usermessage);
        txvMessage = itemView.findViewById(R.id.txv_message);
        txvHour = itemView.findViewById(R.id.txv_hourmessage);
        civPhoto = itemView.findViewById(R.id.cim_photo);
        imvPicture = itemView.findViewById(R.id.imv_picture);
    }

    public TextView getTxvUsername() {
        return txvUsername;
    }

    public void setTxvUsername(TextView txvUsername) {
        this.txvUsername = txvUsername;
    }

    public TextView getTxvMessage() {
        return txvMessage;
    }

    public void setTxvMessage(TextView txvMessage) {
        this.txvMessage = txvMessage;
    }

    public TextView getTxvHour() {
        return txvHour;
    }

    public void setTxvHour(TextView txvHour) {
        this.txvHour = txvHour;
    }

    public CircleImageView getCivPhoto() {
        return civPhoto;
    }

    public void setCivPhoto(CircleImageView civPhoto) {
        this.civPhoto = civPhoto;
    }

    public ImageView getImvPicture() {
        return imvPicture;
    }

    public void setImvPicture(ImageView imvPicture) {
        this.imvPicture = imvPicture;
    }
}
