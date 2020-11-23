package com.withward.service;
import java.util.ArrayList;

import com.withward.model.Destination;
import com.withward.repository.DestinationRepository;

public class DestinationService {

	private DestinationRepository destRepository = new DestinationRepository();
	
	public ArrayList<Destination> getAllDestinations(Integer withlistId) {
		return destRepository.getAll(withlistId);
	}
	
	public Destination getOneDestination(Integer destinationId) {
		return destRepository.getDestination(destinationId);
	}
	
	public void createDestination(Destination destination) {
		destRepository.insertDestination(destination);
	}
	
	public void updateDestination(Destination destination) {
		destRepository.updateDestination(destination);
	}
	
	public void deleteDestination(Integer destinationId) {
		destRepository.deleteOne(destinationId);
	}
}
