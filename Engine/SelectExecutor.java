package Engine;

import storage.Column;
import Engine.DatabaseEngine;
import storage.Row;
import storage.Table;

import java.util.List;

public class SelectExecutor {

    public void selectAll(DatabaseEngine db, String tableName) {

        // Get the table
        Table table = db.getTable(tableName);

        // Print column names
        for (Column column : table.getColumns()) {
            System.out.print(column.getName() + "\t");
        }
        System.out.println();

        // Print separator
        for (int i = 0; i < table.getColumns().size(); i++) {
            System.out.print("--------");
        }
        System.out.println();

        // Print all rows
        for (Row row : table.getRows()) {
            for (Object value : row.getValues()) {
                System.out.print(value + "\t");
            }
            System.out.println();
        }
    }
}