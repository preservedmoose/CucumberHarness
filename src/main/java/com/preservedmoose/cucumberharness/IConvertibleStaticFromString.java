package com.preservedmoose.cucumberharness;

//
// define an interface for conversion of string to object
// as we cannot create a static method on an interface
// we will need to instantiate an object to call this method on it
//
public interface IConvertibleStaticFromString<T>
    //where T : new()
{
    /// <summary>
    /// perform a case sensitive parse and create the object, if possible
    /// </summary>
    /// <param name="value">the string representation of the object</param>
    /// <param name="result">the created object</param>
    /// <returns>whether or not the string was successfully parsed to the object</returns>
    boolean TryParse(String value, /*out*/ T result);
}
