package io.paymeter.assessment.infraestructure.controller;

import io.paymeter.assessment.infraestructure.controller.dto.in.CalculationRequest;
import io.paymeter.assessment.infraestructure.controller.dto.out.CalculationResponse;
import io.paymeter.assessment.infraestructure.databaseadapter.repository.PricingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tickets")
@RequiredArgsConstructor
public class TicketController {

	private PricingRepository pricingRepository;

	@PostMapping("/calculate")
	public ResponseEntity<CalculationResponse> calculate(@RequestBody CalculationRequest request) {

		// TODO

		return new CalculationResponse();
	}
}
