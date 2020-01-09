package ubb.proiectColectiv.businessmanagementbackend.service;

import ubb.proiectColectiv.businessmanagementbackend.model.ChangeModel;
import ubb.proiectColectiv.businessmanagementbackend.model.FullUserSpecification;

public interface CompileChangeStrategy {

    // check if strategy can be used with certain arguments
    // use it to brute-force search for a strategy in a collection which works in the given context
    boolean CanCompile(ChangeModel changeModel);

    // chain multiple previews from multiple strategies in order to obtain the "diff" object
    void Preview(String userIdentification, FullUserSpecification baseRef, ChangeModel changeModel);

    // chain multiple persists from multiple strategies in order to empty changes object from an user entry
    // each call will update the db
    void Persist(String userIdentification, ChangeModel changeModel);
}
