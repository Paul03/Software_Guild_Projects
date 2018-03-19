package com.thesoftwareguild.dvdlibraryweb.aop;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorContainer {

    private List<ValidationError> errorList = new ArrayList<>();

    public List<ValidationError> getErrorList() {
        return errorList;
    }

    public void addError(ValidationError error) {
        errorList.add(error);
    }
}
