package ubb.proiectColectiv.businessmanagementbackend.service.changeStrategies;

import lombok.var;
import org.junit.jupiter.api.Test;
import ubb.proiectColectiv.businessmanagementbackend.model.*;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class UpdateFirstNameChangeStrategyTest {

    @Test
    void canCompileSuccess() {
        var changeModel = ChangeModel.builder().changeType(ChangeType.UPDATE).resource(Resource.FIRST_NAME).build();
        var changeStrategy = new UpdateFirstNameChangeStrategy();

        assertEquals(true, changeStrategy.CanCompile(changeModel));
    }

    @Test
    void canCompileFailure() {
        var changeModel = ChangeModel.builder().changeType(ChangeType.DELETE).resource(Resource.FIRST_NAME).build();
        var changeStrategy = new UpdateFirstNameChangeStrategy();

        assertEquals(false, changeStrategy.CanCompile(changeModel));
    }

    @Test
    void canCompileFailure2() {
        var changeModel = ChangeModel.builder().changeType(ChangeType.UPDATE).resource(Resource.LAST_NAME).build();
        var changeStrategy = new UpdateFirstNameChangeStrategy();

        assertEquals(false, changeStrategy.CanCompile(changeModel));
    }

    @Test
    void canCompileFailure3() {
        var changeModel = ChangeModel.builder().changeType(ChangeType.DELETE).resource(Resource.SKILL).build();
        var changeStrategy = new UpdateFirstNameChangeStrategy();

        assertEquals(false, changeStrategy.CanCompile(changeModel));
    }

    @Test
    void preview() {
        var first = "bogdan";
        var userSpec = FullUserSpecification.builder().firstName(first).build();
        var newVal = "cristi";
        var args = new HashMap<String, Object>();
        args.put("firstName", newVal);
        var changeStrategy = new UpdateFirstNameChangeStrategy();

        var changeModel = ChangeModel.builder().changeType(ChangeType.DELETE).resource(Resource.FIRST_NAME).args(args).build();

        assertEquals(userSpec.getFirstName(), first);
        changeStrategy.Preview("nuConteaza", userSpec, changeModel);
        assertEquals(userSpec.getFirstName(), newVal);
    }

    @Test
    void previewEmptyString() {
        var first = "bogdan";
        var userSpec = FullUserSpecification.builder().firstName(first).build();
        var newVal = new String();
        var args = new HashMap<String, Object>();
        args.put("firstName", newVal);
        var changeStrategy = new UpdateFirstNameChangeStrategy();

        var changeModel = ChangeModel.builder().changeType(ChangeType.DELETE).resource(Resource.FIRST_NAME).args(args).build();

        assertEquals(userSpec.getFirstName(), first);
        changeStrategy.Preview("nuConteaza", userSpec, changeModel);
        assertEquals(userSpec.getFirstName(), newVal);
    }

    @Test
    void previewNullString() {
        var first = "bogdan";
        var userSpec = FullUserSpecification.builder().firstName(first).build();
        String newVal = null;
        var args = new HashMap<String, Object>();
        args.put("firstName", newVal);
        var changeStrategy = new UpdateFirstNameChangeStrategy();

        var changeModel = ChangeModel.builder().changeType(ChangeType.DELETE).resource(Resource.FIRST_NAME).args(args).build();

        assertThrows(NullPointerException.class, () -> changeStrategy.Preview("nuConteaza", userSpec, changeModel));
    }

    @Test
    void previewFailure() {
        var first = "bogdan";
        var userSpec = FullUserSpecification.builder().firstName(first).build();
        var newVal = "cristi";
        var args = new HashMap<String, Object>();
        args.put("false", newVal);
        var changeStrategy = new UpdateFirstNameChangeStrategy();

        var changeModel = ChangeModel.builder().changeType(ChangeType.DELETE).resource(Resource.FIRST_NAME).args(args).build();

        assertThrows(NullPointerException.class, () -> changeStrategy.Preview("asd", userSpec, changeModel));
    }
}