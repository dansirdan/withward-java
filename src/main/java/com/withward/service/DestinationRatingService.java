package com.withward.service;
import java.util.ArrayList;

import com.withward.model.DestinationRating;
import com.withward.repository.DestinationRatingDAO;

public class DestinationRatingService {
	private DestinationRatingDAO destRatingRepo = new DestinationRatingDAO();
	
	public ArrayList<DestinationRating> getAllDestinationRatings(Integer destination_id) {
		return destRatingRepo.getAll(destination_id);
	}
	
	public DestinationRating getOneRating(Integer destinationRating_id) {
		return destRatingRepo.getDestinationRating(destinationRating_id);
	}
	
	public void createDestinationRating(DestinationRating destination_rating) {
		destRatingRepo.updateDestinationRating(destination_rating);
	}
	
	public void updateDestinationRating(Integer destinationRating_id) {
		destRatingRepo.getDestinationRating(destinationRating_id);
	}

}
