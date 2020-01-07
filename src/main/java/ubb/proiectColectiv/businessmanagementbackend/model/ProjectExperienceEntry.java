package ubb.proiectColectiv.businessmanagementbackend.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Class wrapping data for a project experience entry from the database.
 * Also used as a transport model for the HTTP API.
 * All dates are formatted as a UNIX style timestamp (millis since epoch start)
 */
@Getter
@Setter
@Builder
public class ProjectExperienceEntry {
    private String id;
    private int startDate;
    private int endDate;
    private String consultingLevel;
    private String description;
    private int projectEndDate;
    private int projectStartDate;
    private String projectName;
    private String industry;
    private String clientName;
    private String clientAddress;
    private ItemState itemState;
}
