import java.sql.*;

public class DBConnection
{
    private static Connection connection;
   // private static StringBuilder builder = new StringBuilder();
    private static boolean exit = false;
    private static String dbName = "learn";
    private static String dbUser = "root";
    private static String dbPass = "23019088";

    public static Connection getConnection()
    {
        if(connection == null )
        {
            try {
                connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + dbName +
                    "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC" +
                             "&user=" + dbUser + "&password=" + dbPass);

                if(!exit) {
                    connection.createStatement().execute("DROP TABLE IF EXISTS voter_count");
                    connection.createStatement().execute("CREATE TABLE voter_count(" +
                            "id INT NOT NULL AUTO_INCREMENT, " +
                            "name VARCHAR(50) NOT NULL, " +
                            "birthDate DATE NOT NULL, " +
                            "count INT NOT NULL, " +
                            "PRIMARY KEY(id)," +
                            "UNIQUE KEY name_date(birthDate,name))");
                    exit = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void executeMultiInsert(StringBuilder builder) throws SQLException {
        String sql = "INSERT INTO voter_count(name, birthDate, count) " +
                "VALUES" + builder.toString() +
                "ON DUPLICATE KEY UPDATE count = values(count) + 1";
        synchronized (DBConnection.class) {
            DBConnection.getConnection().createStatement().execute(sql);
        }
    }

    /*public synchronized static void countVoter(String name, String birthDay) throws SQLException
    {
        builder.append((builder.length()==0 ? "" : ",") +
                "('" + name + "', '" + birthDay + "',1)");*/





       /* DBConnection.getConnection().createStatement()
                .execute(sql);*/


        /*ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
        if(!rs.next())
        {
            DBConnection.getConnection().createStatement()
                    .execute("INSERT INTO voter_count(name, birthDate, `count`) VALUES('" +
                            name + "', '" + birthDay + "', 1)");
        }
        else {
            Integer id = rs.getInt("id");
            DBConnection.getConnection().createStatement()
                    .execute("UPDATE voter_count SET `count`=`count`+1 WHERE id=" + id);
        }
        rs.close();*/


    public static void printVoterCounts() throws SQLException
    {
        String sql = "SELECT name, birthDate, count FROM voter_count WHERE count > 1";
        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
        while(rs.next())
        {
            System.out.println("\t" + rs.getString("name") + " (" +
                    rs.getString("birthDate") + ") - " + rs.getInt("count"));
        }
    }
}
