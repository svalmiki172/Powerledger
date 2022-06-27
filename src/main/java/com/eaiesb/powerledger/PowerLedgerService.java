package com.eaiesb.powerledger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PowerLedgerService {

	@Autowired
	private PowerLedgerRepository powerLedgerRepo;
	
	public Flux<PowerLedger> getAll() {
		return powerLedgerRepo.findAll();
	}
	
	public Mono<PowerLedger> getById(String id) {
		return powerLedgerRepo.findById(id);
	}
	
	public Flux<PowerLedger> getByRange(int start, int end) {
		return powerLedgerRepo.findByPostalCodeBetween(start, end, Sort.by(Sort.Direction.ASC, "name"));
	}
	
	public Flux<PowerLedgerReport> getAggregation() {
		return powerLedgerRepo.groupByNameAnd("name", Sort.by(Sort.Direction.ASC, "name"));
	}
	
	public Flux<PowerLedgerReport> getByRangegroupByNameAnd(int start, int end) {
		return powerLedgerRepo.findPowerLedgersByPostalCodeBetweengroupByNameAnd(start, end, "name", Sort.by(Sort.Direction.ASC, "name"));
	}
	
	public Mono<PowerLedger> create(PowerLedger powerLedger) {
		return powerLedgerRepo.save(powerLedger);
	}
	
	public Mono<PowerLedger> update(PowerLedger powerLedger, String id) {
		powerLedger.setId(id);
		return powerLedgerRepo.save(powerLedger);
	}
	
	public Mono<Void> delete(String id) {
		return powerLedgerRepo.deleteById(id);
	}
}
