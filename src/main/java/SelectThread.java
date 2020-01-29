import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class SelectThread extends Thread {

    @Override
    public void run() {
        try {
            DBConnection.printVoterCounts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
