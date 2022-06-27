package com.eaiesb.powerledger;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PowerLedger {

	@Id
	public String id;
    private String name;
    private int postalCode;
    private int wattCapacity;
}


