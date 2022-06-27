package com.eaiesb;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.eaiesb.powerledger.PowerLedger;
import com.eaiesb.powerledger.PowerLedgerController;
import com.eaiesb.powerledger.PowerLedgerRepository;
import com.eaiesb.powerledger.PowerLedgerService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@WebFluxTest(PowerLedgerController.class)
public class PowerLedgerApplicationTests {
	
	@Autowired
	public WebTestClient webTestClient;
	
	@MockBean
	private PowerLedgerService powerLedgerService;
	
	@Test
	public void getAllPowerLedgerTest() {
		Flux<PowerLedger> powerLedger = Flux.just(new PowerLedger("1","Hyd",500030,1000),
				new PowerLedger("2","Knl",500031,2000));
		when(powerLedgerService.getAll()).thenReturn(powerLedger);
		
		Flux<PowerLedger> responseBody = webTestClient.get().uri("/api/v1/powerLedger")
				.exchange()
				.expectStatus().isOk()
				.returnResult(PowerLedger.class)
				.getResponseBody();
		
		StepVerifier.create(responseBody)
		.expectSubscription()
		.expectNext(new PowerLedger("1","Hyd",500030,1000))
		.expectNext(new PowerLedger("2","Knl",500031,2000))
		.verifyComplete();
	}
	
	@Test
	public void getPowerLedgerByIdTest(){
		Mono<PowerLedger> powerLedger = Mono.just(new PowerLedger("2","Knl",500031,2000));
		when(powerLedgerService.getById("2")).thenReturn(powerLedger);

		Flux<PowerLedger> responseBody = webTestClient.get().uri("/api/v1/powerLedger/2")
				.exchange()
				.expectStatus().isOk()
				.returnResult(PowerLedger.class)
				.getResponseBody();

		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNextMatches(p->p.getName().equals("Knl"))
				.verifyComplete();
	}
	
	
	
}
