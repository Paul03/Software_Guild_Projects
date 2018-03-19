/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thesoftwareguild.addressbookweb.aop;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulharding
 */
public class ValidationErrorContainer {
    
    private List<ValidationError> errorList = new ArrayList();

    public List<ValidationError> getErrorList() {
        return errorList;
    }
    
    public void addError(ValidationError error) {
        errorList.add(error);
    }
}
