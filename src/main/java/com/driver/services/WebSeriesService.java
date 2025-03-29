package com.driver.services;

import com.driver.EntryDto.WebSeriesEntryDto;
import com.driver.model.ProductionHouse;
import com.driver.model.WebSeries;
import com.driver.repository.ProductionHouseRepository;
import com.driver.repository.WebSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebSeriesService {

    @Autowired
    WebSeriesRepository webSeriesRepository;

    @Autowired
    ProductionHouseRepository productionHouseRepository;

    public Integer addWebSeries(WebSeriesEntryDto webSeriesEntryDto) throws Exception {

        // Check if the series already exists
        if (webSeriesRepository.findBySeriesName(webSeriesEntryDto.getSeriesName()).isPresent()) {
            throw new Exception("Series is already present");
        }

        //  First check if the Production House exists
        if (!productionHouseRepository.existsById(webSeriesEntryDto.getProductionHouseId())) {
            throw new Exception("Production House not found in the system");
        }

        // Retrieve the production house from the database
        ProductionHouse productionHouse = productionHouseRepository.findById(webSeriesEntryDto.getProductionHouseId())
                .orElseThrow(() -> new Exception("Production House not found"));

        // Create a new WebSeries object
        WebSeries newWebSeries = new WebSeries(
                webSeriesEntryDto.getSeriesName(),
                webSeriesEntryDto.getAgeLimit(),
                webSeriesEntryDto.getRating(),
                webSeriesEntryDto.getSubscriptionType(),
                productionHouse
        );

        // Save the new WebSeries first
        webSeriesRepository.save(newWebSeries);

        // Update the production house rating
        updateProductionHouseRating(productionHouse);

        // Save the updated ProductionHouse
        productionHouseRepository.save(productionHouse);

        return newWebSeries.getId();
    }

    private void updateProductionHouseRating(ProductionHouse productionHouse) {
        List<WebSeries> seriesList = webSeriesRepository.findByProductionHouse(productionHouse);

        double totalRating = 0;
        for (WebSeries series : seriesList) {
            totalRating += series.getRating();
        }

        double averageRating = seriesList.isEmpty() ? 0 : totalRating / seriesList.size();
        productionHouse.setAverageRating(averageRating);
    }
}
