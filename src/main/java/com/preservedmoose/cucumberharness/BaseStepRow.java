package com.preservedmoose.cucumberharness;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.preservedmoose.cucumberharness.Application.Resources;

import org.apache.commons.lang3.StringUtils;

import lombok.var;
import lombok.experimental.ExtensionMethod;

@ExtensionMethod(StepRowAssertionFactory.class)
public abstract class BaseStepRow
{
    protected final IParser Parser;

    private final Method _methodInfoEnum;
    private final Method _methodInfoObject;
    private final Method _methodInfoObjectStatic;

    private final String ParseEnum = "ParseEnum";//nameof(IParser.ParseEnum);
    private final String ParseObject = "ParseObject";//nameof(IParser.ParseObject);
    private final String ParseObjectStatic = "ParseObjectStatic";//nameof(IParser.ParseObjectStatic);

    protected static final Type[] SimpleTypes =
    {
        boolean.class,
        byte.class,
        char.class,
        short.class,
        int.class,
        long.class,
        float.class,
        double.class,
        String.class,
        LocalDate.class,
        LocalDateTime.class
    };

    // comparison flag indicating that the object is different (not found in the other table)
    public boolean IsDifferent;// { set; get; }

    // collection for any errors found in the object when attempting to parse the table
    public List<String> ParseErrors;// { get; }

    // collection for all columns found populated with data when parsing the table
    public List<String> UsedProperties;// { get; }

    // ----------------------------------------------------------------------------------------

    protected BaseStepRow()
    {
        Parser = new Parser();

        ParseErrors = new ArrayList<String>();
        UsedProperties = new ArrayList<String>();

        // we may need to handle Objects using reflection to get the correct type
        var type = Parser.GetType();

        _methodInfoEnum = type.GetMethod(ParseEnum, BindingFlags.Instance | BindingFlags.Public);
        _methodInfoObject = type.GetMethod(ParseObject, BindingFlags.Instance | BindingFlags.Public);
        _methodInfoObjectStatic = type.GetMethod(ParseObjectStatic, BindingFlags.Instance | BindingFlags.Public);

        _methodInfoEnum.Should().NotBeNull(Resources.Get("BaseStepRow_MethodInfo"), ParseEnum);
        _methodInfoObject.Should().NotBeNull(Resources.Get("BaseStepRow_MethodInfo"), ParseObject);
        _methodInfoObjectStatic.Should().NotBeNull(Resources.Get("BaseStepRow_MethodInfo"), ParseObjectStatic);
    }

    // ----------------------------------------------------------------------------------------

    /// <summary>
    /// perform validation requested by the overridden class for the Validation method
    /// </summary>
    public void Validate()
    {
        Validation();
    }

    /// <summary>
    /// specify any validation that we wish to perform when we call the Validate method
    /// </summary>
    protected void Validation()
    {
    }

    // ----------------------------------------------------------------------------------------


    /// <summary>
    /// ensures that the table does not contains columns that do not map to the class
    /// </summary>
    /// <typeparam name="TStepRow">the class to which we wish to map</typeparam>
    /// <param name="table">the representation of the table in the feature file</param>
    // ReSharper disable once MemberCanBeMadeStatic.Local
    protected <TStepRow>void CheckForMissingColumnsOnClass(Table table)
    {
        var type = typeof(TStepRow);
        var typeInfo = type.GetTypeInfo();
        var listMissingColumns = new ArrayList<String>();

        for (var sColumn : table.Header)
        {
            var sNarrowColumn = sColumn.replace(StringUtils.SPACE, StringUtils.EMPTY);
            var propertyInfo = typeInfo.GetProperty(sNarrowColumn, BindingFlags.Public | BindingFlags.Instance);
            if (null == propertyInfo) listMissingColumns.add(sColumn);
        }

        if (0 == listMissingColumns.size()) return;

        // we have errors...
        var errorMessage = String.format(Resources.Get("BaseStepRow_MissingColumnsCheck"), String.join(Constants.CommaSeparator, listMissingColumns), type);

        // the assertion will throw an exception if this is invalid
        Integer.valueOf(listMissingColumns.size()).Should().Be(0, errorMessage);
    }

