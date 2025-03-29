package com.driver.services;

import com.driver.EntryDto.ProductionHouseEntryDto;
import com.driver.model.ProductionHouse;
import com.driver.repository.ProductionHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductionHouseService {

    @Autowired
    ProductionHouseRepository productionHouseRepository;

    public Integer addProductionHouseToDb(ProductionHouseEntryDto productionHouseEntryDto) {
        // 1 ProductionHouse entity ka object banao
        ProductionHouse productionHouse = new ProductionHouse();
        productionHouse.setName(productionHouseEntryDto.getName());
        productionHouse.setAvgRating(4.5); // Default rating

        // 2 Database me save karo
        productionHouse = productionHouseRepository.save(productionHouse);

        // 3 Inserted row ka ID return karo
        return productionHouse.getId();
    }
}
