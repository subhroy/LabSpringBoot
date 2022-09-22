package com.training.lab.batch.writer;

import com.training.lab.entity.NiftyStock;
import com.training.lab.repository.NiftyStockRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NiftyStockBatchWriter implements ItemWriter<NiftyStock> {

    @Autowired
    private NiftyStockRepository niftyStockRepository;

    @Override
    public void write(List<? extends NiftyStock> niftyStocks) throws  Exception{
        this.niftyStockRepository.saveAll(niftyStocks);
    }
}
