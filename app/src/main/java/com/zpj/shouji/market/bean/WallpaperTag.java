package com.zpj.shouji.market.bean;

import com.zpj.http.parser.html.nodes.Element;

public class WallpaperTag {

    private String id;
    private String name;

    public static WallpaperTag create(Element element) {
        WallpaperTag tag = new WallpaperTag();
        tag.id = element.selectFirst("id").text();
        tag.name = element.selectFirst("name").text();
        return tag;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
