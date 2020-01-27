import java.sql.SQLException;
import java.util.HashMap;

public class InsertThread extends Thread {

    private HashMap<String, Short> voterCount;
    private StringBuilder builder = new StringBuilder();

    public InsertThread(Object voterCount) {
        this.voterCount = (HashMap<String, Short>) voterCount;
    }

    @Override
    public void run() {
        System.out.println("начал работу " + Thread.currentThread().getName());
        String[] nameAndDate;
        try {
            for (String key : voterCount.keySet()) {
                nameAndDate = key.split(" \\| ");
                builder.append((builder.length()==0 ? "" : ",") +
                        "('" + nameAndDate[0] + "', '" + nameAndDate[1] + "',1)");
            }
            DBConnection.executeMultiInsert(builder);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
