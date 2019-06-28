package com.preservedmoose.cucumberharness;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.preservedmoose.cucumberharness.Application.Resources;

import org.apache.commons.lang3.StringUtils;

import lombok.var;

public class Parser implements IParser
{
    // ----------------------------------------------------------------------------------------

    public <TEnum>String ReadEnumDescription(TEnum enumType)
    {
        /*
        var fieldInfo = enumType.GetType().GetField(enumType.ToString());
        var attributes = (DescriptionAttribute[])fieldInfo.GetCustomAttributes(typeof(DescriptionAttribute), false);

        var description = (attributes.Length > 0) ? attributes[0].Description : typeof(TEnum).ToString();
        return description;
        enumType.name
        */
        return "";
    }

    // ----------------------------------------------------------------------------------------

    public Boolean ParseBoolean(String column, String fromValue, String parseError)
    {
        Boolean value = null;
        var isSuccess = TryParseBoolean(fromValue, value);
        if (!isSuccess)
        {
            parseError = String.format(Resources.Get("Parser_InvalidValue"), column, fromValue, "Boolean");
        }
        return value;
    }

    public Short ParseShort(String column, String fromValue, String parseError)
    {
        Short value = null;
        var isSuccess = TryParseShort(fromValue, value);
        if (!isSuccess)
        {
            parseError = String.format(Resources.Get("Parser_InvalidValue"), column, fromValue, "Short");
        }
        return value;
    }

    public Integer ParseInteger(String column, String fromValue, String parseError)
    {
        Integer value = null;
        var isSuccess = TryParseInteger(fromValue, value);
        if (!isSuccess)
        {
            parseError = String.format(Resources.Get("Parser_InvalidValue"), column, fromValue, "Integer");
        }
        return value;
    }

    public Long ParseLong(String column, String fromValue, String parseError)
    {
        Long value = null;
        var isSuccess = TryParseLong(fromValue, value);
        if (!isSuccess)
        {
            parseError = String.format(Resources.Get("Parser_InvalidValue"), column, fromValue, "Long");
        }
        return value;
    }

    public BigDecimal ParseBigDecimal(String column, String fromValue, String parseError)
    {
        BigDecimal value = null;
        var isSuccess = TryParseBigDecimal(fromValue, value);
        if (!isSuccess)
        {
            parseError = String.format(Resources.Get("Parser_InvalidValue"), column, fromValue, "BigDecimal");
        }
        return value;
    }

    public LocalDate ParseDate(String column, String fromValue, String parseError)
    {
        LocalDate value = null;
        var isSuccess = TryParseDate(fromValue, value);
        if (!isSuccess)
        {
            parseError = String.format(Resources.Get("Parser_InvalidValue"), column, fromValue, "LocalDate");
        }
        return value;
    }

    public LocalDateTime ParseDateTime(String column, String fromValue, String parseError)
    {
        LocalDateTime value = null;
        var isSuccess = TryParseDateTime(fromValue, value);
        if (!isSuccess)
        {
            parseError = String.format(Resources.Get("Parser_InvalidValue"), column, fromValue, "LocalDateTime");
        }
        return value;
    }

    public <TEnum>TEnum ParseEnum(String column, String fromValue, String parseError)
    {
        TEnum value = null;
        var isSuccess = TryParseEnum(fromValue, value);
        if (!isSuccess)
        {
            parseError = String.format(Resources.Get("Parser_InvalidValue"), column, fromValue, "TEnum");
        }
        return value;
    }

    public <TObject extends IConvertibleFromString<TObject>>TObject ParseObject(String column, String fromValue, String parseError)
    {
        /*
        var value = new TObject();
        if (value.TryParse(fromValue)) return value;

        parseError = String.format(Resources.Get("Parser_InvalidValue"), column, fromValue, typeof(TObject));
        return value;
        */
        return (TObject) new Object();
    }

