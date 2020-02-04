package subtitle_view;

import java.awt.*;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnkiHelper {
    private final static String FILE_ENG_NAME = "eng";
    private final static String FILE_RUS_NAME = "rus";
    private static String PATH = "d:\\FILMs\\FamilyGuy\\Family Guy - Season 1\\";
    private static String WORD;
    static Map<String, ArrayList<String>> composeSentences = new HashMap<>();
    static ArrayList<String> sentences = new ArrayList<>();

    static {
        try {
            WORD = SystemClipboardAccess.readClipboard();
        } catch (IOException | UnsupportedFlavorException e) {
            e.printStackTrace();
        }
    }

    private final static String PATTERN = ".*\\b" + WORD + "\\b.*";

    public static void main(String[] args) throws Exception {
            new TrayHelper();
            findInEngSub();
            doBoldSent(composeSentences);
            makeAnkiTemplate(composeSentences);
    }


    private static Map<String, ArrayList<String>> findInEngSub() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(PATH + FILE_ENG_NAME + ".srt"));

        try {
            String line = "";
            String prev_line = "";
            String next_line;
            String time_code = "";

            while (line != null) {
                line = reader.readLine();

                if (line != null && line.matches(PATTERN)) {

                    next_line = reader.readLine();
                    //TODO если слово уже есть в Мапе то мы просто дописываем ее ArrayList

                    if (!reader.readLine().isEmpty() && !prev_line.equals(time_code)) {
                        line = prev_line + " " + line;
                    } else {
                        line = line + " " + next_line;
                    }
                    sentences.add(line);
                }
                prev_line = line;

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

    public static void makeAnkiTemplate(Map<String, ArrayList<String>> composeSentences) throws IOException {

        StringBuilder builder = new StringBuilder();

        for (Map.Entry<String, ArrayList<String>> sentencesEntry : composeSentences.entrySet()) {
            for (String sentences : sentencesEntry.getValue()) {
                builder
                        .append(WORD)
                        .append(" | ")
                        .append("translate")
                        .append(" | ")
                        .append(sentences)
                        .append(" | ")
                        .append("word_class")
                        .append(" | ")
                        .append("serial")
                        .append("\n");

            }
        }
        saveAnkiIntoFile(builder.toString());
    }

    public static void saveAnkiIntoFile(String text) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter("C:\\Users\\Igor\\Desktop\\subtitles\\Anki_Parsed.txt", true));
        pw.write(text);
        pw.close();
    }

}
