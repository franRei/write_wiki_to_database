public class Reader {

    public static void main(String[] args) {
        //XML file of wiki
        String file = "enwiktionary_01_01.xml";
        Read_XML reader = new Read_XML();
        reader.parseFile(file);
    }
}
