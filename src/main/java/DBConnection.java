import java.sql.*;

public class DBConnection
{
    private static Connection connection;
   // private static StringBuilder builder = new StringBuilder();
    private static boolean exit = false;
    private static String dbName = "learn";
    private static String dbUser = "root";
    private static String dbPass = "23019088";
    private static int count = 0;

    public static Connection getConnection()
    {
        if(connection == null )
        {
            try {
                connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + dbName +
                    "?useServerPrepStmts=false&rewriteBatchedStatements=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC" +
                             "&user=" + dbUser + "&password=" + dbPass);

                if(!exit) {
                    connection.createStatement().execute("DROP TABLE IF EXISTS voter_count_18");
                    connection.createStatement().execute("CREATE TABLE voter_count_18(" +
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
        Statement stmt = getConnection().createStatement();
        String sql = "INSERT INTO voter_count(name, birthDate, count) " +
                "VALUES" + builder.toString() +
               " ON DUPLICATE KEY UPDATE count = count + 1";
        stmt.addBatch(sql);
        stmt.executeBatch();
        stmt.close();
        count++;
        System.out.println("объем сделанных записей = " + (count * 350000));
    }

    public static void printVoterCounts() throws SQLException
    {
        String sql = "SELECT name, birthDate, count FROM voter_count WHERE count > 1";
        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
        while(rs.next())
        {
            System.out.println("\t" + rs.getString("name") + " (" +
                    rs.getString("birthDate") + ") - " + rs.getInt("count"));
        }
        String sql1 = "SELECT count(*) FROM voter_count WHERE count > 1";
        ResultSet rs1 = DBConnection.getConnection().createStatement().executeQuery(sql1);
        rs.close();
    }
}

/* Время парсинга - 513551
объем сделанных записей = 12500000
37 MB занимает парсер SAXParser после оптимизации */