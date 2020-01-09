package ubb.proiectColectiv.businessmanagementbackend.service.changeStrategies;

import lombok.var;
import ubb.proiectColectiv.businessmanagementbackend.model.*;
import ubb.proiectColectiv.businessmanagementbackend.service.BaseCompileChangeStrategy;
import ubb.proiectColectiv.businessmanagementbackend.service.UserService;
import ubb.proiectColectiv.businessmanagementbackend.utils.FirebaseUtils;

import java.util.Arrays;
import java.util.Objects;

public class AddProjectChangeStrategy extends BaseCompileChangeStrategy {
    @Override
    public boolean CanCompile(ChangeModel changeModel) {
        return changeModel.getResource().equals(Resource.PROJECT)
                && changeModel.getChangeType().equals(ChangeType.ADD);
    }

    @Override
    public void Preview(String userIdentification, FullUserSpecification baseRef, ChangeModel changeModel) {
        var args = changeModel.getArgs();
        var newId = args.get("newId").toString();
        var userId = String.valueOf(Objects.hash(userIdentification));
        args.put("userId", userId);
        var newProjectExperience = UserService.buildProjectExperienceEntryFromMap(changeModel.getArgs(), newId);
        newProjectExperience.setItemState(ItemState.ADDED);
        baseRef.getProjectExperience().add(newProjectExperience);
    }

    /**
     *
     * @param userIdentification
     * @param changeModel
     * consultingLevelId
     * description
     * endDate
     * projectId
     * startDate
     * newId
     */
    @Override
    public void Persist(String userIdentification, ChangeModel changeModel) {
        var args = changeModel.getArgs();
        var projectExperienceId = args.get("newId").toString();
        var userId = String.valueOf(Objects.hash(userIdentification));
        args.put("userId", userId);
        FirebaseUtils.setValue(Arrays.asList("ProjectExperience", projectExperienceId), changeModel.getArgs());
    }
}
