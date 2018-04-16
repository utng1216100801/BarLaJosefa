package mx.edu.utng.lajosefa.Entity;

public class Comments  {
    String commentsId;
    String commentsName;

    public Comments (){

    }

    public Comments(String commentsId, String commentsName) {
        this.commentsId = commentsId;
        this.commentsName = commentsName;
    }

    public String getCommentsId() {
        return commentsId;
    }

    public String getCommentsName() {
        return commentsName;
    }

    public void setCommentsId(String commentsId) {
        this.commentsId = commentsId;
    }

    public void setCommentsName(String commentsName) {
        this.commentsName = commentsName;
    }
}
