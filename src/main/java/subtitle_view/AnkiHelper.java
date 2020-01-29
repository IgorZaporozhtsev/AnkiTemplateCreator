package subtitle_view;


import com.sun.deploy.util.StringUtils;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnkiHelper {
    private final static String FILE_ENG_NAME = "eng";
    private final static String FILE_RUS_NAME = "rus";

    private static String PATH = "d:\\FILMs\\FamilyGuy\\Family Guy - Season 1\\";
    private static String WORD;

    public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    static {
        try {
            WORD = SystemClipboardAccess.readClipboard();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedFlavorException e) {
            e.printStackTrace();
        }
    }

    private static String TRANSLATE_WORD = "doubt";
    private final static String PATTERN = ".*\\b" + WORD + "\\b.*";
    static Map<String, ArrayList<String>> composeSentences = new HashMap<>();
    static ArrayList<String> sentences = new ArrayList<>();

    public static void main(String[] args) throws IOException, UnsupportedFlavorException {
        findInEngSub();
        doBoldSent(composeSentences);
        makeAnkiTemplate(composeSentences);
    }

    public String getWord() {
        String word = "car";
        return word;
    }

    public static Map<String, ArrayList<String>> findInEngSub() throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(PATH + FILE_ENG_NAME + ".srt"));

        Scanner sc = new Scanner(new FileReader(PATH + FILE_ENG_NAME + ".srt"));
        String line2 = "";
        while(sc.hasNextLine()) {
            if (!(line2 = sc.nextLine()).isEmpty()){
                System.out.println("true");
            }else {
                System.out.println("false");
            }
        }



        try {
            String line = "";
            String time_code = "";
            while (line != null) {
                line = reader.readLine();

                if (line != null && line.matches(PATTERN)) {
                    sentences.add(line);
                    System.out.println(time_code);
                    String text = line + " " + reader.readLine();
                    System.out.println("text: " + text);
                    findInRusSub(time_code);
                }
                if (line != null && Pattern.matches(".*-->.*", line)) {
                    time_code = line;
                }
            }
            composeSentences.put(WORD, sentences);

        } finally {
            reader.close();
        }
        return composeSentences;
    }

    public static String findInRusSub(String translate) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(PATH + FILE_RUS_NAME + ".srt"));
        String line = "";
        String next_line = "";
        try {
            while (line != null) {
                line = reader.readLine();
                if (line != null && line.equals(translate)) {
                     System.out.println("rus_time_code: " + line);
                    next_line = reader.readLine() + " " + reader.readLine();
                     System.out.println("rus_line: " + next_line);
                }
            }

        } finally {
            reader.close();
        }
        return next_line;
    }

    public static void doBoldSent(Map<String, ArrayList<String>> examples) throws IOException {
        ArrayList<String> strings = new ArrayList<>();

        for (Map.Entry<String, ArrayList<String>> sentencesEntry : examples.entrySet()) {
            for (String sentences : sentencesEntry.getValue()) {
                strings.add(bold(WORD, sentences));
            }
        }
        composeSentences.put(WORD, strings);
    }

    public static String bold(String word, String sentence) {
        Pattern pattern = Pattern.compile(word);
        Matcher m = pattern.matcher(sentence);
        return (m.replaceAll("<b>" + word + "</b>"));
    }

    public static String makeAnkiTemplate(Map<String, ArrayList<String>> composeSentences) {
        StringBuilder builder = new StringBuilder();


        for (Map.Entry<String, ArrayList<String>> sentencesEntry : composeSentences.entrySet()) {
            for (String sentences : sentencesEntry.getValue()) {
                System.out.println("With bold:  " + sentences);
                builder.append(sentences).append("|").append("translate").append("|").append("serial");
            }


        }


        return builder.toString();
    }

    public void saveAnkiIntoFile() {
        //      сохранить как последння строка
    }


}
