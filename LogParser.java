import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Extractor implements Runnable {

    private volatile String extracted_data;
    String text, regex;

    public Extractor(String text, String regex) {
        this.text = text;
        this.regex = regex;
    }

    @Override
    public void run() {
        try {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(text);
            if (m.find()) {
                extracted_data = m.group(1); // we're only looking for one group, so get it
            }

        } catch (Exception e) {
        }
    }

    public String getData() {
        return extracted_data;
    }

}

class LogParser {
    public static void name() {

    }

    public static void main(String[] args) throws NullPointerException {
        try {
            FileInputStream fstream = new FileInputStream("access.log");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String line;
            FileWriter file_browser = new FileWriter("textfiles/browser.txt");
            FileWriter file_ip = new FileWriter("textfiles/ip.txt");
            FileWriter file_os = new FileWriter("textfiles/os.txt");
            FileWriter file_date = new FileWriter("textfiles/date.txt");

            /* reading log line by line */
            while ((line = br.readLine()) != null) {

                Extractor temp_browser = new Extractor(line, "(\"\\w+\\/)");
                Extractor ip = new Extractor(line, "(\\b\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\b)");
                Extractor temp_os = new Extractor(line,
                        "(\\([\\w\\d\\s\\.]*;\\s[\\w]*;\\s[\\w\\s\\d]*[;\\s]*[\\w\\d:\\.]*\\))");
                Extractor dateTime = new Extractor(line, "(\\d+\\/\\w+\\/\\d+)");

                Thread b_thread1 = new Thread(temp_browser);
                Thread i_thread = new Thread(ip);
                Thread o_thread1 = new Thread(temp_os);
                Thread d_thread = new Thread(dateTime);

                b_thread1.start();
                i_thread.start();
                o_thread1.start();
                d_thread.start();

                b_thread1.join();
                i_thread.join();
                o_thread1.join();
                d_thread.join();

                String tempB = temp_browser.getData();
                String tempO = temp_os.getData();

                Extractor browser = new Extractor(tempB, "([A-Za-z]{7,})"); // refilterig browser
                Extractor os = new Extractor(tempO, "([A-Za-z]{6,})"); // refilterig os

                Thread b_thread2 = new Thread(browser);
                Thread o_thread2 = new Thread(os);

                b_thread2.start();
                o_thread2.start();

                b_thread2.join();
                o_thread2.join();

                if (browser.getData() != null) {
                    file_browser.write(browser.getData() + "\n");
                }

                if (ip.getData() != null) {
                    file_ip.write(ip.getData() + "\n");
                }

                if (os.getData() != null) {
                    file_os.write(os.getData() + "\n");
                }
                if (dateTime.getData() != null) {
                    file_date.write(dateTime.getData() + "\n");
                }
            }

            file_browser.close();
            file_ip.close();
            file_os.close();
            file_date.close();
            fstream.close();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
