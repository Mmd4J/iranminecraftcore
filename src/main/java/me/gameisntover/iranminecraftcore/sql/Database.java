package me.gameisntover.iranminecraftcore.sql;

import me.gameisntover.iranminecraftcore.plugin.IranMinecraftPlugin;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.sql.*;

public abstract class Database {
    private String address;
    private String dbName;
    private String user;
    private int port;
    private String password;
    public Connection con;
    public static String SQLITE_PREFIX = "jdbc:sqlite:";
    private final Type type;
    private File file;

    public Database(File file) {
        this.file = file;
        type = Type.SQLite;
        createConnection();
    }

    public Database(String address, String dbName, int port, String user, String password) {
        this.address = address;
        this.dbName = dbName;
        this.port = port;
        this.user = user;
        this.password = password;
        type = Type.MySQL;
        createConnection();
    }

    public void createNewTable(String tableName, DBObject<String>... types) {
        createConnection();
        StringBuilder builder = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tableName + "(");
        for (DBObject<String> obj : types) {
            builder.append(obj.name).append(" ").append(obj.value);
            if (obj.primaryKey) builder.append(" PRIMARY KEY");
            if (types[types.length - 1].equals(obj))
                builder.append(")");
            else builder.append(",");
        }
        builder.append(";");
        String sql = builder.toString();
        try {
            Statement stmt = con.createStatement();
            stmt.execute(sql);
            stmt.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertData(String tableName, DBObject<?>... objects) {
        createConnection();
        StringBuilder builder1 = new StringBuilder("INSERT OR IGNORE INTO ").append(tableName).append("(");
        StringBuilder builder2 = new StringBuilder(" VALUES(");
        for (DBObject<?> obj : objects) {
            builder1.append(obj.name);
            builder2.append(obj.value.toString());
            if (objects[objects.length - 1].equals(obj)) {
                builder1.append(")");
                builder2.append(");");
            } else {
                builder1.append(",");
                builder2.append(",");
            }
        }
        String sql = builder1.append(builder2).toString();
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateData(String tableName, DBObject<?>... objects) {
        createConnection();
        StringBuilder builder1 = new StringBuilder("UPDATE OR IGNORE ").append(tableName).append(" SET ");
        StringBuilder builder2 = new StringBuilder();
        for (DBObject<?> obj : objects) {
            if (obj.doNotUpdate) {
                if (builder2.toString().length() == 0) builder2 = new StringBuilder(" WHERE ");
                builder2.append(obj.name).append("=").append(obj.value.toString());
                if (objects[objects.length - 1].equals(obj)) {
                    builder2.append(";");
                } else {
                    builder2.append(",");
                }
            } else {
                builder1.append(obj.name).append("=").append(obj.value.toString());
                if (objects[objects.length - 1].equals(obj)) {
                    builder2.append(";");
                } else {
                    builder1.append(",");
                }
            }
        }
        String sql = builder1.append(builder2).toString();
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public SQLResult selectData(String tableName, DBObject<?>... primaryKeys) {
        createConnection();
        StringBuilder b1 = new StringBuilder("SELECT * FROM " + tableName + " WHERE ");
        for (DBObject<?> obj : primaryKeys) {
            b1.append(obj.name).append("=").append(obj.value).append(" ");
            if (!primaryKeys[primaryKeys.length - 1].equals(obj)) {
                b1.append(",");
            } else {
                b1.append(";");
            }
        }
        SQLResult sr;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(b1.toString());
            sr = new SQLResult(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sr;
    }

    public enum Type {
        MySQL,
        SQLite

    }

    public void createConnection() {
        try {
            switch (type) {
                case MySQL:
                    con = DriverManager.getConnection("jdbc:mysql://" + address + ":" + port + "/" + dbName, user, password);
                    break;
                case SQLite:
                    con = DriverManager.getConnection(SQLITE_PREFIX + file.getPath());
                    break;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

}
