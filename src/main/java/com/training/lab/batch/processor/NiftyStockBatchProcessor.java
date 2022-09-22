package com.training.lab.batch.processor;

import com.training.lab.entity.NiftyStock;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NiftyStockBatchProcessor implements ItemProcessor<NiftyStock, NiftyStock> {

  @Override
  public NiftyStock process(NiftyStock niftyStock) throws Exception {
    niftyStock.setTimeStamp(LocalDateTime.now());
    return niftyStock;
  }
}
