package com.preservedmoose.cucumberharness;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.preservedmoose.cucumberharness.Application.Resources;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

import lombok.var;

public class Converter implements IConverter
{
    public static IConverter Instance() { return new Converter(); }

    private final IParser _parser;

    // ----------------------------------------------------------------------------------------

    public Converter()
    {
        _parser = new Parser();
    }

    // ----------------------------------------------------------------------------------------

    public <TValue>TValue ToValue(String fromValue)
    //where TValue : ValueType
    {
        var type = typeof(TValue);
        dynamic toValue = default(TValue);

        var sValue = ToCleanString(fromValue);
        if (sValue == StringUtils.EMPTY) return toValue;

        // ReSharper disable once RedundantAssignment
        var isValid = false;

        // the per-type methods handle custom parsing
        if (type instanceof Boolean)
        {
            Boolean value;
            isValid = _parser.TryParseBoolean(sValue, /*out*/ value);
            if (isValid) toValue = value;
        }
        else if (type instanceof Short)
        {
            Short value;
            isValid = _parser.TryParseShort(sValue, /*out*/ value);
            if (isValid) toValue = value;
        }
        else if (type instanceof Integer)
        {
            Integer value;
            isValid = _parser.TryParseInteger(sValue, /*out*/ value);
            if (isValid) toValue = value;
        }
        else if (type instanceof Long)
        {
            Long value;
            isValid = _parser.TryParseLong(sValue, /*out*/ value);
            if (isValid) toValue = value;
        }
        else if (type instanceof LocalDate)
        {
            LocalDate value;
            isValid = _parser.TryParseDate(sValue, /*out*/ value);
            if (isValid) toValue = value;
        }
        else if (type instanceof LocalDateTime)
        {
            LocalDateTime value;
            isValid = _parser.TryParseDateTime(sValue, /*out*/ value);
            if (isValid) toValue = value;
        }
        else    // just parse it normally
        {
            TValue value;
            isValid = _parser.TryParseValue(sValue, /*out*/ value);
            if (isValid) toValue = value;
        }

        if (!isValid)
        {
            throw new IllegalArgumentException(String.format(Resources.Get("Converter_InvalidValue"), sValue, type));
        }
        return toValue;
    }

    public <TEnum>TEnum ToEnum(String fromValue)
        //where TEnum : struct
    {
        //var type = typeof(TEnum);
        //var toEnum = default(TEnum);

        var sValue = ToCleanString(fromValue);
        if (sValue == StringUtils.EMPTY) return null;
        //if (sValue == StringUtils.EMPTY) return toEnum;

        var isValid1 = EnumUtils.isValidEnum(TEnum.class, sValue);
        var isValid = Enum.TryParse(sValue, true, /*out*/ toEnum);

        if (!isValid)
        {
            // try and look at the descriptions (if they exist)
            for (TEnum enumNext : Enum.GetValues(typeof(TEnum)))
            {
                if (0 != String.Compare(_parser.ReadEnumDescription(enumNext), sValue, true, CultureInfo.InvariantCulture)) continue;

                toEnum = enumNext;
                isValid = true;
                break;
            }
        }

        if (!isValid)
        {
            throw new IllegalArgumentException(String.format(Resources.Get("Converter_InvalidValue"), sValue, type));
        }
        return toEnum;
    }

    public <TObject extends IConvertibleFromString<TObject>>TObject ToObject(String fromValue)
    {
        var type = typeof(TObject);
        var toObject = new TObject();

        var sValue = ToCleanString(fromValue);
        if (sValue == StringUtils.EMPTY) return toObject;

        var isValid = toObject.TryParse(sValue);

        if (!isValid)
        {
            throw new IllegalArgumentException(String.format(Resources.Get("Converter_InvalidValue"), sValue, type));
        }
        return toObject;
    }

    public <TObject extends IConvertibleStaticFromString<TObject>>TObject ToObjectStatic(String fromValue)
    {
        // for calls that return static objects
        // we must create an instance on which to call the method
        // and then return the static object, not the one created
        var type = typeof(TObject);
        var toObject = new TObject();

        var sValue = ToCleanString(fromValue);
        if (sValue == StringUtils.EMPTY) return toObject;

        var temporary = new TObject();
        var isValid = temporary.TryParse(sValue, /*out*/ toObject);

        if (!isValid)
        {
            throw new IllegalArgumentException(String.format(Resources.Get("Converter_InvalidValue"), sValue, type));
        }
        return toObject;
    }

