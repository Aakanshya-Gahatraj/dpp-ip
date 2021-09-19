import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class map {

    public static String getGeolocation(String ip) throws Exception {
        URL worldmap = new URL("http://geolocation-db.com/json/" + ip);
        URLConnection wm = worldmap.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(wm.getInputStream()));
        String inputLine;
        StringBuilder output = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
            output.append(inputLine);
        in.close();
        return output.toString();
    }

    public static void createGeolocationFile(String file) throws Exception {
        List<String> myArray = new ArrayList<String>();

        try {
            FileInputStream fstream = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String line;

            while ((line = br.readLine()) != null) {
                myArray.add(getGeolocation(line));
            }
            br.close();

        } catch (Exception e) {
        } finally {
            FileWriter f_out = new FileWriter("textfiles/geolocations.txt");
            int i = 0;
            f_out.write("[");

            while (i < myArray.size()) {
                f_out.write(myArray.get(i) + ",\n");
                i++;
            }
            f_out.write("]");

            f_out.close();
            System.out.println("COMPLETED");
        }
    }

    public static void main(String[] args) throws Exception {
        createGeolocationFile("textfiles/ip.txt");
    }
}