    /// <summary>
    /// ensures that the class does not contain nullable types
    /// </summary>
    /// <typeparam name="TStepRow">the class to which we wish to map</typeparam>
    // ReSharper disable once MemberCanBeMadeStatic.Local
    protected <TStepRow extends BaseStepRow>void CheckForNullableTypesOnClass()
    {
        var type = typeof(TStepRow);
        var baseType = typeof(StepRow<TStepRow>);
        var typeInfo = type.GetTypeInfo();

        var listNullableTypes = new List<String>();

        var baseProperties = baseType.GetProperties(BindingFlags.Instance | BindingFlags.Public);
        var derivedProperties = typeInfo.GetProperties(BindingFlags.Instance | BindingFlags.Public);

        var basePropertyNames = baseProperties.Select(p => p.Name);
        var properties = derivedProperties.Where(p => !basePropertyNames.Contains(p.Name)).ToList();

        // we can catch nullable types here for value types and enums
        for (var propertyInfo : properties)
        {
            var isCollection = propertyInfo.PropertyType.IsConstructedGenericType &&
                                typeof(IEnumerable).IsAssignableFrom(propertyInfo.PropertyType);

            if (!isCollection)
            {
                if (propertyInfo.PropertyType.IsGenericType &&
                    propertyInfo.PropertyType.GetGenericTypeDefinition() == typeof(Nullable<>))
                {
                    listNullableTypes.Add(propertyInfo.Name);
                }
            }
            else
            {
                var propertyType = propertyInfo.PropertyType.GetGenericArguments()[0];

                if (propertyType.IsGenericType &&
                    propertyType.GetGenericTypeDefinition() == typeof(Nullable<>))
                {
                    listNullableTypes.Add(propertyInfo.Name);
                }
            }
        }

        if (0 == listNullableTypes.Count) return;

        // we have errors...
        var errorMessage = String.Format(Resources.Get("BaseStepRow_NullableTypesCheck"), String.Join(Constants.CommaSeparator, listNullableTypes), type);

        // the assertion will throw an exception if this is invalid
        listNullableTypes.Count.Should().Be(0, errorMessage);
    }

    /// <summary>
    /// 
    /// </summary>
    /// <param name="type"></param>
    /// <param name="propertyType"></param>
    /// <param name="key"></param>
    /// <returns></returns>
    protected Method GetMethodInfo(Type type, Type propertyType, String key)
    {
        if (SimpleTypes.Contains(propertyType)) return null;

        var methodInfo = (Method)null;

        if (propertyType.IsEnum)
        {
            // handle using reflection to get the correct type
            methodInfo = _methodInfoEnum.MakeGenericMethod(propertyType);
        }
        else
        {
            // make sure the interface is implemented
            for (var iface : propertyType.GetInterfaces())
            {
                if (!iface.IsGenericType) continue;

                var typeOfInterface = iface.GetGenericTypeDefinition();
                var hasInterface = (typeOfInterface == typeof(IConvertibleFromString<>));
                var hasStaticInterface = (typeOfInterface == typeof(IConvertibleStaticFromString<>));

                if (!hasInterface && !hasStaticInterface) continue;

                methodInfo = (hasStaticInterface)
                    ? _methodInfoObjectStatic.MakeGenericMethod(propertyType)
                    : _methodInfoObject.MakeGenericMethod(propertyType);

                break;
            }
            methodInfo.Should().NotBeNull(Resources.Get("BaseStepRow_MethodInfoConverterCheck"), key, type);

            // ReSharper disable once InvertIf
            if (propertyType.IsClass)
            {
                var hasDefaultConstructor = (null != propertyType.GetConstructor(Type.EmptyTypes));
                hasDefaultConstructor.Should().BeTrue(Resources.Get("BaseStepRow_MethodInfoConstructorCheck"), key, type);
            }
        }
        return methodInfo;
    }

}
