package com.preservedmoose.cucumberharness;

//
// define an interface for conversion of string to object
//
// ReSharper disable once UnusedTypeParameter
public interface IConvertibleFromString<T>
    //where T : new()
{
    /// <summary>
    /// perform a case sensitive parse and create the object, if possible
    /// </summary>
    /// <param name="value">the string representation of the object</param>
    /// <returns>whether or not the string was successfully parsed to the object</returns>
    boolean TryParse(String value);
}
