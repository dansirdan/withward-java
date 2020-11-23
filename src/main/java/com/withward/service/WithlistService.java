package com.withward.service;
import java.util.ArrayList;

import com.withward.model.Withlist;
import com.withward.repository.WithlistRepository;

public class WithlistService {
	private WithlistRepository wlRepo = new WithlistRepository();
	
	public ArrayList<Withlist> getAllWithlists(Integer userId) {
		return wlRepo.getAll(userId);
	}
	
	public Withlist getOneWithlist(Integer withlistId) {
		return wlRepo.getWithlist(withlistId);
	}
	
	public void createWithlist(Withlist withlist) {
		wlRepo.insertWithlist(withlist);
	}
	
	public void updateWithlist(Withlist withlist) {
		wlRepo.updateWithlist(withlist);
	}
	
	public void deleteWithlist(Integer withlistId) {
		wlRepo.deleteOne(withlistId);
	}
}
