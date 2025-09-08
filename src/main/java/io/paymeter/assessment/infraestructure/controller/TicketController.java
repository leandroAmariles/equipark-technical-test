package io.paymeter.assessment.infraestructure.controller;

import io.paymeter.assessment.applications.services.CalculatingService;
import io.paymeter.assessment.infraestructure.controller.dto.in.CalculationRequest;
import io.paymeter.assessment.infraestructure.controller.dto.out.CalculationResponse;
import io.paymeter.assessment.infraestructure.databaseadapter.repository.PricingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tickets")
@RequiredArgsConstructor
public class TicketController {

	private final CalculatingService calculatingService;

	@PostMapping("/calculate")
	public ResponseEntity<CalculationResponse> calculate(@RequestBody CalculationRequest request) {
		return new ResponseEntity<>(calculatingService.calculateTicket(request), HttpStatus.OK);
	}
}
