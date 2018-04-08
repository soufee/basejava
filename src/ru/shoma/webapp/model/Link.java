package ru.shoma.webapp.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Shoma on 09.03.2018.
 */
public class Link implements Serializable{

    private static final long serialVersionUid = 1L;

    private String name;
    private String url;

    public Link(String name, String url) {
        Objects.requireNonNull(name, "Name can not be empty or null");
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Link)) return false;

        Link link = (Link) o;

        if (!name.equals(link.name)) return false;
        return url != null ? url.equals(link.url) : link.url == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Link{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
