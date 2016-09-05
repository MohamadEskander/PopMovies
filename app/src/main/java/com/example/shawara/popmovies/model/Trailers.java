package com.example.shawara.popmovies.model;

import java.util.List;

/**
 * Created by shawara on 9/3/2016.
 */

public class Trailers {

    /**
     * id : 150540
     * results : [{"id":"571f2fa39251417e8a001b3f","iso_639_1":"en","iso_3166_1":"US","key":"yRUAzGQ3nSY","name":"Official US Trailer","site":"YouTube","size":1080,"type":"Trailer"},{"id":"571f2fffc3a3683393002ca1","iso_639_1":"en","iso_3166_1":"US","key":"1HFv47QHWJU","name":"Official US Trailer 2","site":"YouTube","size":1080,"type":"Trailer"},{"id":"543391a80e0a265834006c5a","iso_639_1":"en","iso_3166_1":"US","key":"1t0A_tZGrYw","name":"Inside Out US Teaser Trailer","site":"YouTube","size":1080,"type":"Teaser"},{"id":"54a954299251414d5d004843","iso_639_1":"en","iso_3166_1":"US","key":"_MC3XuMvsDI","name":"Inside Out UK Trailer 2","site":"YouTube","size":1080,"type":"Trailer"},{"id":"571f302f92514142e2002023","iso_639_1":"en","iso_3166_1":"US","key":"Yr3tv1hW1gg","name":"Official New UK Trailer","site":"YouTube","size":1080,"type":"Trailer"},{"id":"571f309dc3a36856a7000b46","iso_639_1":"en","iso_3166_1":"US","key":"k1oXx4delIY","name":"\"Disgust & Anger\"","site":"YouTube","size":1080,"type":"Clip"}]
     *
     */

    private int id;
    /**
     * id : 571f2fa39251417e8a001b3f
     * iso_639_1 : en
     * iso_3166_1 : US
     * key : yRUAzGQ3nSY
     * name : Official US Trailer
     * site : YouTube
     * size : 1080
     * type : Trailer
     */

    private List<Trailer> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Trailer> getResults() {
        return results;
    }

    public void setResults(List<Trailer> results) {
        this.results = results;
    }

    public static class Trailer {
        private String id;
        private String key;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
