import java.sql.SQLException;

public class InsertThread extends Thread
{
    StringBuilder builder;
    public InsertThread(StringBuilder builder)
    {
        this.builder = builder;
    }
    @Override
    public void run() {
        try {
            DBConnection.executeMultiInsert(builder);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
