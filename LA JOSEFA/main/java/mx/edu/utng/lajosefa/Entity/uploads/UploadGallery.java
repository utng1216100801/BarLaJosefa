package mx.edu.utng.lajosefa.Entity.uploads;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class UploadGallery implements Serializable{
    private String mImageUrl;
    private String mKey;

    public UploadGallery(){

    }

    public UploadGallery(String imageUrl){
       // if (name.trim().equals("")){
          //  name= "no name";
        //}
        //mName=name;
        mImageUrl = imageUrl;
    }

   /* public String  getmName(){
        return  mName;
    }*/

    public String getmImageUrl (){
        return  mImageUrl;
    }


   /* public void setmName (String name){
        name = name;
    }*/

    public void setmImageUrl (String imageUrl){
        mImageUrl = imageUrl;
    }
@Exclude
    public String getKey(){
        return mKey;
    }
@Exclude
    public void setKey (String key){
        mKey = key;
    }
}


