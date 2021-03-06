package com.reactnativenavigation.parse.parsers;


import com.reactnativenavigation.parse.params.Interpolation;

import org.json.JSONObject;

public class InterpolationParser {
    public static Interpolation parse(JSONObject json, String intepolation) {
        Interpolation result = Interpolation.DEFAULT;
        if (json.has(intepolation)) {
            switch (json.optString(intepolation)) {
                case "decelerate":
                    result = Interpolation.DECELERATING;
                    break;
                case "accelerate":
                    result = Interpolation.ACCELERATING;
                    break;
            }
        }
        return result;
    }
}
