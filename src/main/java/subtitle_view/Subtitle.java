package subtitle_view;

import java.io.*;
/*
public class Subtitle {
    public static void main(String[] args) throws IOException {

        String file_name = "Family.Guy.1x01.Death.has.a.Shadow.[filiza.ru]";


        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Igor\\Desktop\\subtitles\\" + file_name + ".srt"));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                line = line.replaceAll("[0-9:,->]","");
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }

            String everything = sb.toString();
            everything = everything.replaceAll("(?m)^\\s*$[\n\r]", "");
            PrintWriter writer = new PrintWriter("C:\\Users\\Igor\\Desktop\\subtitles\\" + file_name + "_parsed.txt", "UTF-8");
            writer.write(everything);
            writer.close();

            System.out.println(everything);
        } finally {
            br.close();
        }

    }


}
*/