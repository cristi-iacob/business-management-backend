package ubb.proiectColectiv.businessmanagementbackend.service.changeStrategies;

import lombok.var;
import ubb.proiectColectiv.businessmanagementbackend.model.*;
import ubb.proiectColectiv.businessmanagementbackend.service.BaseCompileChangeStrategy;
import ubb.proiectColectiv.businessmanagementbackend.utils.FirebaseUtils;

import java.util.Arrays;

public class DeleteSkillChangeStrategy extends BaseCompileChangeStrategy {

    @Override
    public boolean CanCompile(ChangeModel changeModel) {
        return changeModel.getResource().equals(Resource.SKILL)
                && changeModel.getChangeType().equals(ChangeType.DELETE);
    }

    @Override
    public void Preview(String userIdentification, FullUserSpecification baseRef, ChangeModel changeModel) {
        var args = changeModel.getArgs();
        var skillId = args.get("id").toString();

        baseRef.getSkills().forEach(s -> {
            if(s.getId().equals(skillId)) {
                s.setItemState(ItemState.DELETED);
            }
        });
    }

    @Override
    public void Persist(String userIdentification, ChangeModel changeModel) {
        var args = changeModel.getArgs();
        var skillId = args.get("id").toString();
        FirebaseUtils.removeValue(Arrays.asList("UserSkills", skillId));
    }
}
