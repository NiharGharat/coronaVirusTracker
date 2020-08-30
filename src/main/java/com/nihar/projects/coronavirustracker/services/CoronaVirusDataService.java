package com.nihar.projects.coronavirustracker.services;

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
import java.util.List;

@Service
public class CoronaVirusDataService {

    private final String HTTP_DATA_GET_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    @PostConstruct
    @Scheduled(cron = "0/5 * * * * *")
    private void getDataFromGithub() throws URISyntaxException, IOException, InterruptedException {
        /*
        For cron exp checking in time delays
         */
        System.out.println(System.currentTimeMillis());
        /*
        Create a http get request to get the data from github url
         */
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(HTTP_DATA_GET_URL)).build();

        /*
        Send the request and
         */
        HttpResponse<String> send = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        parseData(send);
    }

    /*

     */
    private void parseData(HttpResponse<String> response) throws IOException {
        StringReader stringReader = new StringReader(response.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);
        for (CSVRecord record : records) {
            String s = record.get("Province/State");
            System.out.println(s);
            String y = record.get("Country/Region");
            System.out.println(y);
            break;
        }

    }
}
