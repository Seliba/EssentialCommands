package de.seliba.essentialcommands.home.util;

/*
EssentialCommands created by Seliba
*/

import de.seliba.essentialcommands.home.Home;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.*;
import java.util.UUID;

public class MySQLHandler {

    public interface SQLConsumer {

        default public void accept(PreparedStatement preparedStatement) {
            try {
                run(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        void run(PreparedStatement preparedStatement) throws SQLException;
    }

    private static final String DRIVER = "com.mysql.jdbc.Driver";

    private Connection connection;
    private PreparedStatement preparedStatement;
    private String url;
    private String username;
    private String password;
    private String server;

    static {
        try {
            DriverManager.registerDriver((Driver) Class.forName(DRIVER).newInstance());
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public MySQLHandler(final String host, final int port, final String username, final String password, final String database, final String servername) {
        setConnection(host, port, username, password, database);
        server = servername;
    }

    public MySQLHandler(final String host, final String username, final String password, final String database, final String servername) {
        setConnection(host, 3306, username, password, database);
        server = servername;
    }

    public void setConnection(final String host, final int port, final String username, final String password, final String database) {
        this.url = "jdbc:mysql://" + host + ":" + port + "/" + database;
        this.username = username;
        this.password = password;
    }

    public boolean connected() {
        try {
            return (connection != null) && !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void connect() {
        if (!connected()) {
            try {
                this.connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void disconnect() {
        if (connected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createTable(final String name, final String columns) {
        if (connected()) {
            try {
                preparedStatement = connection.prepareStatement(String.format("CREATE TABLE IF NOT EXISTS `%s` (%s)", name, columns));
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Location getHome(UUID uuid) {
        if (connected()) {
            try {
                preparedStatement = connection.prepareStatement("SELECT * FROM `homes` WHERE `uuid` = ? AND `server` = ?");
                preparedStatement.setString(1, uuid.toString());
                preparedStatement.setString(2, server);

                ResultSet r;
                if ((r = preparedStatement.executeQuery()).next()) {
                    Location location = new Location(
                            Bukkit.getWorld(r.getString("world")),
                            r.getDouble("x"),
                            r.getDouble("y"),
                            r.getDouble("z"),
                            r.getFloat("pitch"),
                            r.getFloat("yaw")
                    );
                    r.close();
                    preparedStatement.close();
                    return location;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void setHome(UUID uuid, Location location) {
        if (connected()) {
            if (hasHome(uuid)) {
                updateHome(uuid, location);
                return;
            }

            try {
                preparedStatement = connection.prepareStatement("INSERT INTO `homes` (`uuid`, `server`, ``world`, `x`, `y`, `z`, `pitch`, `yaw`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                preparedStatement.setString(1, uuid.toString());
                preparedStatement.setString(2, server);
                preparedStatement.setString(3, location.getWorld().getName());
                preparedStatement.setDouble(4, location.getX());
                preparedStatement.setDouble(5, location.getY());
                preparedStatement.setDouble(6, location.getZ());
                preparedStatement.setFloat(7, location.getPitch());
                preparedStatement.setFloat(8, location.getYaw());

                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateHome(UUID uuid, Location location) {
        if (connected()) {
            try {
                preparedStatement = connection.prepareStatement("UPDATE `homes` SET `world`=?, `x`=?, `y`=?, `z`=?, `pitch`=?, `yaw`=? WHERE `uuid` = ?");
                preparedStatement.setString(1, location.getWorld().getName());
                preparedStatement.setDouble(2, location.getX());
                preparedStatement.setDouble(3, location.getY());
                preparedStatement.setDouble(4, location.getZ());
                preparedStatement.setFloat(5, location.getPitch());
                preparedStatement.setFloat(6, location.getYaw());
                preparedStatement.setString(7, uuid.toString());

                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean hasHome(UUID uuid) {
        if (!connected())
            return false;

        boolean exists = false;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `homes` WHERE `uuid`=? AND `server` = ?");
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, server);

            final ResultSet resultSet = preparedStatement.executeQuery();
            exists = resultSet.next();

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }
}