package ru.shoma.webapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Shoma on 04.03.2018.
 */
public class OrgSection extends Section {
    private List<Organization> organizations = new ArrayList<>();

    public List<Organization> getOrganizations() {
        return new ArrayList<>(organizations);
    }

    class Organization {
        String name;
        String position;
        LocalDate startDate;
        LocalDate endDate;

        public Organization(String name, String position, LocalDate startDate, LocalDate endDate) {
            Objects.requireNonNull(name, "The name of organization must not be null");
            Objects.requireNonNull(position, "The position must not be null");
            Objects.requireNonNull(startDate, "The start date must not be null");
            this.name = name;
            this.position = position;
            this.startDate = startDate;
            this.endDate = endDate;
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
    }

    public void addOrganization(Organization organization) {

    }

    public void updateOrganization(Organization organization) {

    }

    public void deleteOrganization(Organization organization) {

    }
}
