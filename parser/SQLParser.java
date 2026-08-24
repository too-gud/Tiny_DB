package parser;

import Engine.DatabaseEngine;
import Engine.DeleteExecutor;
import Engine.InsertExecutor;
import Engine.SelectExecutor;
import Engine.CreateTableExecutor;

import storage.Column;

import java.util.ArrayList;
import java.util.List;

public class SQLParser {

    private final Tokenizer tokenizer;

    public SQLParser() {
        tokenizer = new Tokenizer();
    }

    public void execute(String query, DatabaseEngine db) {

        List<String> tokens = tokenizer.tokenize(query);

        if (tokens.isEmpty()) {
            return;
        }

        String command = tokens.get(0).toUpperCase();

        switch (command) {

            case "CREATE":
                parseCreate(tokens, db);
                break;

            case "INSERT":
                parseInsert(tokens, db);
                break;

            case "SELECT":
                parseSelect(tokens, db);
                break;

            case "DELETE":
                parseDelete(tokens, db);
                break;

            default:
                throw new RuntimeException("Unsupported query.");
        }
    }

    private void parseCreate(List<String> tokens, DatabaseEngine db) {

        String tableName = tokens.get(2);

        List<Column> columns = new ArrayList<>();

        for (int i = 4; i < tokens.size(); i++) {

            if (tokens.get(i).equals(")")) {
                break;
            }

            if (tokens.get(i).equals(",")) {
                continue;
            }

            String columnName = tokens.get(i);
            String columnType = tokens.get(i + 1);

            columns.add(new Column(columnName, columnType));

            i++;
        }

        CreateTableExecutor create = new CreateTableExecutor();
        create.execute(db, tableName, columns);
    }

    private void parseInsert(List<String> tokens, DatabaseEngine db) {

        String tableName = tokens.get(2);

        List<Object> values = new ArrayList<>();

        boolean readingValues = false;

        for (String token : tokens) {

            if (token.equals("(")) {
                readingValues = true;
                continue;
            }

            if (token.equals(")")) {
                break;
            }

            if (readingValues && !token.equals(",")) {

                try {
                    values.add(Integer.parseInt(token));
                }
                catch (NumberFormatException e) {
                    values.add(token);
                }
            }
        }

        InsertExecutor insert = new InsertExecutor();
        insert.execute(db, tableName, values);
    }

    private void parseSelect(List<String> tokens, DatabaseEngine db) {

        String tableName = tokens.get(3);

        SelectExecutor select = new SelectExecutor();
        select.selectAll(db, tableName);
    }

    private void parseDelete(List<String> tokens, DatabaseEngine db) {

        String tableName = tokens.get(2);

        String columnName = tokens.get(4);

        String value = tokens.get(6);

        DeleteExecutor delete = new DeleteExecutor();
        delete.execute(db, tableName, columnName, value);
    }
}