package crawlerPack;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class crawlerClass {
    public static void main(String[] args) throws IOException {
        URL startUrl = new URL("https://marksism.space");

        checkLink(startUrl);

        URL nextUrl = getUrl(getDataFromUrl(startUrl), 0); //This sets nextUrl to the first link on the website

        int i = 0;

        while (true) {
            String theHtml = getDataFromUrl(nextUrl);
            nextUrl = getUrl(theHtml, i);
            System.out.println("Attempting to get link from " + nextUrl);
            if (!checkInFile(nextUrl.toString())) { //This checks if the link is already in the file
                System.out.println("The link " + nextUrl.toString() + "did not exist in the file");
                writeToFile(nextUrl.toString()); //This writes the link to the file
            } else {
                System.out.println("The link already exists in the file, and the link is " + nextUrl);
                i++;
            }

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

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(e);
        }

        return returnString;
    }

    /**
     * This method gets the first https link of a html document
     * @param html This is the html you want to use
     * @return It returns the link
     * @throws MalformedURLException If the url is malformed, it throws this
     */
    public static URL getUrl(String html, int itemNUmber) throws MalformedURLException {
        String https = "https://";
        if (html.contains("href=\"" + https)) {
            URL url;
            int indexOfHttps = html.indexOf(https);

            String newHtml = html.substring(indexOfHttps);

            if (itemNUmber == 0) {
                return url = new URL(newHtml.substring(0, newHtml.indexOf("\"")));
            } else {
                for (int i = 1; itemNUmber>=i; i++) {
                    indexOfHttps = newHtml.substring(https.length()).indexOf(https);
                    newHtml = newHtml.substring(indexOfHttps);
                }
                return url = new URL(newHtml.substring(0, newHtml.indexOf("\"")));
            }

        } else {
            return null;
        }
    }

    public static void writeToFile(String append) throws IOException {
        String filename = "links.txt";
        FileWriter fw = new FileWriter(filename,true); //the true will append the new data
        fw.write(append + "\n");//appends the string to the file
        fw.close();
    } //Simply writes to the file, really doesn't need an explanation

    public static boolean checkInFile(String checkString) throws IOException {
        Path thePath = Path.of("/home/markus/git/crawler/links.txt/");
        String list = Files.readString(thePath, StandardCharsets.UTF_8);
        return list.contains(checkString);
    } //This checks if something is in the file

    public static boolean checkLink(URL theUrl) {
        if (theUrl.getProtocol().equals("https")) { //Checks if the staring link is in https
            System.out.println("It worked!");
            try {
                getDataFromUrl(theUrl); //If it worked, it tries to get the data from that site
                return true;
            } catch (Exception e) {
                System.out.println("The protocol was correct, but something else went wrong with the site");
                return false;
            }

        } else {
            System.out.println("It did not work!");
            return false;
        }
    }

}
