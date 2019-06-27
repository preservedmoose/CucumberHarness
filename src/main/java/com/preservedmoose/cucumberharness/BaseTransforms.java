package com.preservedmoose.cucumberharness;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

import lombok.var;

//[Binding]
public class BaseTransforms
{
    private final IConverter _converter;

    public BaseTransforms()
    {
        _converter = new Converter();
    }

    // ---------------------------------------------------------------------------------------

    /// <summary>
    /// implicit transformation
    /// </summary>
    /// <param name="fromValue">value</param>
    /// <returns>value converted to type</returns>
    //[StepArgumentTransformation(@"(.+)")]
    public Boolean TransformTo_Boolean(String fromValue)
    {
        var toValue = _converter.<Boolean>ToValue(fromValue);
        return toValue;
    }

    /// <summary>
    /// implicit transformation
    /// </summary>
    /// <param name="fromValue">value</param>
    /// <returns>value converted to type</returns>
    //[StepArgumentTransformation(@"(.+)")]
    public Short TransformTo_Short(String fromValue)
    {
        var toValue = _converter.<Short>ToValue(fromValue);
        return toValue;
    }

    /// <summary>
    /// implicit transformation
    /// </summary>
    /// <param name="fromValue">value</param>
    /// <returns>value converted to type</returns>
    //[StepArgumentTransformation(@"(.+)")]
    public Integer TransformTo_Integer(String fromValue)
    {
        var toValue = _converter.<Integer>ToValue(fromValue);
        return toValue;
    }

    /// <summary>
    /// implicit transformation
    /// </summary>
    /// <param name="fromValue">value</param>
    /// <returns>value converted to type</returns>
    //[StepArgumentTransformation(@"(.+)")]
    public Long TransformTo_Long(String fromValue)
    {
        var toValue = _converter.<Long>ToValue(fromValue);
        return toValue;
    }

    /// <summary>
    /// implicit transformation
    /// </summary>
    /// <param name="fromValue">value</param>
    /// <returns>value converted to type</returns>
    //[StepArgumentTransformation(@"(.+)")]
    public Float TransformTo_Float(String fromValue)
    {
        var toValue = _converter.<Float>ToValue(fromValue);
        return toValue;
    }

    /// <summary>
    /// implicit transformation
    /// </summary>
    /// <param name="fromValue">value</param>
    /// <returns>value converted to type</returns>
    //[StepArgumentTransformation(@"(.+)")]
    public Double TransformTo_Double(String fromValue)
    {
        var toValue = _converter.<Double>ToValue(fromValue);
        return toValue;
    }

    /// <summary>
    /// implicit transformation
    /// </summary>
    /// <param name="fromValue">value</param>
    /// <returns>value converted to type</returns>
    //[StepArgumentTransformation(@"(.+)")]
    public BigDecimal TransformTo_BigDecimal(String fromValue)
    {
        var toValue = _converter.<BigDecimal>ToValue(fromValue);
        return toValue;
    }

    /// <summary>
    /// implicit transformation
    /// </summary>
    /// <param name="fromValue">value</param>
    /// <returns>value converted to type</returns>
    //[StepArgumentTransformation(@"(.+)")]
    public LocalDate TransformTo_Date(String fromValue)
    {
        var toValue = _converter.<LocalDate>ToValue(fromValue);
        return toValue;
    }

    /// <summary>
    /// implicit transformation
    /// </summary>
    /// <param name="fromValue">value</param>
    /// <returns>value converted to type</returns>
    //[StepArgumentTransformation(@"(.+)")]
    public LocalDateTime TransformTo_DateTime(String fromValue)
    {
        var toValue = _converter.<LocalDateTime>ToValue(fromValue);
        return toValue;
    }

    // ---------------------------------------------------------------------------------------

