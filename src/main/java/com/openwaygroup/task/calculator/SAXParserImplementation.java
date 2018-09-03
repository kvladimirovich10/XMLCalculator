package com.openwaygroup.task.calculator;import org.xml.sax.Attributes;import org.xml.sax.SAXException;import org.xml.sax.XMLReader;import org.xml.sax.helpers.DefaultHandler;import javax.xml.parsers.ParserConfigurationException;import javax.xml.parsers.SAXParser;import javax.xml.parsers.SAXParserFactory;import java.io.IOException;import java.nio.file.Path;import java.util.LinkedList;import java.util.Objects;import static com.openwaygroup.task.calculator.Main.logger;class SAXParserImplementation {    static LinkedList<LinkedList<String>> SAXParser(Path input) throws ParserConfigurationException, SAXException, IOException {        SAXParserFactory spf = SAXParserFactory.newInstance();        SAXParser saxParser = spf.newSAXParser();        XMLReader xmlReader = saxParser.getXMLReader();        Parser parser = new Parser();        xmlReader.setContentHandler(parser);        xmlReader.parse(input.toString());        return parser.getExpressionList();    }    private static class Parser extends DefaultHandler {        private static final String ARGUMENT = "arg";        private static final String OPERATION = "operation";        private static final String EXPRESSION = "expression";        private static final String OPERATION_TYPE = "OperationType";        private static String currentElement;        private LinkedList<LinkedList<String>> expressionList;        @Override        public void startDocument() throws SAXException {            expressionList = new LinkedList<>();        }        @Override        public void endDocument() throws SAXException {            logger.info("File's successfully parsed");        }        @Override        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {            currentElement = qName;            switch (qName) {                case EXPRESSION:                    expressionList.add(new LinkedList<>());                    break;                case OPERATION:                    expressionList.getLast().add(attributes.getValue(OPERATION_TYPE));                    break;            }        }        @Override        public void characters(char[] ch, int start, int length) {            String text = new String(ch, start, length);            text = text.replaceAll("\\s+", "");            if (text.contains("<") || currentElement == null || Objects.equals(text, ""))                return;            switch (currentElement) {                case ARGUMENT:                    expressionList.getLast().add(text);                    break;            }        }        LinkedList<LinkedList<String>> getExpressionList() {            return expressionList;        }    }}