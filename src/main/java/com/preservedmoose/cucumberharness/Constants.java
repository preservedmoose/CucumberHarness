package com.preservedmoose.cucumberharness;

public class Constants
{
    public static final String CommaSeparator = ", ";
 
    public static final int HashPrime = 397;

    public static final String IsoYearMonth = "yyyy-MM";

    public static final String IsoDate = "yyyy-MM-dd";

    public static final String IsoDateTime = "yyyy-MM-dd HH:mm:ss";
    public static final String IsoDateTimePrecisionMinutes = "yyyy-MM-dd HH:mm";

    public static final String[] IsoDateTimePrecisionMilliseconds =
    {
        "yyyy-MM-dd HH:mm:ss.f",
        "yyyy-MM-dd HH:mm:ss.ff",
        "yyyy-MM-dd HH:mm:ss.fff",
        "yyyy-MM-dd HH:mm:ss.ffff",
        "yyyy-MM-dd HH:mm:ss.fffff",
        "yyyy-MM-dd HH:mm:ss.ffffff",
        "yyyy-MM-dd HH:mm:ss.fffffff"
    };
}
