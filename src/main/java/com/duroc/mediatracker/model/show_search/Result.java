package com.duroc.mediatracker.model.show_search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {
    private long id;
    private String name;
    private String overview;
    private String poster_path;
    private String first_air_date;

    public Result(long id, String name, String overview, String poster_path, String first_air_date) {
        this.id = id;
        this.name = name;
        this.overview = overview;
        this.poster_path = poster_path;
        this.first_air_date = first_air_date;
    }

    public Result() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return id == result.id && Objects.equals(name, result.name) && Objects.equals(overview, result.overview) && Objects.equals(poster_path, result.poster_path) && Objects.equals(first_air_date, result.first_air_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, overview, poster_path, first_air_date);
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", overview='" + overview + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", first_air_date='" + first_air_date + '\'' +
                '}';
    }
}
