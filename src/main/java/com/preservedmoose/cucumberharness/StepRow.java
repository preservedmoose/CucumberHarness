package com.preservedmoose.cucumberharness;
/*
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;

import lombok.var;
*/
import lombok.experimental.ExtensionMethod;

@ExtensionMethod(StepRowAssertionFactory.class)
public abstract class StepRow<TStepRow extends BaseStepRow> extends BaseStepRow
{
/*
    private final String PropertyParseErrors = "ParseErrors"; //nameof(ParseErrors);
    private final String PropertyUsedProperties = "PropertyUsedProperties"; //nameof(UsedProperties);

    // ----------------------------------------------------------------------------------------

    /// <summary>
    /// transform a SpecFlow table object into a class collection
    /// </summary>
    /// <typeparam name="TStepRow">the derived class</typeparam>
    /// <param name="table">table data provided by specflow</param>
    /// <returns>a list of objects of the defined type</returns>
    //[StepArgumentTransformation]
    public Collection<TStepRow> AutoTransform(Table table)
    {
        var collection = Transform(table);
        collection.Validate();
        return collection;
    }

    // ----------------------------------------------------------------------------------------

    /// <summary>
    /// use reflection to perform the check for valid fields in the DERIVED class
    /// </summary>
    /// <param name="other">the other instance with which to compare</param>
    protected boolean HelperEquals(TStepRow other)
    {
        var type = typeof(TStepRow);
        other.Should().NotBeNull(Resources.StepRow_EqualsOverrideNullMessage);

        var equals = true;

        // ReSharper disable once InvertIf
        if (!ReferenceEquals(this, other))
        {
            // ReSharper disable once PossibleNullReferenceException
            var usedProperties = UsedProperties.Union(other.UsedProperties).ToList();

            for (var propertyName : usedProperties)
            {
                var propertyInfo = type.GetProperty(propertyName);

                // this should perform a check against the type, not the object
                dynamic thisValue = propertyInfo.GetValue(this, null);
                dynamic otherValue = propertyInfo.GetValue(other, null);

                var isCollection = propertyInfo.PropertyType.IsConstructedGenericType
                    && typeof(IEnumerable).IsAssignableFrom(propertyInfo.PropertyType);

                if (!isCollection)
                {
                    equals = (thisValue == otherValue);
                    if (!equals) break;
                }
                else // check that the collections' contents are equal
                {
                    var bothNull = (thisValue == null && otherValue == null);
                    var bothNotNull = (thisValue != null && otherValue != null);

                    equals = bothNull || bothNotNull;
                    if (!equals) break;
                    if (bothNull) break;

                    // ReSharper disable PossibleNullReferenceException
                    equals = (thisValue.Count == otherValue.Count);
                    // ReSharper restore PossibleNullReferenceException
                    if (!equals) break;

                    //equals = Enumerable.SequenceEqual(thisValue, otherValue);
                    //if (!equals) break;

                    for (var index = 0; index < thisValue.Count; ++index)
                    {
                        equals = (otherValue.Contains(thisValue[index]));
                        if (!equals) break;
                    }
                    if (!equals) break;

                    for (var index = 0; index < otherValue.Count; ++index)
                    {
                        equals = (thisValue.Contains(otherValue[index]));
                        if (!equals) break;
                    }
                    if (!equals) break;
                }
            }
        }
        return equals;
    }

    /// <summary>
    /// use reflection to perform the check for valid fields
    /// </summary>
    protected int HelperHashCode()
    {
        var type = typeof(TStepRow);
        var baseType = typeof(StepRow<TStepRow>);

        // we only want the properties from the derived class
        var baseProperties = baseType.GetProperties(BindingFlags.Instance | BindingFlags.Public);
        var derivedProperties = type.GetProperties(BindingFlags.Instance | BindingFlags.Public);

        var basePropertyNames = baseProperties.Select(p => p.Name);
        var derivedPropertyNames = derivedProperties.Select(p => p.Name);

        var propertyNames = derivedPropertyNames.Except(basePropertyNames).ToList();

        int hash;

        unchecked
        {
            hash = type.GetHashCode();

            for (var propertyName : propertyNames)
            {
                var propertyInfo = type.GetProperty(propertyName);
                dynamic value = propertyInfo.GetValue(this, null);

                hash = (hash * Constants.HashPrime) ^ value.GetHashCode();
            }
        }
        return hash;
    }

    // ----------------------------------------------------------------------------------------

    /// <summary>
    /// determines whether or not the available property was actually used
    /// </summary>
    /// <typeparam name="TProperty">property type</typeparam>
    /// <param name="expression">a lambda expression of the type s => s.PropertyName</param>
    /// <returns>if this property is populated</returns>
    protected <TProperty>boolean IsUsedProperty(Expression<Func<TStepRow, TProperty>> expression)
    {
        var uses = false;
        var memberExpression = expression.Body as MemberExpression;

        // ReSharper disable once InvertIf
        if (memberExpression != null &&
            memberExpression.Member.MemberType == MemberTypes.Property)
        {
            var sPropertyName = memberExpression.Member.Name;
            uses = (UsedProperties.Contains(sPropertyName));
        }
        return uses;
    }

    // ----------------------------------------------------------------------------------------

    /// <summary>
    /// validates that the specified TValue has a valid value (not the default)
    /// for strings, where the default is null, there is an additional check against empty
    /// </summary>
    /// <typeparam name="TValue">value type</typeparam>
    /// <param name="value">the value to be validated against the default</param>
    /// <param name="expression">a lambda expression of the type s => s.PropertyName</param>
    protected <TValue>void ValidateValue(TValue value, Expression<Func<TStepRow, TValue>> expression)
    //where TValue : ValueType
    {
        var propertyType = typeof(TValue);

        if (!SimpleTypes.Contains(propertyType))
        {
            throw new StepRowException(String.Format(Resources.StepRow_ProvidedTypeMustBeValueType, propertyType));
        }

        if (value is String)
        {
            var stringValue = value as String;
            if (!String.IsNullOrWhiteSpace(stringValue)) return;
        }
        else
        {
            if (!default(TValue).Equals(value)) return;
        }
        AddValidationError(value, expression);
    }

    /// <summary>
    /// validates that the specified TEnum has a valid value (not the default)
    /// </summary>
    /// <typeparam name="TEnum">enum type</typeparam>
    /// <param name="value">the value to be validated against the default</param>
    /// <param name="expression">a lambda expression of the type s => s.PropertyName</param>
    protected <TEnum>void ValidateEnum(TEnum value, Expression<Func<TStepRow, TEnum>> expression)
        where TEnum : struct
    {
        if (!default(TEnum).Equals(value)) return;

        AddValidationError(value, expression);
    }

    // ----------------------------------------------------------------------------------------

    /// <summary>
    /// take a SpecFlow table and transform it to the supplied type
    /// </summary>
    /// <param name="table"></param>
    /// <returns></returns>
    private Collection<TStepRow> Transform(Table table)
    {
        // make sure we have valid column names
        // and that we are not using nullable types
        CheckForMissingColumnsOnClass<TStepRow>(table);
        CheckForNullableTypesOnClass<TStepRow>();

        var type = typeof(TStepRow);
        var entries = new Collection<TStepRow>();
        var typeInfo = type.GetTypeInfo();

        // handle specific type conversions and default the others
        for (var row : table.Rows)
        {
            var entry = (TStepRow)Activator.CreateInstance(type);
            entry.Should().NotBeNull(Resources.StepRow_CouldNotCreateInstance, type);

            var keys = row.Keys.ToArray();
            var values = row.Values.ToArray();

            // we need to reference the error structure property
            // we need to reference the used property structure property
            var propertyInfoParseErrors = typeInfo.GetProperty(PropertyParseErrors);
            var propertyInfoUsedProperties = typeInfo.GetProperty(PropertyUsedProperties);

            propertyInfoParseErrors.Should().NotBeNull(Resources.StepRow_CollectionPropertyNotFound, PropertyParseErrors);
            propertyInfoUsedProperties.Should().NotBeNull(Resources.StepRow_CollectionPropertyNotFound, PropertyUsedProperties);

            dynamic listParseErrors = propertyInfoParseErrors.GetValue(entry);
            dynamic listUsedProperties = propertyInfoUsedProperties.GetValue(entry);

            // loop through all columns in the table and map them to our class
            for (var column = 0; column < row.Count; ++column)
            {
                // strings are non-null and trimmed
                var key = keys[column];
                var value = values[column];

                var sNarrowKey = key.Replace(StringUtils.SPACE, StringUtils.EMPTY);
                var propertyInfo = typeInfo.GetProperty(sNarrowKey, BindingFlags.Public | BindingFlags.Instance);

                // note that this column was populated in the table
                listUsedProperties.Add(propertyInfo.Name);

                if (StringUtils.EMPTY == value) continue;

                // we have data that we need to convert
                var isCollection = propertyInfo.PropertyType.IsConstructedGenericType &&
                                    typeof(IEnumerable).IsAssignableFrom(propertyInfo.PropertyType);

                // if a class, struct or enum then we need the methodInfo structure populated accordingly
                var propertyType = (isCollection) ? propertyInfo.PropertyType.GetGenericArguments()[0] : propertyInfo.PropertyType;
                var methodInfo = GetMethodInfo(type, propertyType, key);

                if (!isCollection)
                {
                    var sParseError = StringUtils.EMPTY;
                    var result = ParseValue(propertyType, value, key, methodInfo, ref sParseError);

                    if (String.IsNullOrEmpty(sParseError))
                    {
                        propertyInfo.SetValue(entry, result);
                        continue;
                    }

                    // pick up any error encountered during parsing
                    listParseErrors.Add(sParseError);
                }
                else
                {
                    // we expect a collection, so split the String into separate entries
                    var listValues = value.Split(new[] { Constants.CommaSeparator }, StringSplitOptions.RemoveEmptyEntries).Select(s => s.Trim()).ToList();

                    // create a collection using reflection
                    var collectionType = typeof(Collection<>).MakeGenericType(propertyType);
                    dynamic collection = Activator.CreateInstance(collectionType);

                    var sParseError = StringUtils.EMPTY;

                    for (var nextValue : listValues)
                    {
                        var result = ParseValue(propertyType, nextValue, key, methodInfo, ref sParseError);

                        if (String.IsNullOrEmpty(sParseError))
                        {
                            collection.Add(result);
                            continue;
                        }

                        // pick up any error encountered during parsing
                        listParseErrors.Add(sParseError);
                    }
                    propertyInfo.SetValue(entry, collection);
                }
            }
            entries.Add(entry);
        }
        return entries;
    }

    private Object ParseValue(Type propertyType, String value, String key, Method methodInfo, String sParseError)
    {
        Object result;

        if (propertyType == typeof(String))
        {
            result = value;
        }
        else if (propertyType == typeof(boolean))
        {
            result = Parser.ParseBoolean(key, value, ref sParseError);
        }
        else if (propertyType == typeof(short))
        {
            result = Parser.ParseInt16(key, value, ref sParseError);
        }
        else if (propertyType == typeof(int))
        {
            result = Parser.ParseInt32(key, value, ref sParseError);
        }
        else if (propertyType == typeof(long))
        {
            result = Parser.ParseInt64(key, value, ref sParseError);
        }
        else if (propertyType == typeof(DateTime))
        {
            result = Parser.ParseDateTime(key, value, ref sParseError);
        }
        else if (SimpleTypes.Contains(propertyType))
        {
            result = Parser.ParseValue(propertyType, key, value, ref sParseError);
        }
        else    // enum, class or struct
        {
            var parameters = new object[] { key, value, sParseError };
            result = methodInfo.Invoke(Parser, parameters);
            sParseError = (String)parameters[2];
        }
        return result;
    }

    private <TValue>void AddValidationError(TValue value, Expression<Func<TStepRow, TValue>> expression)
    {
        var sPropertyName = StringUtils.EMPTY;
        var memberExpression = expression.Body as MemberExpression;

        if (memberExpression != null &&
            memberExpression.Member.MemberType == MemberTypes.Property)
        {
            sPropertyName = memberExpression.Member.Name;
        }
        var sParseError = String.Format(Resources.StepRow_ValidationFieldNotSet, typeof(TValue), sPropertyName);
        ParseErrors.Add(sParseError);
    }
*/
    // ----------------------------------------------------------------------------------------
}
