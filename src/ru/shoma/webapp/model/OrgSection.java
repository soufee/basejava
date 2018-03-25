package ru.shoma.webapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by Shoma on 04.03.2018.
 */
public class OrgSection extends Section {
    private List<Organization> organizations;

    public OrgSection(Organization ... organizations){
        this(Arrays.asList(organizations));
    }

    public OrgSection(List<Organization> organizations) {
       Objects.requireNonNull(organizations, "List of organizations must not be null");
        this.organizations = organizations;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrgSection)) return false;

        OrgSection that = (OrgSection) o;

        return organizations != null ? organizations.equals(that.organizations) : that.organizations == null;
    }

    @Override
    public int hashCode() {
        return organizations != null ? organizations.hashCode() : 0;
    }

    public List<Organization> getOrganizations() {
        return new ArrayList<>(organizations);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Organization s : organizations) {
            sb.append("- " + s + "\n");
        }
        return sb.toString();
    }
}
