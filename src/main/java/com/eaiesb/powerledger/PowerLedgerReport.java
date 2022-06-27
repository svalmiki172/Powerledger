package com.eaiesb.powerledger;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PowerLedgerReport {

	@Id
    public String name;

    private Long count;

    private Long avg_wattCapacity;
    private Long max_wattCapacity;
    private Long min_wattCapacity;
}
