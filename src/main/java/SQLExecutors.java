import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SQLExecutors
{
    private static ExecutorService executor = Executors.newFixedThreadPool(1);

    public static void SQLExecutorsInsert(StringBuilder builder)
    {
        executor.submit(new InsertThread(builder));
    }

    public static void SQLExecutorsSelect()
    {
        executor.submit(new SelectThread());
    }

    public static void SQLExecutorsShutdown(){
        executor.shutdown();
    }


}
