import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class XMLHandlerString extends DefaultHandler
{
    private String voter;
    private static SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");
    private HashMap<String, Short> voterCount;


    public XMLHandlerString(){
        voterCount = new HashMap<>();
    }

    public  HashMap<String, Short> getVoterCount(){
        return voterCount;
    }

    public int getSizeVoterCount(){
        return voterCount.size();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        String[] nameAndDate;
        try
        {
            if(qName.equals("voter") && voter == null)
            {
                Date birthDay = birthDayFormat.parse(attributes.getValue("birthDay"));
                voter = attributes.getValue("name") + " | " + birthDayFormat.format(birthDay).replace(".", "-");
            }
            else if(qName.equals("visit") && voter != null)
            {
                short count = voterCount.getOrDefault(voter, (short)0);

                if(voterCount.size() == 50000){

                    /*for (String key : voterCount.keySet()) {
                        nameAndDate = key.split(" \\| ");
                        DBConnection.countVoter(nameAndDate[0], nameAndDate[1]);
                    }
                    DBConnection.executeMultiInsert();*/
                    voterCount.clear();
                }
            }
            else{
                throw new RuntimeException();
            }
        }catch (ParseException | RuntimeException | InterruptedException e){
            e.getMessage();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
    {
        if(qName.equals("voter"))
        {
            voter = null;
        }
    }

    public void DuplicatedVotersString()
    {
        System.out.println("Duplicated voters:");
        for (String voter: voterCount.keySet()){
            int count = voterCount.get(voter);
            if (count > 1){
                System.out.println("\t" +voter + " - " + count);
            }
        }
        System.out.println();
    }
}
