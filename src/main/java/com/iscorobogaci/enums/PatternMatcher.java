package com.iscorobogaci.enums;

public enum PatternMatcher {
    IMAGE("glob:**.{jpg,jpeg}"),
    VIDEO("glob:**.{mp4,mov}");

    private String regex;

    PatternMatcher(String regex) {
        this.regex = regex;
    }

    public String value() {
        return this.regex;
    }
}
