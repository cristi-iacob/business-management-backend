package ubb.proiectColectiv.businessmanagementbackend.service.changeStrategies;

import ubb.proiectColectiv.businessmanagementbackend.model.ChangeModel;
import ubb.proiectColectiv.businessmanagementbackend.model.ChangeType;
import ubb.proiectColectiv.businessmanagementbackend.model.FullUserSpecification;
import ubb.proiectColectiv.businessmanagementbackend.model.Resource;
import ubb.proiectColectiv.businessmanagementbackend.service.BaseCompileChangeStrategy;
import ubb.proiectColectiv.businessmanagementbackend.utils.FirebaseUtils;

import java.util.Arrays;

public class UpdateLastNameChangeStrategy extends BaseCompileChangeStrategy {
    @Override
    public boolean CanCompile(ChangeModel changeModel) {
        return changeModel.getChangeType().equals(ChangeType.UPDATE) &&
                changeModel.getResource().equals(Resource.LAST_NAME);
    }

    @Override
    public void Preview(String userIdentification, FullUserSpecification baseRef, ChangeModel changeModel) {
        baseRef.addMetadata(Resource.LAST_NAME.toString(), ChangeType.UPDATE);
        baseRef.setLastName(changeModel.getArgs().get("lastName").toString());
    }

    @Override
    public void Persist(String userIdentification, ChangeModel changeModel) {
        FirebaseUtils.setValue(Arrays.asList("User", getUserHash(userIdentification), "lastName"), changeModel.getArgs().get("lastName"));
    }
}
