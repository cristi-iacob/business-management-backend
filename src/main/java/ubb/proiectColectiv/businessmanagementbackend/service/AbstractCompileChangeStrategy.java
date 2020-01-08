package ubb.proiectColectiv.businessmanagementbackend.service;

import lombok.Getter;
import lombok.var;
import ubb.proiectColectiv.businessmanagementbackend.model.ChangeModel;
import ubb.proiectColectiv.businessmanagementbackend.model.FullUserSpecification;
import ubb.proiectColectiv.businessmanagementbackend.utils.FirebaseUtils;

import java.util.Arrays;
import java.util.List;

@Getter
public abstract class AbstractCompileChangeStrategy implements CompileChangeStrategy {

    @Override
    abstract public boolean CanCompile(ChangeModel changeModel);

    @Override
    public FullUserSpecification Preview(String userIdentification, ChangeModel changeModel) {
        var fullUserSpecification = GetPersistedUserSpecificationForIdentification(userIdentification);
        return Compile(fullUserSpecification, changeModel, false);
    }

    private FullUserSpecification GetPersistedUserSpecificationForIdentification(String userIdentification) {
        var path = GetPath(userIdentification);
        return new FullUserSpecification(); // compute specification
    }

    private List<String> GetPath(String locationSpecifier) {
        return Arrays.asList("Users", locationSpecifier);
    }

    // not silent will log top-level changes in a metadata field on the user specification object and
    // deletions will we handled only by specifying a different state and
    // additions will be marked with a different state
    // silent set to true changes all these in place without "logging" any operation (ItemState = PERSISTED)
    abstract protected FullUserSpecification Compile(FullUserSpecification base, ChangeModel changeModel, boolean silent);

    @Override
    public void Persist(String userIdentification, ChangeModel changeModel) {
        var fullUserSpecification = GetPersistedUserSpecificationForIdentification(userIdentification);
        var compiled = Compile(fullUserSpecification, changeModel, true);
        FirebaseUtils.setValue(GetPath(userIdentification), compiled);
    }
}
