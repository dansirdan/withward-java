package com.withward.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.withward.DTO.DestinationDTO;
import com.withward.model.Destination;
import com.withward.model.DestinationRating;
import com.withward.repository.DestinationDAO;
import com.withward.repository.DestinationRatingDAO;

public class DestinationService {

	private DestinationDAO destRepository = new DestinationDAO();
	private DestinationRatingDAO ratingRepository = new DestinationRatingDAO();

	public ArrayList<Destination> getAllDestinations(Integer withlistId) throws SQLException {
		return destRepository.getAll(withlistId);
	}

	public DestinationDTO getOneDestination(Integer destinationId) throws SQLException {

		ArrayList<DestinationRating> ratings = ratingRepository.getAll(destinationId);
		Destination destination = destRepository.getDestination(destinationId);
		return new DestinationDTO(destinationId, destination.getWithlist_id(), destination.getName(),
				destination.getDescription(), destination.getPhoto(), destination.isCompleted(),
				destination.getAverageRating(), ratings);
	}

	public Destination createDestination(Destination destination) throws SQLException {
		return destRepository.insertDestination(destination);
	}

	public Destination updateDestination(Destination destination, Integer id) throws SQLException {
		return destRepository.updateDestination(destination, id);
	}

	public void deleteDestination(Integer destinationId) throws SQLException {
		destRepository.deleteOne(destinationId);
	}
}
