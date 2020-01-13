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

public class UpdateRegionChangeStrategy extends BaseCompileChangeStrategy {
    @Override
    public boolean CanCompile(ChangeModel changeModel) {
        return changeModel.getChangeType().equals(ChangeType.UPDATE) &&
                changeModel.getResource().equals(Resource.REGION);
    }

    @Override
    public void Preview(String userIdentification, FullUserSpecification baseRef, ChangeModel changeModel) {
        baseRef.addMetadata(Resource.REGION.toString(), ChangeType.UPDATE);
        var regionId = changeModel.getArgs().get("regionId").toString();
        var allRegions = UserService.getAllPossibleRegions();
        var newRegion = allRegions.stream().filter(x -> x.getId().equals(regionId)).findFirst().get().getName();
        baseRef.setRegion(newRegion);
    }

    @Override
    public void Persist(String userIdentification, ChangeModel changeModel) {
        FirebaseUtils.setValue(Arrays.asList("User", getUserHash(userIdentification), "regionId"), changeModel.getArgs().get("regionId"));
    }
}
