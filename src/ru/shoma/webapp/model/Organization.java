package ru.shoma.webapp.model;

import ru.shoma.webapp.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ru.shoma.webapp.util.DateUtil.NOW;

/**
 * Created by Shoma on 08.03.2018.
 */
public class Organization {
    private Link homepage;
    private String name;
    private List<Position> positions = new ArrayList<>();

    public Organization(Link homepage, List<Position> positions) {
        Objects.requireNonNull(homepage, "The homepage of organization must not be null");
        this.homepage = homepage;
        this.positions = positions;
    }

public Organization (String name, String url, Position ... positions){
        this(new Link(name, url), Arrays.asList(positions));
}
    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-YYYY");

        return "Organisation: " + homepage+"\nPosition: "+positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization)) return false;

        Organization that = (Organization) o;

        if (homepage != null ? !homepage.equals(that.homepage) : that.homepage != null) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = homepage != null ? homepage.hashCode() : 0;
        result = 31 * result + name.hashCode();
        return result;
    }

    public static class Position {
        private String title;
        private LocalDate startDate;
        private LocalDate endDate;
        private String description;

        public Position(int year, Month month, String title, String description){
            this(title, DateUtil.of(year,month), NOW, description);
        }

        public Position(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description){
            this(title, DateUtil.of(startYear, startMonth), DateUtil.of(endYear, endMonth), description);
        }

        public Position(String title, LocalDate startDate, LocalDate endDate, String description) {
            Objects.requireNonNull(title, "The title must not be null");
            Objects.requireNonNull(startDate, "The start date must not be null");
            this.title = title;
            this.startDate = startDate;
            if (endDate==null)
            this.endDate = NOW;
            else this.endDate = endDate;
            this.description = description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Position)) return false;

            Position position = (Position) o;

            if (title != null ? !title.equals(position.title) : position.title != null) return false;
            if (startDate != null ? !startDate.equals(position.startDate) : position.startDate != null) return false;
            if (endDate != null ? !endDate.equals(position.endDate) : position.endDate != null) return false;
            return description != null ? description.equals(position.description) : position.description == null;
        }

        @Override
        public int hashCode() {
            int result = title != null ? title.hashCode() : 0;
            result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
            result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
            result = 31 * result + (description != null ? description.hashCode() : 0);
            return result;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public void setStartDate(LocalDate startDate) {
            this.startDate = startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "title='" + title + '\'' +
                    ", startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}

