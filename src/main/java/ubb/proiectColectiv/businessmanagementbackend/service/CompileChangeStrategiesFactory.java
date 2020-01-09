package ubb.proiectColectiv.businessmanagementbackend.service;

import ubb.proiectColectiv.businessmanagementbackend.service.changeStrategies.AddProjectChangeStrategy;
import ubb.proiectColectiv.businessmanagementbackend.service.changeStrategies.DeleteProjectChangeStrategy;

import java.util.Arrays;
import java.util.List;

public class CompileChangeStrategiesFactory {

    public static List<CompileChangeStrategy> createStrategies() {
        return Arrays.asList(new AddProjectChangeStrategy(), new DeleteProjectChangeStrategy());
    }
}
