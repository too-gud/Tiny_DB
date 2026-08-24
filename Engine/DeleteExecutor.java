package Engine;


import storage.Row;
import storage.Table;

import java.util.Iterator;

public class DeleteExecutor {

    public void execute(DatabaseEngine db,
                        String tableName,
                        String columnName,
                        Object value) {

        // Get the table
        Table table = db.getTable(tableName);

        // Find the column index
        int columnIndex = -1;

        for (int i = 0; i < table.getColumns().size(); i++) {
            if (table.getColumns().get(i).getName().equalsIgnoreCase(columnName)) {
                columnIndex = i;
                break;
            }
        }

        if (columnIndex == -1) {
            throw new RuntimeException("Column '" + columnName + "' not found.");
        }

        int deletedRows = 0;

        Iterator<Row> iterator = table.getRows().iterator();

        while (iterator.hasNext()) {

            Row row = iterator.next();

            Object currentValue = row.getValues().get(columnIndex);

            if (currentValue.equals(value)) {
                iterator.remove();
                deletedRows++;
            }
        }

        System.out.println(deletedRows + " row(s) deleted.");
    }
}