package com.curl.ciykit.curl.model;

/**
 * Created by Akash Chandra on 07-03-2017.
 */

public class NewsModel {
    public int news_id;
    public String news_head;
    public String news_data;
    public String news_type;
    public String news_imgurl;
    public String news_source;
    public String news_category;
    public String updated_at;
    public String news_date;
    public String news_url;
    public int status;
    public String lang;
    public String video;
    public String slide;
    public String advt;

    public NewsModel() {
    }

    public NewsModel(int news_id, String news_head, String news_data, String news_type, String news_imgurl, String news_source, String news_category, String updated_at, String news_date, String news_url, int status, String lang, String video, String slide, String advt) {
        this.news_id = news_id;
        this.news_head = news_head;
        this.news_data = news_data;
        this.news_type = news_type;
        this.news_imgurl = news_imgurl;
        this.news_source = news_source;
        this.news_category = news_category;
        this.updated_at = updated_at;
        this.news_date = news_date;
        this.news_url = news_url;
        this.status = status;
        this.lang = lang;
        this.video = video;
        this.slide = slide;
        this.advt = advt;
    }

    public int getNews_id() {
        return news_id;
    }

    public String getNews_head() {
        return news_head;
    }

    public String getNews_data() {
        return news_data;
    }

    public String getNews_type() {
        return news_type;
    }

    public String getNews_imgurl() {
        return news_imgurl;
    }

    public String getNews_source() {
        return news_source;
    }

    public String getNews_category() {
        return news_category;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getNews_date() {
        return news_date;
    }

    public String getNews_url() {
        return news_url;
    }

    public int getStatus() {
        return status;
    }

    public String getLang() {
        return lang;
    }

    public String getVideo() {
        return video;
    }

    public String getSlide() {
        return slide;
    }

    public String getAdvt() {
        return advt;
    }

    public void setNews_id(int news_id) {
        this.news_id = news_id;
    }

    public void setNews_head(String news_head) {
        this.news_head = news_head;
    }

    public void setNews_data(String news_data) {
        this.news_data = news_data;
    }

    public void setNews_type(String news_type) {
        this.news_type = news_type;
    }

    public void setNews_imgurl(String news_imgurl) {
        this.news_imgurl = news_imgurl;
    }

    public void setNews_source(String news_source) {
        this.news_source = news_source;
    }

    public void setNews_category(String news_category) {
        this.news_category = news_category;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public void setNews_date(String news_date) {
        this.news_date = news_date;
    }

    public void setNews_url(String news_url) {
        this.news_url = news_url;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public void setSlide(String slide) {
        this.slide = slide;
    }

    public void setAdvt(String advt) {
        this.advt = advt;
    }
}
