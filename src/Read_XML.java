import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Read_XML {

        int count = 0;
        int count_text;
        @SuppressWarnings({"unchecked", "null"})


        //skips to the next start element with specified tag, otherwise
        private boolean findNext(XMLEventReader eventReader, String tag) throws XMLStreamException {
            while (eventReader.hasNext()){
                XMLEvent event = eventReader.nextEvent();
                if(event.isStartElement()){
                    StartElement startElement = event.asStartElement();
                    if(startElement.getName().getLocalPart().equals(tag) && eventReader.hasNext()){
                        return true;
                    }
                }
            }
            return false;
        }

        private boolean findPattern(String line, String wordToFind) {
            Pattern p = Pattern.compile(wordToFind);
            Matcher m = p.matcher(line);
            if(m.find()){
                return true;
            }
            return false;
        }

        public void parseFile(String file/*, String suche*/) {
            Database_Writer database = new Database_Writer();

            try{
                XMLInputFactory inputFactory = XMLInputFactory.newInstance();
                InputStream in = new FileInputStream(file);
                XMLEventReader eventReader =
                        inputFactory.createXMLEventReader(in);



                while(findNext(eventReader, "page")){

                    if(findNext(eventReader, "title")){
                        XMLEvent title = eventReader.nextEvent();
                        if(title.isCharacters()){
                            String wiki = title.asCharacters().getData();

                            
                                count++;
                                System.out.println(wiki + " " + count);
                                String set_wiki =  wiki;
                                String set_description = "";

                                if(findNext(eventReader, "text")){

                                    XMLEvent text = eventReader.nextEvent();
                                    String description="";
                                    System.out.println("TEST");
                                    while(text.isCharacters()){
                                        description += text.asCharacters().getData();

                                        if(eventReader.hasNext()){
                                            text = eventReader.nextEvent();
                                        }
                                        else{
                                            break;
                                        }
                                    }

                                    set_description = description;

                                    count_text++;
                                    System.out.println(count_text);
                                }
                                database.open_Database(set_wiki, set_description);
                        }
                    }
                }
                System.out.println("count: " + count);
                System.out.println("count_description: " + count_text);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (XMLStreamException e) {
                e.printStackTrace();
            } 
        }
    }
