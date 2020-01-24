import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Loader
{
    private static SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");
    private static SimpleDateFormat visitDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    private static HashMap<Voter, Integer> voterCounts = new HashMap<>();

    public static void main(String[] args) throws Exception
    {
        String fileName = "res/data-1572M.xml";
        System.out.println("Начало цикла");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLHandlerString handlerString = new XMLHandlerString();

        // Оптимизтрованный
        long usageS = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        parser.parse(new File(fileName), handlerString);
        usageS = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - usageS)/1024/1024;
        System.out.println(usageS + " MB занимает парсер SAXParser после оптимизации \n");
        String [] nameAndDate;
        for (String key : handlerString.getVoterCount().keySet()){
            nameAndDate = key.split(" \\| ");
            DBConnection.countVoter(nameAndDate[0], nameAndDate[1]);
        }
        long a = System.currentTimeMillis();
        DBConnection.executeMultiInsert();
        System.out.println(System.currentTimeMillis() - a);
        handlerString.DuplicatedVotersString();
        DBConnection.printVoterCounts();


    }
}