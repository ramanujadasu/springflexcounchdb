package com.springflexcounchdb.common;

public class CouchDbOperationConstant {
    private CouchDbOperationConstant(){}
    public static final String FIND_ALL = "/_all_docs";
    public static final String FIND = "/_find";
    public static final String DELETE = "/_purge";
    public static final String ID_IN_JSON_FORMAT = "{\"_id\":%s}";
    public static final String REV_KEY_VAL = "\"_rev\":%s";
    public static final String REV_VAL = "%s";
}
