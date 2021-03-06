package dev.jeffersonfreitas.myfinance.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.jeffersonfreitas.myfinance.api.service.BankAgenceService;
import dev.jeffersonfreitas.myfinance.api.service.BankService;
import dev.jeffersonfreitas.myfinance.model.dto.BankAgenceDTO;
import dev.jeffersonfreitas.myfinance.model.entity.Bank;
import dev.jeffersonfreitas.myfinance.model.entity.BankAgence;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/bankagence")
@AllArgsConstructor
public class BankAgenceController {
	
	private BankAgenceService service;
	private BankService bankService;
	private ModelMapper modelMapper;
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BankAgence create(@RequestBody @Valid BankAgenceDTO dto) {
		Bank bank = bankService.findById(dto.getBank());
		BankAgence agence = modelMapper.map(dto, BankAgence.class);
		agence.setBank(bank);
		return agence = service.save(agence);
	}
	
	
	@GetMapping("{id}")
	public BankAgence getBankAgence(@PathVariable Long id) {
		return service.findById(id);
	}
	
	
	@GetMapping("all")
	public List<BankAgence> findAll(){
		return service.findAllOrderByAgence();
	}
	
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
	
	
	@PutMapping("{id}")
	public BankAgence update(@PathVariable Long id, @Valid @RequestBody BankAgenceDTO dto) {
		BankAgence agence = service.findById(id);
		Bank bank = bankService.findById(dto.getBank());
		agence.setAgence(dto.getAgence());
		agence.setBank(bank);
		return service.save(agence);
	}
	
	
	@GetMapping
	public Page<BankAgence> filter (@RequestBody BankAgenceDTO dto, Pageable pageable){
		BankAgence filter = new BankAgence();
		
		if(dto.getBank() != null) {
			Bank bank = bankService.findById(dto.getBank());
			filter.setBank(bank);
		}
		
		modelMapper.map(dto, filter);
		Page<BankAgence> result = service.filter(filter, pageable);
		return new PageImpl<>(result.getContent(), pageable, result.getTotalElements());
	}
	

}
