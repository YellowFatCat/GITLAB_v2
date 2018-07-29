package com.epam.functions.apps.predicates;

import javax.json.JsonObject;
import java.util.function.Function;

public class AssembleCode implements Function<JsonObject, String> {

    public static final AssembleCode instance = new AssembleCode();


    @Override
    public String apply(JsonObject jsonObject) {

        return null;
    }


}
