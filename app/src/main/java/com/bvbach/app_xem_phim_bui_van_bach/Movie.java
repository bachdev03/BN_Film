package com.bvbach.app_xem_phim_bui_van_bach;

import java.io.Serializable;

public class Movie implements Serializable {
    private int id;
    private String title;
    private String overview;
    private String posterPath;
    private String description; // Thêm thuộc tính description


    // Constructor
    public Movie(int id, String title, String overview, String posterPath) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
        this.description = description; // Khởi tạo description

    }

    // Getter và Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getOverview() { return overview; }
    public void setOverview(String overview) { this.overview = overview; }

    public String getPosterPath() { return posterPath; }
    public void setPosterPath(String posterPath) { this.posterPath = posterPath; }

    public String getDescription() {
        return description; // Phương thức getter cho description
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
