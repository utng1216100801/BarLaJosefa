package mx.edu.utng.lajosefa;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import mx.edu.utng.lajosefa.Entity.Comments;

public class CommentsList extends ArrayAdapter {

    private Activity context;
    private List<Comments> commentsList;

    public CommentsList (Activity context, List<Comments> commentsList){
        super (context, R.layout.activity_comments_list,commentsList);
        this.context = context;
        this.commentsList = commentsList;
    }
    @Override
    public View getView (int position, View convertirView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.activity_comments_list,null,true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);

        Comments comments = commentsList.get(position);
        textViewName.setText(comments.getCommentsName());

        return listViewItem;


    }
}
