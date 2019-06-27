package com.preservedmoose.cucumberharness;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/// <summary>
/// These methods all take a string and attempt to return the expected type.
/// If the string is null or empty they will return the requested type (but empty).
/// Otherwise they will try and convert the string and, if they fail, will throw an IllegalArgumentException exception.
/// </summary>
public interface IConverter
{
    // ----------------------------------------------------------------------------------------

    /// <summary>
    /// helper to parse Value type
    /// There are custom overrides for these extra options:
    /// Boolean  - case insensitive 'Yes' and 'No'
    /// Int16    - the thousands separator
    /// Int32    - the thousands separator
    /// Int64    - the thousands separator
    /// DateTime - three ISO formats from the Constants class
    /// </summary>
    /// <typeparam name="TValue">the value type</typeparam>
    /// <param name="fromValue"></param>
    /// <returns></returns>
    <TValue>TValue ToValue(String fromValue);
    //where TValue : ValueType

    /// <summary>
    /// helper to parse Enum types
    /// </summary>
    /// <typeparam name="TEnum">the enum type</typeparam>
    /// <param name="fromValue">what we wish to parse</param>
    /// <returns></returns>
    <TEnum extends Enum>TEnum ToEnum(String fromValue);

    /// <summary>
    /// helper to parse object values
    /// </summary>
    /// <typeparam name="TObject">the object type</typeparam>
    /// <param name="fromValue"></param>
    /// <returns></returns>
    <TObject extends IConvertibleFromString<TObject>>TObject ToObject(String fromValue);
        //new();      factory.createDefault();

    /// <summary>
    /// helper to parse static object values
    /// </summary>
    /// <typeparam name="TObject">the static object type</typeparam>
    /// <param name="fromValue"></param>
    /// <returns></returns>
    <TObject extends IConvertibleStaticFromString<TObject>>TObject ToObjectStatic(String fromValue);
        //new();

    // ----------------------------------------------------------------------------------------

    /// <summary>
    /// helper to handle dates in the passed in value using the passed in format string
    /// sometimes useful when it is necessary to use formats outside of those supported by ToValue
    /// </summary>
    /// <param name="fromValue"></param>
    /// <param name="sFormat"></param>
    /// <returns></returns>
    LocalDate ToDate(String fromValue, String sFormat);

    // ----------------------------------------------------------------------------------------

    /// <summary>
    /// helper to handle dates in the passed in value using the passed in format string
    /// sometimes useful when it is necessary to use formats outside of those supported by ToValue
    /// </summary>
    /// <param name="fromValue"></param>
    /// <param name="sFormat"></param>
    /// <returns></returns>
    LocalDateTime ToDateTime(String fromValue, String sFormat);

    // ----------------------------------------------------------------------------------------

    /// <summary>
    /// helper to parse value types
    /// </summary>
    /// <typeparam name="TValue">the value type</typeparam>
    /// <param name="fromValues">a comma separated list that we wish to parse</param>
    /// <returns></returns>
    <TValue>List<TValue> ToValues(String fromValues);
    //where TValue : ValueType

    /// <summary>
    /// helper to parse Enum types
    /// </summary>
    /// <typeparam name="TEnum">the enum type</typeparam>
    /// <param name="fromValues">a comma separated list that we wish to parse</param>
    /// <returns></returns>
    <TEnum extends Enum>List<TEnum> ToEnums(String fromValues);
        //where TEnum : struct;

    /// <summary>
    /// helper to parse object types
    /// </summary>
    /// <typeparam name="TObject">the object type</typeparam>
    /// <param name="fromValues">a comma separated list that we wish to parse</param>
    /// <returns></returns>
    <TObject extends IConvertibleFromString<TObject>>List<TObject> ToObjects(String fromValues);
        //new();

    /// <summary>
    /// helper to parse static object types
    /// </summary>
    /// <typeparam name="TObject">the static object type</typeparam>
    /// <param name="fromValues">a comma separated list that we wish to parse</param>
    /// <returns></returns>
    <TObject extends IConvertibleStaticFromString<TObject>>List<TObject> ToObjectsStatic(String fromValues);
        //new();

    // ----------------------------------------------------------------------------------------
}
