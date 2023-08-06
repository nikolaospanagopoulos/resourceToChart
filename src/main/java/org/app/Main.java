package org.app;

import org.app.chartCreator.ChartCreator;
import org.app.fetcher.ApiDataFetcher;
import org.app.oilData.CommoditiesValueMap;
import org.app.user.InvalidRequestError;
import org.app.user.UserChoices;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            String choice = UserChoices.getUserInput();
            ApiDataFetcher fetcher = new ApiDataFetcher();
            List<CommoditiesValueMap> data = fetcher.fetchData(choice);
            if (data == null) {
                throw new InvalidRequestError("Invalid request \n");
            }
            for (CommoditiesValueMap price : data) {
                System.out.println("date: " + price.getDate() + "\n" + "value: " + price.getValue());
            }

            ChartCreator creator = new ChartCreator(data,choice);
            creator.createPDFfile();
        } catch (InvalidRequestError e) {
            System.out.println(e.getMessage());
        }

    }
}