    public <TObject extends IConvertibleStaticFromString<TObject>>TObject ParseObjectStatic(String column, String fromValue, String parseError)
    {
        /*
        // for calls that return static objects
        // we must create an instance on which to call the method
        // and then return the static object, not the one created
        TObject value;
        var temporary = new TObject();
        if (temporary.TryParse(fromValue, value)) return value;

        parseError = String.format(Resources.Get("Parser_InvalidValue"), column, fromValue, typeof(TObject));
        return value;
        */
        return (TObject) new Object();
    }

    // ----------------------------------------------------------------------------------------

    public boolean TryParseBoolean(String fromValue, Boolean toValue)
    {
        /*
        var isSuccess = Boolean.TryParse(fromValue, toValue);

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
        */
        return false;
    }

    public boolean TryParseShort(String fromValue, Short toValue)
    {
        /*
        // allow comma separated values
        var isSuccess = short.TryParse(fromValue, NumberStyles.Integer | NumberStyles.AllowThousands, CultureInfo.InvariantCulture, toValue);
        return isSuccess;
        */
        return false;
    }

    public boolean TryParseInteger(String fromValue, Integer toValue)
    {
        /*
        // allow comma separated values
        var is = NumberUtils.isCreatable(fromValue);
        var isSuccess = int.TryParse(fromValue, NumberStyles.Integer | NumberStyles.AllowThousands, CultureInfo.InvariantCulture, toValue);
        return isSuccess;
        */
        return false;
    }

    public boolean TryParseLong(String fromValue, Long toValue)
    {
        /*
        // allow comma separated values
        var isSuccess = long.TryParse(fromValue, NumberStyles.Integer | NumberStyles.AllowThousands, CultureInfo.InvariantCulture, toValue);
        return isSuccess;
        */
        return false;
    }

    public boolean TryParseBigDecimal(String fromValue, BigDecimal toValue)
    {
        /*
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
        */
        return false;
    }

    public boolean TryParseDate(String fromValue, LocalDate toValue)
    {
        /*
        // we are only interested in ISO 8601 based dates
        var isSuccess =
            LocalDate.TryParseExact(fromValue, Constants.IsoDate, CultureInfo.InvariantCulture, DateTimeStyles.None, toValue) ||
            LocalDate.TryParseExact(fromValue, Constants.IsoDateTime, CultureInfo.InvariantCulture, DateTimeStyles.None, toValue) ||
            LocalDate.TryParseExact(fromValue, Constants.IsoDateTimePrecisionMinutes, CultureInfo.InvariantCulture, DateTimeStyles.None, toValue) ||
            LocalDate.TryParseExact(fromValue, Constants.IsoDateTimePrecisionMilliseconds, CultureInfo.InvariantCulture, DateTimeStyles.None, toValue);

        return isSuccess;
        */
        return false;
    }

    public boolean TryParseDateTime(String fromValue, LocalDateTime toValue)
    {
        /*
        // we are only interested in ISO 8601 based dates
        var isSuccess =
            LocalDateTime.TryParseExact(fromValue, Constants.IsoDate, CultureInfo.InvariantCulture, DateTimeStyles.None, toValue) ||
            LocalDateTime.TryParseExact(fromValue, Constants.IsoDateTime, CultureInfo.InvariantCulture, DateTimeStyles.None, toValue) ||
            LocalDateTime.TryParseExact(fromValue, Constants.IsoDateTimePrecisionMinutes, CultureInfo.InvariantCulture, DateTimeStyles.None, toValue) ||
            LocalDateTime.TryParseExact(fromValue, Constants.IsoDateTimePrecisionMilliseconds, CultureInfo.InvariantCulture, DateTimeStyles.None, toValue);

        return isSuccess;
        */
        return false;
    }

    public <TEnum>boolean TryParseEnum(String fromValue, TEnum toValue)
    {
        /*
        var sValueNoSpaces = fromValue.replace(StringUtils.SPACE, StringUtils.EMPTY);

        // try the built in parser first
        var isSuccess = Enum.TryParse(sValueNoSpaces, true, toValue);
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
        */
        return false;
    }

    // ----------------------------------------------------------------------------------------

}
