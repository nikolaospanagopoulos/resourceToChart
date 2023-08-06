package org.app.fetcher;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.app.oilData.CommoditiesValueMap;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ApiDataFetcher {

    public List<CommoditiesValueMap> fetchData(String choice) {
        try {
            URL apiUrl = new URL("https://www.alphavantage.co/query?function="+choice+"&interval=monthly&apikey=CE6I2Y31HILZY6GT");
            HttpURLConnection conn = (HttpURLConnection) apiUrl.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);

            }
            System.out.println(response.toString());

            reader.close();
            String jsonData = response.toString();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonData);
            if (!jsonNode.hasNonNull("data")) {
                return null;
            }
            JsonNode dataNode = jsonNode.get("data");
            List<CommoditiesValueMap> commodityData = new ArrayList<>();
            if (dataNode.isArray()) {
                for (JsonNode entry : dataNode) {
                    String date = entry.get("date").asText();
                    double value = entry.get("value").asDouble();
                    if (value != 0.0) {
                        commodityData.add(new CommoditiesValueMap(date, value));
                    }
                }
            }
            return commodityData;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
