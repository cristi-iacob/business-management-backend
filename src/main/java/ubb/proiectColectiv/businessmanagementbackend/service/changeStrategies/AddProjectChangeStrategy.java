package ubb.proiectColectiv.businessmanagementbackend.service.changeStrategies;

import ubb.proiectColectiv.businessmanagementbackend.model.ChangeModel;
import ubb.proiectColectiv.businessmanagementbackend.model.ChangeType;
import ubb.proiectColectiv.businessmanagementbackend.model.FullUserSpecification;
import ubb.proiectColectiv.businessmanagementbackend.model.Resource;
import ubb.proiectColectiv.businessmanagementbackend.service.BaseCompileChangeStrategy;

public class AddProjectChangeStrategy extends BaseCompileChangeStrategy {
    @Override
    public boolean CanCompile(ChangeModel changeModel) {
        return changeModel.getResource().equals(Resource.PROJECT)
                && changeModel.getChangeType().equals(ChangeType.ADD);
    }

    @Override
    public void Preview(String userIdentification, FullUserSpecification baseRef, ChangeModel changeModel) {

    }

    @Override
    public void Persist(String userIdentification, ChangeModel changeModel) {

    }
}
