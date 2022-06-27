package com.eaiesb.powerledger; 

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RequestMapping("/api/v1")
@Tag(name = "powerLedger")
@RestController
public class PowerLedgerController {

     @Autowired
     private PowerLedgerService powerLedgerService;
     
     @GetMapping("/powerLedger")
     @Operation(summary = "Find All", description = "Get All Records", tags = {"powerLedger" })
     public Flux<PowerLedger> getAllPowerLedger() {
         return powerLedgerService.getAll()
        		 .defaultIfEmpty(new PowerLedger())
        		 .onErrorStop();
     }
          
     @GetMapping(value="/powerLedgerStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
     @Operation(summary = "Retrive records as Stream", description = "Get All Streams", tags = {"powerLedger" })
     public Flux<PowerLedger> getAllPowerLedgers() {
             return powerLedgerService.getAll().delayElements(Duration.ofSeconds(1))
            		 .defaultIfEmpty(new PowerLedger())
                     .onErrorStop();
     }
     
     @GetMapping("/powerLedger/{id}")
     @Operation(summary = "Find By Id", description = "Get Records By Id", tags = {"powerLedger" })
     public Mono<ResponseEntity<PowerLedger>> getPowerLedgerById(@PathVariable String id) {
             return powerLedgerService.getById(id)
            		 .map(ResponseEntity::ok)
            		 .defaultIfEmpty(ResponseEntity.noContent().build())
            		 .onErrorStop();
     }
     
     @GetMapping("/findByPostCodesBetween/{start}/{end}")
     @Operation(summary = "Find By PostalCodes", description = "Get PostalCodes In Between", tags = {"powerLedger" })
     public Flux<PowerLedger> getPostalCodesByRange(@PathVariable int start, @PathVariable int end) {
             return powerLedgerService.getByRange(start, end)
            		 .defaultIfEmpty(new PowerLedger())
                     .onErrorStop();
     }
     
     @GetMapping("/aggregationReports")
     @Operation(summary = "Reports with Statistics group by name", description = "Get All AggregationReports", tags = {"powerLedger" })
     public Object getAggregationReports() {   
    	 	return powerLedgerService.getAggregation();
     }
     
     @GetMapping("/findByPostCodesBetweengroupByNameAnd/{start}/{end}")
     @Operation(summary = "Retrive records Between two PostCodes with Statistics and group by name", description = "Get PostalCodes GroupByName", tags = {"powerLedger" })
     public Flux<PowerLedgerReport> getPostalCodeByRangegroupByNameAnd(@PathVariable int start, @PathVariable int end) {
             return powerLedgerService.getByRangegroupByNameAnd(start, end)
            		 .defaultIfEmpty(new PowerLedgerReport())
                     .onErrorStop();
     }

     @PostMapping("/powerLedger")
     @Operation(summary = "Create Record", description = "Create Record", tags = {"powerLedger" })
     public Mono<ResponseEntity <PowerLedger>> createPowerLedger(@Valid @RequestBody PowerLedger powerLedger) {
             return powerLedgerService.create(powerLedger)
            		 .map(ResponseEntity::ok)
            		 .defaultIfEmpty(ResponseEntity.noContent().build())
            		 .onErrorStop();
     }

     @PutMapping("/powerLedger/{id}")
     @Operation(summary = "Update Record", description = "Update Record By Id", tags = {"powerLedger" })
     public Mono<ResponseEntity <PowerLedger>> updatePowerLedgerById(@PathVariable String id, @Validated @RequestBody PowerLedger powerLedger) {
             return powerLedgerService.update(powerLedger, id)
            		 .map(ResponseEntity::ok)
            		 .defaultIfEmpty(ResponseEntity.noContent().build())
            		 .onErrorStop();
     }

     @DeleteMapping("/powerLedger/{id}")
     @Operation(summary = "Delete Record", description = "delete Record By Id", tags = {"powerLedger" })
     public Mono<ResponseEntity<Void>> deletePowerLedgerById(@PathVariable String id) {
             return powerLedgerService.delete(id)
            		 .map(ResponseEntity::ok)
            		 .defaultIfEmpty(ResponseEntity.noContent().build())
            		 .onErrorStop();
     }

}

