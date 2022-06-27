package com.eaiesb.powerledger;


import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux; 

@Repository
public interface PowerLedgerRepository extends ReactiveMongoRepository <PowerLedger, String>,ReactiveSortingRepository<PowerLedger, String>  {

    public Flux<PowerLedger> findByPostalCodeBetween(int start, int end,Sort sort);
    
 // aggregation with parameter replacement

    @Aggregation("{ '$group': { 'id' : '$name', count : { $sum : 1 }, avg_wattCapacity: { $avg: '$wattCapacity'},  max_wattCapacity: { $max: '$wattCapacity'}, min_wattCapacity: { $min: '$wattCapacity'},names : { $addToSet : '$?0' } } }")
    Flux<PowerLedgerReport> groupByNameAnd(String property,Sort sort);
    
    @Aggregation("{ 'postalCode' : { $gt: ?0, $lt: ?1 } }")
    Flux<PowerLedger> findPowerLedgersByPostalCodeBetween(int postalCodeGT, int postalCodeLT);
    
    @Aggregation(pipeline = {"{ '$match': { 'postalCode' : { $gt: ?0, $lt: ?1 } }}","{ '$group': { '_id' : '$name', count : { $sum : 1 }, avg_wattCapacity: { $avg: '$wattCapacity'},  max_wattCapacity: { $max: '$wattCapacity'}, min_wattCapacity: { $min: '$wattCapacity'},names : { $addToSet : '$?2' } } }"})
		Flux<PowerLedgerReport> findPowerLedgersByPostalCodeBetweengroupByNameAnd(int postalCodeGT, int postalCodeLT,String property,Sort sort);

}