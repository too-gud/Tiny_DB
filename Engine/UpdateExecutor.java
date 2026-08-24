package Engine;

import storage.Row;
import storage.Table;

public class UpdateExecutor {

    public void execute(DatabaseEngine db,
                        String tableName,
                        String updateColumn,
                        Object newValue,
                        String whereColumn,
                        Object whereValue) {

        // Get the table
        Table table = db.getTable(tableName);

        int updateIndex = -1;
        int whereIndex = -1;

        // Find column indices
        for (int i = 0; i < table.getColumns().size(); i++) {

            String columnName = table.getColumns().get(i).getName();

            if (columnName.equalsIgnoreCase(updateColumn)) {
                updateIndex = i;
            }

            if (columnName.equalsIgnoreCase(whereColumn)) {
                whereIndex = i;
            }
        }

        // Validate columns
        if (updateIndex == -1) {
            throw new RuntimeException("Column '" + updateColumn + "' not found.");
        }

        if (whereIndex == -1) {
            throw new RuntimeException("Column '" + whereColumn + "' not found.");
        }

        int updatedRows = 0;

        // Update matching rows
        for (Row row : table.getRows()) {

            Object currentValue = row.getValues().get(whereIndex);

            if (currentValue.equals(whereValue)) {

                row.getValues().set(updateIndex, newValue);

                updatedRows++;
            }
        }

        System.out.println(updatedRows + " row(s) updated.");
    }
}