    // ----------------------------------------------------------------------------------------

    public LocalDate ToDate(String fromValue, String format)
    {
        var type = typeof(LocalDate);
        var toDate = default(LocalDate);

        var sValue = ToCleanString(fromValue);
        if (sValue == StringUtils.EMPTY) return toDate;

        var isValid = LocalDate.TryParseExact(sValue, format, CultureInfo.InvariantCulture, DateTimeStyles.None, /*out*/ toDate);

        if (!isValid)
        {
            throw new IllegalArgumentException(String.format(Resources.Get("Converter_InvalidValue"), sValue, type));
        }
        return toDate;
    }

    // ----------------------------------------------------------------------------------------

    public LocalDateTime ToDateTime(String fromValue, String format)
    {
        var type = typeof(LocalDateTime);
        var toDateTime = default(LocalDateTime);

        var sValue = ToCleanString(fromValue);
        if (sValue == StringUtils.EMPTY) return toDateTime;

        var isValid = LocalDateTime.TryParseExact(sValue, format, CultureInfo.InvariantCulture, DateTimeStyles.None, /*out*/ toDateTime);

        if (!isValid)
        {
            throw new IllegalArgumentException(String.format(Resources.Get("Converter_InvalidValue"), sValue, type));
        }
        return toDateTime;
    }

    // ----------------------------------------------------------------------------------------

    public <TValue>List<TValue> ToValues(String fromValues)
    //where TValue : ValueType
    {
        var toValues = new ArrayList<TValue>();

        var sValues = ToCleanString(fromValues);
        if (sValues == StringUtils.EMPTY) return toValues;

        var valuesArray = sValues.split(Constants.CommaSeparator);

        for (var value : valuesArray)
        {
            toValues.Add(ToValue<TValue>(value));
        }
        return toValues;
    }

    public <TEnum>List<TEnum> ToEnums(String fromValues)
        //where TEnum : struct
    {
        var toEnums = new ArrayList<TEnum>();

        var sValues = ToCleanString(fromValues);
        if (sValues == StringUtils.EMPTY) return toEnums;

        var valuesArray = sValues.split(Constants.CommaSeparator);

        for (var value : valuesArray)
        {
            toEnums.Add(ToEnum<TEnum>(value));
        }
        return toEnums;
    }

    public <TObject extends IConvertibleFromString<TObject>>List<TObject> ToObjects(String fromValues)
    {
        var toObjects = new ArrayList<TObject>();

        var sValues = ToCleanString(fromValues);
        if (sValues == StringUtils.EMPTY) return toObjects;

        var valuesArray = sValues.split(Constants.CommaSeparator);

        for (var value : valuesArray)
        {
            toObjects.Add(ToObject<TObject>(value));
        }
        return toObjects;
    }

    public <TObject extends IConvertibleStaticFromString<TObject>>List<TObject> ToObjectsStatic(String fromValues)
    {
        var toObjects = new ArrayList<TObject>();

        var sValues = ToCleanString(fromValues);
        if (sValues == StringUtils.EMPTY) return toObjects;

        var valuesArray = sValues.split(Constants.CommaSeparator);

        for (var value : valuesArray)
        {
            toObjects.Add(ToObjectStatic<TObject>(value));
        }
        return toObjects;
    }

    // ----------------------------------------------------------------------------------------

    /// <summary>
    /// To a compact String, removing spaces.
    /// </summary>
    /// <param name="fromValue">From value.</param>
    /// <param name="removeSpacing">Remove spaces inside the text.</param>
    /// <returns></returns>
    private static String ToCleanString(String fromValue)
    {
        return ToCleanString(fromValue, false);
    }

    /// <summary>
    /// To a compact String, removing spaces.
    /// </summary>
    /// <param name="fromValue">From value.</param>
    /// <param name="removeSpacing">Remove spaces inside the text.</param>
    /// <returns></returns>
    private static String ToCleanString(String fromValue, Boolean removeSpacing)
    {
        var toValue = StringUtils.EMPTY;
        if (StringUtils.isNotBlank(fromValue)) toValue = fromValue.trim();

        if (removeSpacing) toValue = toValue.replace(StringUtils.SPACE, StringUtils.EMPTY);
        return toValue;
    }

    // ----------------------------------------------------------------------------------------
}
