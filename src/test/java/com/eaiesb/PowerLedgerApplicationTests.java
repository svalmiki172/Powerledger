package com.eaiesb;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.eaiesb.powerledger.PowerLedger;
import com.eaiesb.powerledger.PowerLedgerController;
import com.eaiesb.powerledger.PowerLedgerService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(PowerLedgerController.class)
@Import(PowerLedgerService.class)
public class PowerLedgerApplicationTests {	
	
	@Autowired
	public WebTestClient webTestClient;
	
	@MockBean
	public PowerLedgerService powerLedgerService;
	
	@Test
	public void createPowerLedgerTest() {
		PowerLedger powerLedger = new PowerLedger();
		powerLedger.setId("1");
		powerLedger.setName("Hyd");
		powerLedger.setPostalCode(500030);
		powerLedger.setWattCapacity(100);
		Mockito.when(powerLedgerService.create(powerLedger)).thenReturn(Mono.just(powerLedger));
		
		webTestClient.post().uri("/api/v1/powerLedger")
		.body(Mono.just(powerLedger),PowerLedger.class)
		.exchange()
		.expectStatus().isOk();//200
	}
	
	@Test
	public void getAllPowerLedgerTest() {
		Flux<PowerLedger> powerLedger = Flux.just(new PowerLedger("1","Hyd",500030,1000),
				new PowerLedger("2","Knl",500031,2000));
		Mockito.when(powerLedgerService.getAll()).thenReturn(powerLedger);
		
		webTestClient.get().uri("/api/v1/powerLedger")
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(PowerLedger.class);				
		
		Mockito.verify(powerLedgerService).getAll();
	}
	
	@Test
	public void getPowerLedgerByIdTest() {
		Mono<PowerLedger> powerLedger = Mono.just(new PowerLedger("1","Hyd",500030,1000));
		Mockito.when(powerLedgerService.getById("1")).thenReturn(powerLedger);

		webTestClient.get().uri("/api/v1/powerLedger/1")
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(PowerLedger.class);

		Mockito.verify(powerLedgerService).getById("1");
	}
	
		
	@Test
	public void updatePowerLedgerTest() {
		PowerLedger powerLedger = new PowerLedger();
		powerLedger.setId("1");
		powerLedger.setName("Hyd");
		powerLedger.setPostalCode(500032);
		powerLedger.setWattCapacity(100);
		Mockito.when(powerLedgerService.update(powerLedger, "1")).thenReturn(Mono.just(powerLedger));
		
		webTestClient.put().uri("/api/v1/powerLedger/1")
		.body(Mono.just(powerLedger),PowerLedger.class)
		.exchange()
		.expectStatus().isOk();//200
	}
	
	@Test
	public void deletePowerLedgerTest() {
		Mono<Void> voidReturn  = Mono.empty();
		Mockito.when(powerLedgerService.delete("1")).thenReturn(voidReturn);
		
		webTestClient.delete().uri("/api/v1/powerLedger/1")
        .exchange()
        .expectStatus().isNoContent();//204
	}
}
