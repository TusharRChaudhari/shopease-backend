package com.shopease.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shopease.backend.entity.Address;
import com.shopease.backend.entity.User;
import com.shopease.backend.repository.AddressRepository;
import com.shopease.backend.repository.UserRepository;

@Service
public class AddressService 
{
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    
	public AddressService(AddressRepository addressRepository, UserRepository userRepository) 
	{
		this.addressRepository = addressRepository;
		this.userRepository = userRepository;
	}
    
    public Address addAddress(String email, Address address)
    {
    	User user = userRepository.findByEmail(email);
    	
    	if (user == null)
    		return null;
    	
    	address.setUser(user);
    	return addressRepository.save(address);
    	
    }
    
    public List<Address> getAddresses(String email)
    {
    	User user = userRepository.findByEmail(email);
    	
    	if (user == null)
    		return null;
    	
    	return addressRepository.findByUserId(user.getId());
    }
    
    public Address updateAddress(Long addressId, Address updated) 
    {
    	Address existing = addressRepository.findById(addressId).orElse(null);
    	
    	if (existing == null)
	    	return null;
    	
    	existing.setFullName(updated.getFullName());
        existing.setLine1(updated.getLine1());
        existing.setLine2(updated.getLine2());
        existing.setCity(updated.getCity());
        existing.setState(updated.getState());
        existing.setPincode(updated.getPincode());
        existing.setPhone(updated.getPhone());

        return addressRepository.save(existing);
    }
    
    public boolean deleteAddress(Long addressId) 
    {
        Address address = addressRepository.findById(addressId).orElse(null);
        if (address == null) 
        	return false;

        addressRepository.delete(address);
        return true;
    }
}
