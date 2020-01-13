package ubb.proiectColectiv.businessmanagementbackend.service;

import ubb.proiectColectiv.businessmanagementbackend.service.changeStrategies.AddProjectChangeStrategy;
import ubb.proiectColectiv.businessmanagementbackend.service.changeStrategies.DeleteProjectChangeStrategy;
import ubb.proiectColectiv.businessmanagementbackend.service.changeStrategies.UpdateFirstNameChangeStrategy;
import ubb.proiectColectiv.businessmanagementbackend.service.changeStrategies.UpdateLastNameChangeStrategy;

import java.util.Arrays;
import java.util.List;

public class CompileChangeStrategiesFactory {

    public static List<CompileChangeStrategy> createStrategies() {
        return Arrays.asList(
                new AddProjectChangeStrategy(),
                new DeleteProjectChangeStrategy(),
                new UpdateFirstNameChangeStrategy(),
                new UpdateLastNameChangeStrategy()
        );
    }
}
