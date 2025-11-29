package com.shopease.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopease.backend.entity.Address;
import com.shopease.backend.security.AuthUtil;
import com.shopease.backend.service.AddressService;

@RestController
@RequestMapping("/address")
public class AddressController 
{
    private final AddressService addressService;
    private final AuthUtil authUtil;
    
	public AddressController(AddressService addressService, AuthUtil authUtil) 
	{
		this.addressService = addressService;
		this.authUtil = authUtil;
	}
    
    public ResponseEntity<Address> addAddress(@RequestBody Address address)
    {
    	String email = authUtil.getLoggedInEmail();
    	Address saved = addressService.addAddress(email, address);
    	return ResponseEntity.ok(saved);
    }
    
    @GetMapping
    public ResponseEntity<List<Address>> getAddresses() 
    {
        String email = authUtil.getLoggedInEmail();
        return ResponseEntity.ok(addressService.getAddresses(email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @RequestBody Address address) 
    {
        Address updated = addressService.updateAddress(id, address);
        return (updated == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long id) 
    {
        boolean ok = addressService.deleteAddress(id);
        return ok ? ResponseEntity.ok("Address deleted") : ResponseEntity.notFound().build();
    }
}
