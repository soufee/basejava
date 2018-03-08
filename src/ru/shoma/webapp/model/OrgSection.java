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


    public void addOrganization(Organization organization) {
        organizations.add(organization);
    }

    public void updateOrganization(Organization organization) {

    }

    public void deleteOrganization(Organization organization) {

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Organization s:organizations) {
            sb.append("- "+s+"\n");
        }
        return sb.toString();
    }
}
