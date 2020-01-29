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
        try {
            DBConnection.printVoterCounts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
