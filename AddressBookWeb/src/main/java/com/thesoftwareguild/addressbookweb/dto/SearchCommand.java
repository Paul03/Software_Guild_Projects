package com.thesoftwareguild.addressbookweb.dto;

public class SearchCommand {

    private String fieldToSearch;
    private String valueToSearchFor;

    public String getFieldToSearch() {
        return fieldToSearch;
    }

    public void setFieldToSearch(String fieldToSearch) {
        this.fieldToSearch = fieldToSearch;
    }

    public String getValueToSearchFor() {
        return valueToSearchFor;
    }

    public void setValueToSearchFor(String valueToSearchFor) {
        this.valueToSearchFor = valueToSearchFor;
    }

}
