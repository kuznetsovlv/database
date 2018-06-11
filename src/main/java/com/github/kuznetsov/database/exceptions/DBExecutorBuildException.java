package com.github.kuznetsov.database.exceptions;

import java.util.List;

/**
 *
 * @author leonid
 */
public class DBExecutorBuildException extends Exception{
    private final List<String> unconfigurated;

    public DBExecutorBuildException(List<String> unconfigurated) {
        super("Cannot build executor because some fields are not configured.");
        this.unconfigurated = unconfigurated;
    }
    
    public List<String> getUnconfigurated() {
        return unconfigurated;
    }
}
