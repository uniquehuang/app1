package com.century.zj.entity;

public class UpdateCountEntity {
    /**
     *  params.put("id", id);
     *  params.put("collectcount", collectcount);
     *  params.put("commentcount", commentcount);
     *  params.put("dzcount", dzcount);
     *  params.put("dzflag", dzflag);
     *  params.put("scflag", scflag);
     */
    private int id;
    private int collectcount;
    private int commentcount;
    private int dzcount;
    private int dzflag;
    private int scflag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCollectcount() {
        return collectcount;
    }

    public void setCollectcount(int collectcount) {
        this.collectcount = collectcount;
    }

    public int getCommentcount() {
        return commentcount;
    }

    public void setCommentcount(int commentcount) {
        this.commentcount = commentcount;
    }

    public int getDzcount() {
        return dzcount;
    }

    public void setDzcount(int dzcount) {
        this.dzcount = dzcount;
    }

    public int getDzflag() {
        return dzflag;
    }

    public void setDzflag(int dzflag) {
        this.dzflag = dzflag;
    }

    public int getScflag() {
        return scflag;
    }

    public void setScflag(int scflag) {
        this.scflag = scflag;
    }

    public UpdateCountEntity(int id, int collectcount, int commentcount, int dzcount, int dzflag, int scflag) {
        this.id = id;
        this.collectcount = collectcount;
        this.commentcount = commentcount;
        this.dzcount = dzcount;
        this.dzflag = dzflag;
        this.scflag = scflag;
    }
}
