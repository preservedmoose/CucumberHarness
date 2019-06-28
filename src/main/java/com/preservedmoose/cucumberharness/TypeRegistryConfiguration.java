package com.preservedmoose.cucumberharness;

import java.lang.reflect.Type;
import java.util.Locale;
import java.util.Map;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.cucumberexpressions.ParameterByTypeTransformer;
import io.cucumber.cucumberexpressions.ParameterType;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableCellByTypeTransformer;
import io.cucumber.datatable.TableEntryByTypeTransformer;
import lombok.var;

public class TypeRegistryConfiguration implements TypeRegistryConfigurer
{
    @Override
    public Locale locale()
    {
        return Locale.ENGLISH;
    }

    @Override
    public void configureTypeRegistry(TypeRegistry typeRegistry)
    {
        var parameterType = new ParameterType<>
        (
            "digit",
            "[0-9]",
            Integer.class,
            (String s) -> Integer.parseInt(s)
        );

        var dataTableType = new DataTableType
        (
            Converter.class,
            (String s) -> new Converter()
        );

        typeRegistry.defineParameterType(parameterType);
        typeRegistry.defineDataTableType(dataTableType);

        var transformer = new Transformer();
        typeRegistry.setDefaultParameterTransformer(transformer);
        typeRegistry.setDefaultDataTableCellTransformer(transformer);
        typeRegistry.setDefaultDataTableEntryTransformer(transformer);
    }

    private class Transformer implements ParameterByTypeTransformer, TableEntryByTypeTransformer, TableCellByTypeTransformer {
 
        @Override
        public Object transform(String s, Type type) {
            return new Object();
        }
 
        @Override
        public <T> T transform(Map<String, String> map, Class<T> aClass, TableCellByTypeTransformer tableCellByTypeTransformer) {
            return (T)new Object();
        }
 
        @Override
        public <T> T transform(String s, Class<T> aClass) {
            return (T)new Object();
        }
    }
}
