package storage;
import java.util.*;
public class Table {

    private String tableName;
    private List<Column> columns;
    private List<Row> rows;

    public Table(String tableName, List<Column> columns) {
        this.tableName = tableName;
        this.columns = columns;
        this.rows = new ArrayList<>();
    }

    public void insert(Row row) {
        rows.add(row);
    }

    public List<Row> getRows() {
        return rows;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public String getTableName() {
        return tableName;
    }
}