package com.preservedmoose.cucumberharness;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.preservedmoose.cucumberharness.Application.Resources;

import org.apache.commons.lang3.StringUtils;

import lombok.var;

public class Parser implements IParser
{
    // ----------------------------------------------------------------------------------------

    public <TEnum>String ReadEnumDescription(TEnum enumType)
        //where TEnum : struct
    {
        var fieldInfo = enumType.GetType().GetField(enumType.ToString());
        var attributes = (DescriptionAttribute[])fieldInfo.GetCustomAttributes(typeof(DescriptionAttribute), false);

        var description = (attributes.Length > 0) ? attributes[0].Description : typeof(TEnum).ToString();
        return description;
    }

    // ----------------------------------------------------------------------------------------

    public boolean ParseBoolean(String column, String fromValue, /*ref*/ String parseError)
    {
        boolean value;
        var isSuccess = TryParseBoolean(fromValue, /*out*/ value);
        if (!isSuccess)
        {
            parseError = String.Format(Resources.Get("Parser_InvalidValue"), column, fromValue, typeof(boolean));
        }
        return value;
    }

    public short ParseShort(String column, String fromValue, /*ref*/ String parseError)
    {
        short value;
        var isSuccess = TryParseInt16(fromValue, /*out*/ value);
        if (!isSuccess)
        {
            parseError = String.Format(Resources.Get("Parser_InvalidValue"), column, fromValue, typeof(short));
        }
        return value;
    }

    public int ParseInteger(String column, String fromValue, /*ref*/ String parseError)
    {
        int value;
        var isSuccess = TryParseInt32(fromValue, /*out*/ value);
        if (!isSuccess)
        {
            parseError = String.Format(Resources.Get("Parser_InvalidValue"), column, fromValue, typeof(int));
        }
        return value;
    }

    public long ParseLong(String column, String fromValue, /*ref*/ String parseError)
    {
        long value;
        var isSuccess = TryParseInt64(fromValue, /*out*/ value);
        if (!isSuccess)
        {
            parseError = String.Format(Resources.Get("Parser_InvalidValue"), column, fromValue, typeof(long));
        }
        return value;
    }

    public LocalDate ParseDate(String column, String fromValue, /*ref*/ String parseError)
    {
        LocalDateTime value;
        var isSuccess = TryParseDate(fromValue, /*out*/ value);
        if (!isSuccess)
        {
            parseError = String.Format(Resources.Get("Parser_InvalidValue"), column, fromValue, typeof(DateTime));
        }
        return value;
    }

    public LocalDateTime ParseDateTime(String column, String fromValue, /*ref*/ String parseError)
    {
        LocalDateTime value;
        var isSuccess = TryParseDateTime(fromValue, /*out*/ value);
        if (!isSuccess)
        {
            parseError = String.Format(Resources.Get("Parser_InvalidValue"), column, fromValue, typeof(DateTime));
        }
        return value;
    }

    public Object ParseValue(Type type, String column, String fromValue, /*ref*/ String parseError)
    {
        ValueType value = null;
        var exceptionThrown = false;

        try
        {
            value = (ValueType)Convert.ChangeType(fromValue, type);
        }
        catch (FormatException)
        {
            exceptionThrown = true;
        }
        catch (OverflowException)
        {
            exceptionThrown = true;
        }

        if (exceptionThrown)
        {
            parseError = String.Format(Resources.Get("Parser_InvalidValue"), column, fromValue, type);
        }
        return value;
    }

    public <TEnum>TEnum ParseEnum(String column, String fromValue, /*ref*/ String parseError)
        //where TEnum : struct
    {
        TEnum value;
        var isSuccess = TryParseEnum(fromValue, /*out*/ value);
        if (!isSuccess)
        {
            parseError = String.Format(Resources.Get("Parser_InvalidValue"), column, fromValue, typeof(TEnum));
        }
        return value;
    }

    public <TObject extends IConvertibleFromString<TObject>>TObject ParseObject(String column, String fromValue, /*ref*/ String parseError)
    {
        var value = new TObject();
        if (value.TryParse(fromValue)) return value;

        parseError = String.Format(Resources.Get("Parser_InvalidValue"), column, fromValue, typeof(TObject));
        return value;
    }

    public <TObject extends IConvertibleStaticFromString<TObject>>TObject ParseObjectStatic(String column, String fromValue, /*ref*/ String parseError)
    {
        // for calls that return static objects
        // we must create an instance on which to call the method
        // and then return the static object, not the one created
        TObject value;
        var temporary = new TObject();
        if (temporary.TryParse(fromValue, /*out*/ value)) return value;

        parseError = String.Format(Resources.Get("Parser_InvalidValue"), column, fromValue, typeof(TObject));
        return value;
    }

    // ----------------------------------------------------------------------------------------

