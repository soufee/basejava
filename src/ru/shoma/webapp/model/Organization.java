package ru.shoma.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Created by Shoma on 08.03.2018.
 */
public   class Organization {
    String name;
    String position;
    LocalDate startDate;
    LocalDate endDate;
    String memo; //Описание должностных обязанностей и достижений

    public Organization(String name, String position, LocalDate startDate, LocalDate endDate) {
        Objects.requireNonNull(name, "The name of organization must not be null");
        Objects.requireNonNull(position, "The position must not be null");
        Objects.requireNonNull(startDate, "The start date must not be null");
        this.name = name;
        this.position = position;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

    @Override
    public String toString() {
        if (endDate==null) {
            return  startDate +" - Н.в. "+ name + "\n" +
                position + "\n" +
                memo;
        } else
        return  startDate +" - "+ endDate+"  "+ name + "\n" +
                position + "\n" +
                memo;
    }
}

