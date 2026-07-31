package com.fieldservicemanagement.field_service_management.enums;

public enum SortDirection {
    ASC,
    DESC;

    public boolean isAscending() {
        return this == ASC;
    }

    public boolean isDescending() {
        return this == DESC;
    }
}