    public boolean TryParseBoolean(String fromValue, /*out*/ boolean toValue)
    {
        var isSuccess = boolean.TryParse(fromValue, /*out*/ toValue);

        // allow yes/no values
        if (!isSuccess)
        {
            if (0 == String.Compare(fromValue, Resources.Get("Parser_No"), true, CultureInfo.InvariantCulture) ||
                0 == String.Compare(fromValue, Resources.Get("Parser_Yes"), true, CultureInfo.InvariantCulture))
            {
                toValue = (0 == String.Compare(fromValue, Resources.Get("Parser_Yes"), true, CultureInfo.InvariantCulture));
                isSuccess = true;
            }
        }
        return isSuccess;
    }

    public boolean TryParseShort(String fromValue, /*out*/ short toValue)
    {
        // allow comma separated values
        var isSuccess = short.TryParse(fromValue, NumberStyles.Integer | NumberStyles.AllowThousands, CultureInfo.InvariantCulture, /*out*/ toValue);
        return isSuccess;
    }

    public boolean TryParseInteger(String fromValue, /*out*/ int toValue)
    {
        // allow comma separated values
        var is = NumberUtils.isCreatable(fromValue);
        var isSuccess = int.TryParse(fromValue, NumberStyles.Integer | NumberStyles.AllowThousands, CultureInfo.InvariantCulture, /*out*/ toValue);
        return isSuccess;
    }

    public boolean TryParseLong(String fromValue, /*out*/ long toValue)
    {
        // allow comma separated values
        var isSuccess = long.TryParse(fromValue, NumberStyles.Integer | NumberStyles.AllowThousands, CultureInfo.InvariantCulture, /*out*/ toValue);
        return isSuccess;
    }

    public boolean TryParseDate(String fromValue, /*out*/ LocalDate toValue)
    {
        // we are only interested in ISO 8601 based dates
        var isSuccess =
            LocalDate.TryParseExact(fromValue, Constants.IsoDate, CultureInfo.InvariantCulture, DateTimeStyles.None, /*out*/ toValue) ||
            LocalDate.TryParseExact(fromValue, Constants.IsoDateTime, CultureInfo.InvariantCulture, DateTimeStyles.None, /*out*/ toValue) ||
            LocalDate.TryParseExact(fromValue, Constants.IsoDateTimePrecisionMinutes, CultureInfo.InvariantCulture, DateTimeStyles.None, /*out*/ toValue) ||
            LocalDate.TryParseExact(fromValue, Constants.IsoDateTimePrecisionMilliseconds, CultureInfo.InvariantCulture, DateTimeStyles.None, /*out*/ toValue);

        return isSuccess;
    }

    public boolean TryParseDateTime(String fromValue, /*out*/ LocalDateTime toValue)
    {
        // we are only interested in ISO 8601 based dates
        var isSuccess =
            LocalDateTime.TryParseExact(fromValue, Constants.IsoDate, CultureInfo.InvariantCulture, DateTimeStyles.None, /*out*/ toValue) ||
            LocalDateTime.TryParseExact(fromValue, Constants.IsoDateTime, CultureInfo.InvariantCulture, DateTimeStyles.None, /*out*/ toValue) ||
            LocalDateTime.TryParseExact(fromValue, Constants.IsoDateTimePrecisionMinutes, CultureInfo.InvariantCulture, DateTimeStyles.None, /*out*/ toValue) ||
            LocalDateTime.TryParseExact(fromValue, Constants.IsoDateTimePrecisionMilliseconds, CultureInfo.InvariantCulture, DateTimeStyles.None, /*out*/ toValue);

        return isSuccess;
    }

    public <TValue>boolean TryParseValue(String fromValue, /*out*/ TValue toValue)
    {
        var isSuccess = true;
        toValue = default(TValue);
        try
        {
            // use the build-in converter for the others
            toValue = (TValue)Convert.ChangeType(fromValue, typeof(TValue), CultureInfo.InvariantCulture);
        }
        catch (FormatException)
        {
            isSuccess = false;
        }
        catch (OverflowException)
        {
            isSuccess = false;
        }
        return isSuccess;
    }

    public <TEnum>boolean TryParseEnum(String fromValue, /*out*/ TEnum toValue)
    {
        var sValueNoSpaces = fromValue.Replace(StringUtils.SPACE, StringUtils.EMPTY);

        // try the built in parser first
        var isSuccess = Enum.TryParse(sValueNoSpaces, true, /*out*/ toValue);
        // ReSharper disable once InvertIf
        if (!isSuccess)
        {
            // try and look at the descriptions (if they exist)
            for (TEnum enumNext : Enum.GetValues(typeof(TEnum)))
            {
                if (0 != String.Compare(ReadEnumDescription(enumNext), fromValue, true, CultureInfo.InvariantCulture)) continue;

                toValue = enumNext;
                isSuccess = true;
                break;
            }
        }
        return isSuccess;
    }

    // ----------------------------------------------------------------------------------------

}
