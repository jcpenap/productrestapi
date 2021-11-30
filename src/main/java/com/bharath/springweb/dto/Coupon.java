package com.bharath.springweb.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Coupon {
    private Long id;
    private String code;
    private BigDecimal discount;
    private String expDate;
}
