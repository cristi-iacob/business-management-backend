package ubb.proiectColectiv.businessmanagementbackend.service.changeStrategies;

import lombok.var;
import ubb.proiectColectiv.businessmanagementbackend.model.ChangeModel;
import ubb.proiectColectiv.businessmanagementbackend.model.ChangeType;
import ubb.proiectColectiv.businessmanagementbackend.model.FullUserSpecification;
import ubb.proiectColectiv.businessmanagementbackend.model.Resource;
import ubb.proiectColectiv.businessmanagementbackend.service.BaseCompileChangeStrategy;
import ubb.proiectColectiv.businessmanagementbackend.service.UserService;
import ubb.proiectColectiv.businessmanagementbackend.utils.FirebaseUtils;

import java.util.Arrays;

public class UpdateConsultingLevelChangeStrategy extends BaseCompileChangeStrategy {
    @Override
    public boolean CanCompile(ChangeModel changeModel) {
        return changeModel.getChangeType().equals(ChangeType.UPDATE) &&
                changeModel.getResource().equals(Resource.CONSULTING_LEVEL);
    }

    @Override
    public void Preview(String userIdentification, FullUserSpecification baseRef, ChangeModel changeModel) {
        baseRef.addMetadata(Resource.CONSULTING_LEVEL.toString(), ChangeType.UPDATE);
        var newConsultingLevelId = changeModel.getArgs().get("consultingLevelId").toString();
        var allConsultingLevels = UserService.getAllPossibleConsultingLevels();
        var newConsultingLevel = allConsultingLevels.stream().filter(x -> x.getId().equals(newConsultingLevelId)).findFirst().get().getName();
        baseRef.setConsultingLevel(newConsultingLevel);
    }

    @Override
    public void Persist(String userIdentification, ChangeModel changeModel) {
        FirebaseUtils.setValue(Arrays.asList("User", getUserHash(userIdentification), "consultingLevelId"), changeModel.getArgs().get("consultingLevelId"));
    }
}
