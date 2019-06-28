package com.preservedmoose.cucumberharness;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/// <summary>
/// This class is slightly different from the Converter class
/// It is specifically for the use of the transformation logic in the StepRow class
/// </summary>
public interface IParser
{
    // ----------------------------------------------------------------------------------------

    /// <summary>
    /// returns the value in the description attribute for the Enum value
    /// </summary>
    /// <param name="enumType"></param>
    /// <returns></returns>
    <TEnum>String ReadEnumDescription(TEnum enumType);

    // ----------------------------------------------------------------------------------------

    /// <summary>
    /// helper to allow yes/no values
    /// </summary>
    /// <param name="column"></param>
    /// <param name="fromValue"></param>
    /// <param name="parseError"></param>
    /// <returns></returns>
    Boolean ParseBoolean(String column, String fromValue, String parseError);

    /// <summary>
    /// helper to allow comma separated values
    /// </summary>
    /// <param name="column"></param>
    /// <param name="fromValue"></param>
    /// <param name="parseError"></param>
    /// <returns></returns>
    Short ParseShort(String column, String fromValue, String parseError);

    /// <summary>
    /// helper to allow comma separated values
    /// </summary>
    /// <param name="column"></param>
    /// <param name="fromValue"></param>
    /// <param name="parseError"></param>
    /// <returns></returns>
    Integer ParseInteger(String column, String fromValue, String parseError);

    /// <summary>
    /// helper to allow comma separated values
    /// </summary>
    /// <param name="column"></param>
    /// <param name="fromValue"></param>
    /// <param name="parseError"></param>
    /// <returns></returns>
    Long ParseLong(String column, String fromValue, String parseError);

    /// <summary>
    /// helper to allow comma separated values
    /// </summary>
    /// <param name="column"></param>
    /// <param name="fromValue"></param>
    /// <param name="parseError"></param>
    /// <returns></returns>
    BigDecimal ParseBigDecimal(String column, String fromValue, String parseError);

    /// <summary>
    /// helper to handle dates in the passed in value (two formats supported)
    /// </summary>
    /// <param name="column"></param>
    /// <param name="fromValue"></param>
    /// <param name="parseError"></param>
    /// <returns></returns>
    LocalDate ParseDate(String column, String fromValue, String parseError);

    /// <summary>
    /// helper to handle dates in the passed in value (two formats supported)
    /// </summary>
    /// <param name="column"></param>
    /// <param name="fromValue"></param>
    /// <param name="parseError"></param>
    /// <returns></returns>
    LocalDateTime ParseDateTime(String column, String fromValue, String parseError);

    /// <summary>
    /// helper to parse Enum types
    /// </summary>
    /// <typeparam name="TEnum">the enum type</typeparam>
    /// <param name="column">the column name displayed in any error</param>
    /// <param name="fromValue">what we wish to parse</param>
    /// <param name="parseError">the error String returned if the conversion failed</param>
    /// <returns></returns>
    <TEnum>TEnum ParseEnum(String column, String fromValue, String parseError);
        //where TEnum : struct;

    /// <summary>
    /// helper to parse object values
    /// </summary>
    /// <param name="column"></param>
    /// <param name="fromValue"></param>
    /// <param name="parseError"></param>
    /// <returns></returns>
    <TObject extends IConvertibleFromString<TObject>>TObject ParseObject(String column, String fromValue, String parseError);
        //new();

    /// <summary>
    /// helper to parse object values
    /// </summary>
    /// <param name="column"></param>
    /// <param name="fromValue"></param>
    /// <param name="parseError"></param>
    /// <returns></returns>
    <TObject extends IConvertibleStaticFromString<TObject>>TObject ParseObjectStatic(String column, String fromValue, String parseError);
        //new();

    // ----------------------------------------------------------------------------------------

    /// <summary>
    /// Tries to parse to a Boolean, handling case insensitive 'Yes' and 'No'.
    /// </summary>
    /// <param name="fromValue">From value.</param>
    /// <param name="toValue">The return value.</param>
    /// <returns></returns>
    boolean TryParseBoolean(String fromValue, Boolean toValue);

    /// <summary>
    /// Tries to parse an Short, allowing for the thousands separator.
    /// </summary>
    /// <param name="fromValue">From value.</param>
    /// <param name="toValue">The return value.</param>
    /// <returns></returns>
    boolean TryParseShort(String fromValue, Short toValue);

    /// <summary>
    /// Tries to parse an Integer, allowing for the thousands separator.
    /// </summary>
    /// <param name="fromValue">From value.</param>
    /// <param name="toValue">The return value.</param>
    /// <returns></returns>
    boolean TryParseInteger(String fromValue, Integer toValue);

    /// <summary>
    /// Tries to parse an Long, allowing for the thousands separator.
    /// </summary>
    /// <param name="fromValue">From value.</param>
    /// <param name="toValue">The return value.</param>
    /// <returns></returns>
    boolean TryParseLong(String fromValue, Long toValue);

    /// <summary>
    /// Tries to parse a BigDecimal
    /// </summary>
    /// <param name="fromValue">From value.</param>
    /// <param name="toValue">The return value.</param>
    /// <returns></returns>
    boolean TryParseBigDecimal(String fromValue, BigDecimal toValue);

    /// <summary>
    /// Tries to parse a LocalDate, allowing for three ISO formats from the Constants class.
    /// </summary>
    /// <param name="fromValue">From value.</param>
    /// <param name="toValue">The return value.</param>
    /// <returns></returns>
    boolean TryParseDate(String fromValue, LocalDate toValue);

    /// <summary>
    /// Tries to parse a LocalDateTime, allowing for three ISO formats from the Constants class.
    /// </summary>
    /// <param name="fromValue">From value.</param>
    /// <param name="toValue">The return value.</param>
    /// <returns></returns>
    boolean TryParseDateTime(String fromValue, LocalDateTime toValue);

    /// <summary>
    /// Tries the parse enum.
    /// </summary>
    /// <typeparam name="TEnum">The type of the enum.</typeparam>
    /// <param name="fromValue">From value.</param>
    /// <param name="toValue">To value.</param>
    /// <returns></returns>
    <TEnum>boolean TryParseEnum(String fromValue, TEnum toValue);

    // ----------------------------------------------------------------------------------------

}
