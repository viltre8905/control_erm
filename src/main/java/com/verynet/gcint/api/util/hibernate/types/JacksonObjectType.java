package com.verynet.gcint.api.util.hibernate.types;

/**
 * Created by day on 30/04/2016.
 */
public class JacksonObjectType extends JacksonUserType {
    @Override
    public Class returnedClass() {
        return Object.class;
    }
}
