package com.preservedmoose.cucumberharness;
/*
import java.util.HashMap;
import java.util.List;

import io.cucumber.datatable.DataTable;
import lombok.var;
*/
/// <summary>
/// Helper extension methods for handling DataTable modifications
/// </summary>
public class DataTableExtensions
{
/*
    private final String Separator = " | ";

    /// <summary>
    /// returns the table as a String representation
    /// </summary>
    /// <param name="dataTable"></param>
    /// <returns></returns>
    public static String ToDisplayString(DataTable dataTable)
    {
        var stringBuilder = new StringBuilder();
        var dictionary = new HashMap<String, Integer>();

        //List<Converter> l = dataTable.asList(Converter.class);

        // set the structure to hold the columns and the initial widths of these
        for (DataColumn column : dataTable.Columns)
        {
            dictionary[column.ColumnName] = column.ColumnName.Length;
        }

        // look through all of the rows for any that are wider than the headers
        for (DataRow row : dataTable.Rows)
        {
            for (var iColumn = 0; iColumn < row.ItemArray.Length; iColumn++)
            {
                if (row[iColumn] == null) continue;

                var iWidth = (row[iColumn] is DateTime) ? Constants.IsoDateTime.Length : row[iColumn].ToString().Length;

                // if this row is wider then widen our column size
                if (iWidth > dictionary[dataTable.Columns[iColumn].ColumnName])
                {
                    dictionary[dataTable.Columns[iColumn].ColumnName] = iWidth;
                }
            }
        }

        var rowLength = (dictionary.Values.Count + 1) * Separator.Length;
        for (var value : dictionary.Values) rowLength += value;

        // separator bar
        stringBuilder.AppendLine(new String('-', rowLength));

        // put in the headers
        for (DataColumn column : dataTable.Columns)
        {
            var format = "{0,-" + dictionary[column.ColumnName] + "}";
            stringBuilder.Append(Separator + String.Format(format, column.ColumnName));
        }
        stringBuilder.AppendLine(Separator);

        // separator bar
        stringBuilder.AppendLine(new String('-', rowLength));

        // put in the content
        for (DataRow row : dataTable.Rows)
        {
            for (var iColumn = 0; iColumn < row.ItemArray.Length; iColumn++)
            {
                var iColumnWidth = dictionary[dataTable.Columns[iColumn].ColumnName];
                stringBuilder.Append(Separator);

                var rightAlign = row[iColumn] is ValueType;
                var format = "{0," + (rightAlign ? ' ' : '-') + iColumnWidth + "}";
                stringBuilder.AppendFormat(format, row[iColumn]);

                if (iColumn == row.ItemArray.Length - 1) stringBuilder.AppendLine(Separator);
            }
        }

        // separator bar
        stringBuilder.AppendLine(new String('-', rowLength));

        return stringBuilder.ToString();
    }
*/
}