    /// <summary>
    /// implicit transformation to collection
    /// </summary>
    /// <param name="fromValues">comma separated values</param>
    /// <returns>collection of values converted to type</returns>
    //[StepArgumentTransformation(@"(.+)")]
    public Collection<Boolean> TransformToCollection_Boolean(String fromValues)
    {
        var toValues = _converter.<Boolean>ToValues(fromValues);
        return toValues;
    }

    /// <summary>
    /// implicit transformation to collection
    /// </summary>
    /// <param name="fromValues">comma separated values</param>
    /// <returns>collection of values converted to type</returns>
    //[StepArgumentTransformation(@"(.+)")]
    public Collection<Short> TransformToCollection_Short(String fromValues)
    {
        var toValues = _converter.<Short>ToValues(fromValues);
        return toValues;
    }

    /// <summary>
    /// implicit transformation to collection
    /// </summary>
    /// <param name="fromValues">comma separated values</param>
    /// <returns>collection of values converted to type</returns>
    //[StepArgumentTransformation(@"(.+)")]
    public Collection<Integer> TransformToCollection_Integer(String fromValues)
    {
        var toValues = _converter.<Integer>ToValues(fromValues);
        return toValues;
    }

    /// <summary>
    /// implicit transformation to collection
    /// </summary>
    /// <param name="fromValues">comma separated values</param>
    /// <returns>collection of values converted to type</returns>
    //[StepArgumentTransformation(@"(.+)")]
    public Collection<Long> TransformToCollection_Long(String fromValues)
    {
        var toValues = _converter.<Long>ToValues(fromValues);
        return toValues;
    }

    /// <summary>
    /// implicit transformation to collection
    /// </summary>
    /// <param name="fromValues">comma separated values</param>
    /// <returns>collection of values converted to type</returns>
    //[StepArgumentTransformation(@"(.+)")]
    public Collection<Float> TransformToCollection_Float(String fromValues)
    {
        var toValues = _converter.<Float>ToValues(fromValues);
        return toValues;
    }

    /// <summary>
    /// implicit transformation to collection
    /// </summary>
    /// <param name="fromValues">comma separated values</param>
    /// <returns>collection of values converted to type</returns>
    //[StepArgumentTransformation(@"(.+)")]
    public Collection<Double> TransformToCollection_Double(String fromValues)
    {
        var toValues = _converter.<Double>ToValues(fromValues);
        return toValues;
    }

    /// <summary>
    /// implicit transformation to collection
    /// </summary>
    /// <param name="fromValues">comma separated values</param>
    /// <returns>collection of values converted to type</returns>
    //[StepArgumentTransformation(@"(.+)")]
    public Collection<BigDecimal> TransformToCollection_BigDecimal(String fromValues)
    {
        var toValues = _converter.<BigDecimal>ToValues(fromValues);
        return toValues;
    }

    /// <summary>
    /// implicit transformation to collection
    /// </summary>
    /// <param name="fromValues">comma separated values</param>
    /// <returns>collection of values converted to type</returns>
    //[StepArgumentTransformation(@"(.+)")]
    public Collection<LocalDate> TransformToCollection_Date(String fromValues)
    {
        var toValues = _converter.<LocalDate>ToValues(fromValues);
        return toValues;
    }

    /// <summary>
    /// implicit transformation to collection
    /// </summary>
    /// <param name="fromValues">comma separated values</param>
    /// <returns>collection of values converted to type</returns>
    //[StepArgumentTransformation(@"(.+)")]
    public Collection<LocalDateTime> TransformToCollection_DateTime(String fromValues)
    {
        var toValues = _converter.<LocalDateTime>ToValues(fromValues);
        return toValues;
    }

    /// <summary>
    /// implicit transformation to collection
    /// </summary>
    /// <param name="fromValues">comma separated values</param>
    /// <returns>collection of values converted to type</returns>
    //[StepArgumentTransformation(@"(.+)")]
    public Collection<String> TransformToCollection_String(String fromValues)
    {
        var toValues = _converter.<String>ToValues(fromValues);
        return toValues;
    }

    // ---------------------------------------------------------------------------------------
}
