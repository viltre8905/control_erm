package com.verynet.gcint.api.util.hibernate.types;

import com.google.gson.JsonObject;

/**
 * Created by day on 30/04/2016.
 */
public class JacksonJsonObjectType extends JacksonUserType {
    @Override
    public Class returnedClass() {
        return JsonObject.class;
    }
}
