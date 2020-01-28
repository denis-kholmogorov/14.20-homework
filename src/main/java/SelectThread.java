import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class SelectThread implements Runnable {
    Thread thread;

    public SelectThread()
    {
        thread = new Thread(this);
    }
    public static SelectThread createAndSrart(){
        SelectThread selectThread = new SelectThread();
        selectThread.thread.start();
        return selectThread;
    }

    @Override
    public void run() {
        System.out.println("начал работу " + Thread.currentThread().getName());
        String sql = "SELECT name, birthDate, count FROM voter_count WHERE count > 1";
        ResultSet rs;
        try
        {
            rs = DBConnection.getConnection().createStatement().executeQuery(sql);
        while(rs.next())
        {
            System.out.println("\t" + rs.getString("name") + " (" +
                    rs.getString("birthDate") + ") - " + rs.getInt("count"));
        }
        rs.close();
        }
        catch (SQLException e)
        {
        e.printStackTrace();
    }

    }
}
