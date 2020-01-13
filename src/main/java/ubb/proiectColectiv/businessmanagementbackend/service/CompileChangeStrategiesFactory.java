package ubb.proiectColectiv.businessmanagementbackend.service;

import ubb.proiectColectiv.businessmanagementbackend.service.changeStrategies.*;

import java.util.Arrays;
import java.util.List;

public class CompileChangeStrategiesFactory {

    public static List<CompileChangeStrategy> createStrategies() {
        return Arrays.asList(
                new AddProjectChangeStrategy(),
                new DeleteProjectChangeStrategy(),
                new UpdateFirstNameChangeStrategy(),
                new UpdateLastNameChangeStrategy(),
                new UpdateConsultingLevelChangeStrategy(),
                new UpdateRegionChangeStrategy()
        );
    }
}
