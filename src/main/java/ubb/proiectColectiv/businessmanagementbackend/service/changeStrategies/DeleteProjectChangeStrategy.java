package ubb.proiectColectiv.businessmanagementbackend.service.changeStrategies;

import lombok.var;
import ubb.proiectColectiv.businessmanagementbackend.model.*;
import ubb.proiectColectiv.businessmanagementbackend.service.BaseCompileChangeStrategy;
import ubb.proiectColectiv.businessmanagementbackend.utils.FirebaseUtils;

import java.util.Arrays;

public class DeleteProjectChangeStrategy extends BaseCompileChangeStrategy {

    @Override
    public boolean CanCompile(ChangeModel changeModel) {
        return changeModel.getResource().equals(Resource.PROJECT)
                && changeModel.getChangeType().equals(ChangeType.DELETE);
    }

    @Override
    public void Preview(String userIdentification, FullUserSpecification baseRef, ChangeModel changeModel) {
        var args = changeModel.getArgs();
        var projectExperienceId = args.get("id").toString();

        baseRef.getProjectExperience().forEach(p -> {
            if(p.getId().equals(projectExperienceId)) {
                p.setItemState(ItemState.DELETED);
            }
        });
    }

    @Override
    public void Persist(String userIdentification, ChangeModel changeModel) {
        var args = changeModel.getArgs();
        var projectExperienceId = args.get("id").toString();
        FirebaseUtils.removeValue(Arrays.asList("ProjectExperience", projectExperienceId));
    }
}
