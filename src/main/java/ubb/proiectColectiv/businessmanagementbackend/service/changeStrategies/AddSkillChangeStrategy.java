package ubb.proiectColectiv.businessmanagementbackend.service.changeStrategies;

import lombok.var;
import ubb.proiectColectiv.businessmanagementbackend.model.*;
import ubb.proiectColectiv.businessmanagementbackend.service.BaseCompileChangeStrategy;
import ubb.proiectColectiv.businessmanagementbackend.service.UserService;
import ubb.proiectColectiv.businessmanagementbackend.utils.FirebaseUtils;

import java.util.Arrays;
import java.util.HashMap;

public class AddSkillChangeStrategy extends BaseCompileChangeStrategy {
    @Override
    public boolean CanCompile(ChangeModel changeModel) {
        return changeModel.getResource().equals(Resource.SKILL)
                && changeModel.getChangeType().equals(ChangeType.ADD);
    }

    @Override
    public void Preview(String userIdentification, FullUserSpecification baseRef, ChangeModel changeModel) {
        var args = changeModel.getArgs();
        var skillId = args.get("id").toString();
        var skill = UserService.patchSkillWithId(skillId);
        skill.setItemState(ItemState.ADDED);
        baseRef.getSkills().add(skill);
    }

    @Override
    public void Persist(String userIdentification, ChangeModel changeModel) {
        var args = changeModel.getArgs();
        var skillId = args.get("id").toString();
        var nextId = UserService.getNextUserSkillId();
        var map = new HashMap<String, String>();
        map.put("userHash", getUserHash(userIdentification));
        map.put("skillId", skillId);
        FirebaseUtils.setValue(Arrays.asList("UserSkills", nextId), map);
    }
}
