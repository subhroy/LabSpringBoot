package com.training.lab.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class NiftyStock {
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @JsonFormat(pattern = "yyyy-mm-dd")
  private LocalDate tradingDate;

  private String symbol;
  private String securityType;
  private double prevClose;
  private double open;
  private double high;
  private double low;
  private double lastPrice;
  private double close;
  private double volWeightAvgPrice; // volume Weighted Average Price
  private long volume;
  private String turnOver;
  private String deliverableVolume;
  private String deliverablePercent;
  private LocalDateTime timeStamp;
}
