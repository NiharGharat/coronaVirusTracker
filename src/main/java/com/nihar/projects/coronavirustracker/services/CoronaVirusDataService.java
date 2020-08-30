package com.nihar.projects.coronavirustracker.services;

import com.nihar.projects.coronavirustracker.model.LocationBean;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CoronaVirusDataService {

    /*
    The url to get the data from
     */
    private final String HTTP_DATA_GET_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    /*
    To persist the data, temp we are using the list => cron job will get entire data, and we will put it in this list to get latest data info
     */
    private List<LocationBean> allStats = new ArrayList<>();

    @PostConstruct
    @Scheduled(cron = "5 * * * * *")
    private void getDataFromGithub() throws URISyntaxException, IOException, InterruptedException {
        /*
        For cron exp checking in time delays
         */
        System.out.println("Current time in milli is " + System.currentTimeMillis());
        /*
        Create a http get request to get the data from github url
         */
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(HTTP_DATA_GET_URL)).build();

        /*
        Send the request and
         */
        HttpResponse<String> send = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        /*
        Create a csv parser to read per line of the response
         */
        CSVParser csvRecords = parseData(send);
        /*
        Populate the needed values of the response to the class varaible in list of string
         */
        populateListWithData(csvRecords);

        System.out.println("All done");
        System.out.println(this.allStats.size());
        System.out.println(this.allStats.get(new Random().nextInt(this.allStats.size())));
    }

    /*
    Create a list in init the instance list at once
     */
    private void populateListWithData(CSVParser csvRecords) {
        /*
        Add all in the instance var  - user req while list is being prepared -> error
        Hence, crete this one, and then add all to the instance main list
         */
        List<LocationBean> newStats = new ArrayList<>();
        for (CSVRecord csvRecord : csvRecords) {
            LocationBean locationBean = new LocationBean();
            locationBean.setProvince(csvRecord.get(0));
            locationBean.setState(csvRecord.get(1));
            locationBean.setTotalCases(Integer.parseInt(csvRecord.get(csvRecord.size() - 1)));
            newStats.add(locationBean);
        }
        this.allStats = newStats;
    }

    /*
    Read the data as a csv records
     */
    private CSVParser parseData(HttpResponse<String> response) throws IOException {
        StringReader stringReader = new StringReader(response.body());
        CSVParser parse = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);
        /*for (CSVRecord record : records) {
            String s = record.get("Province/State");
            System.out.println(s);
            String y = record.get("Country/Region");
            System.out.println(y);
            break;
        }*/
        return parse;
    }
}
