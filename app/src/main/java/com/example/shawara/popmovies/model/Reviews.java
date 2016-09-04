package com.example.shawara.popmovies.model;

import java.util.List;

/**
 * Created by shawara on 9/3/2016.
 */

public class Reviews {

    /**
     * id : 150540
     * page : 1
     * results : [{"id":"5611c3d99251417899002fcd","author":"Fatota","content":"This is the most incredible movie I've ever seen :)","url":"https://www.themoviedb.org/review/5611c3d99251417899002fcd"},{"id":"56127371c3a368680b015293","author":"Andres Gomez","content":"Another great movie from Pixar. The story in entangling and is structured in a master way to show us in a nice recreation how the mind works and emotions like sadness are important for a healthy life.\r\n\r\nA must to be seen.","url":"https://www.themoviedb.org/review/56127371c3a368680b015293"},{"id":"564d7a06c3a368602b009af9","author":"Sxerks3","content":"A powerfully moving story, Inside Out takes place inside the mind of a young girl, Riley, as she tackles relatively normal hassles, from growing up to moving away. Inside her mind comes five emotions, all with different perceptions of life.\r\n\r\nThere's Joy, who takes charge and her job is to keep Riley content, Sadness, Anger, Fear, and Disgust, and together, they work together (or try) to keep Riley from harming herself. They live together in the headquarters, or Riley's head, and they look after Riley's memories.\r\n\r\nAfter eleven years of enjoyment and content, Riley and her parents are forced to move out of Minnesota, away from her friends and hockey team, and moves to the city of San Francisco inside a desolate and cold house.\r\n\r\nBut things get even more anarchic when Joy and Sadness are sundered away from the rest of the gang, holding onto Riley's core memories that make Riley, well, Riley. One by one, the islands of personality fall apart as the rest of the gang back in headquarters watch in dismay and anguish. After all, Joy is not there to keep things under control.\r\n\r\nInside Out truly contemplates the hardships of growing up, but every problem comes to a heart-wrenching solution. The movie truly captivates this and continues the Pixar tradition of inspiration, family, and friendship in a little bit under two hours. Watch this, you must.","url":"https://www.themoviedb.org/review/564d7a06c3a368602b009af9"}]
     * total_pages : 1
     * total_results : 3
     */

    private int id;
    /**
     * id : 5611c3d99251417899002fcd
     * author : Fatota
     * content : This is the most incredible movie I've ever seen :)
     * url : https://www.themoviedb.org/review/5611c3d99251417899002fcd
     */

    private List<Review> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Review> getResults() {
        return results;
    }

    public void setResults(List<Review> results) {
        this.results = results;
    }

    public static class Review {
        private String id;
        private String author;
        private String content;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
