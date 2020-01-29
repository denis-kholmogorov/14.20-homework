import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class Loader
{
    static {
        DBConnection.getConnection();
    }

    public static void main(String[] args) throws Exception
    {
        String fileName = "res/data-18M.xml";
        System.out.println("Начало цикла");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLHandlerString handlerString = new XMLHandlerString();

        // Оптимизтрованный
        long usageS = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long a = System.currentTimeMillis();
        parser.parse(new File(fileName), handlerString);
        System.out.println(" Время парсинга - " + (System.currentTimeMillis() - a));
        SQLExecutors.SQLExecutorsInsert(XMLHandlerString.getBuilder());

        usageS = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - usageS)/1024/1024;
        System.out.println(usageS + " MB занимает парсер SAXParser после оптимизации \n");

        SQLExecutors.SQLExecutorsSelect();
        SQLExecutors.SQLExecutorsShutdown();


    }
}