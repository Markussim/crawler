package crawlerPack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class crawlerClass {
    public static void main(String[] args) throws IOException {
        URL startUrl = new URL("https://google.com");

        if (startUrl.getProtocol().equals("https")) { //Checks if the link is in https
            System.out.println("It worked!");
            try {
                getDataFromUrl(startUrl); //If it worked, it tries to get the data from that site
            } catch (Exception e) {
                System.out.println("The protocol was correct, but something else went wrong with the site");
            }

        } else {
            System.out.println("It did not work!");
        }

        URL nextUrl = getUrl(getDataFromUrl(startUrl));
        System.out.println(nextUrl);

        while (true) {
            String theHtml = getDataFromUrl(nextUrl);
            System.out.println(getUrl(theHtml));
            nextUrl = getUrl(theHtml);
        }
    }

    /**
     * As the name sugests, this method simply return the content of the URL
     * @param url The url you want to get the data from
     * @return The data of the website
     * @throws IOException Something went wrong
     */
    public static String getDataFromUrl(URL url) throws IOException { //Do not worry about this, it's copypasted
        String returnString;
        InputStream is;
        BufferedReader br;
        String line;

        StringBuilder theBuffer = new StringBuilder(); //A string buffer is needed to append

        is = url.openStream();  // throws an IOException
        br = new BufferedReader(new InputStreamReader(is));

        while ((line = br.readLine()) != null) {
            theBuffer.append(line).append("\n");
        }

        returnString = theBuffer.toString();

        return returnString;
    }

    /**
     * This method gets the first https link of a html document
     * @param html This is the html you want to use
     * @return It returns the link
     * @throws MalformedURLException If the url is malformed, it throws this
     */
    public static URL getUrl(String html) throws MalformedURLException {
        URL url = null;
        if (html.contains("href=\"https://")) {
            String https = "https://"; //This is what it searches for

            String afterHttps = html.substring(html.indexOf(https)); //This creates a string of everything that's after the first occurrence

            url = new URL(afterHttps.substring(0, afterHttps.indexOf("\""))); //This is a substring of afterHttps, where it goes from 0 to the length of the link (Really just the first " but that's the same thing)
        } else {
            System.out.println("There is not a https link somewhere");
        }
        return url;
    }

}
