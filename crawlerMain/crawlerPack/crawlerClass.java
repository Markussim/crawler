package crawlerPack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;

public class crawlerClass {
    public static void main(String[] args) throws IOException, URISyntaxException {
        URL startUrl = new URL("https://marksism.space/");



        if (startUrl.getProtocol().equals("https")) {
            System.out.println("It worked!");
            try {
                System.out.println(getDataFromUrl(startUrl));
            } catch (Exception e) { //Exception
                System.out.println("The protocol was correct, but something else went wrong with the site");
            }

        } else {
            System.out.println("It did not work!");
        }
    }

    /**
     * As the name sugests, this method simply return the content of the URL
     * @param url The url you want to get the data from
     * @return The data of the website
     * @throws IOException
     */
    public static String getDataFromUrl(URL url) throws IOException {
        String returnString;
        InputStream is;
        BufferedReader br;
        String line;

        StringBuffer theBuffer = new StringBuffer();

        is = url.openStream();  // throws an IOException
        br = new BufferedReader(new InputStreamReader(is));

        while ((line = br.readLine()) != null) {
            theBuffer.append(line + "\n");
        }

        returnString = theBuffer.toString();

        return returnString;
    }

}
