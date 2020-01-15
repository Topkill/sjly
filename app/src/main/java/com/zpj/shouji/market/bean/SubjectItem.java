package com.zpj.shouji.market.bean;

import com.zpj.http.parser.html.nodes.Element;

public class SubjectItem {

    private String id;
    private String icon;
    private String title;
    private String m;
    private String viewType;
    private String appType;
    private String comment;

    public static SubjectItem create(Element element) {
        SubjectItem item = new SubjectItem();
        item.setId(element.selectFirst("id").text());
        item.setIcon(element.selectFirst("icon").text());
        item.setTitle(element.selectFirst("title").text());
        item.setViewType(element.selectFirst("viewtype").text());
        item.setAppType(element.selectFirst("apptype").text());
        item.setComment(element.selectFirst("comment").text());
        item.setM(element.selectFirst("m").text());
        return item;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
