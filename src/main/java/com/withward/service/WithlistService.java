package com.withward.service;
import java.util.ArrayList;

import com.withward.model.Withlist;
import com.withward.repository.WithlistDAO;

public class WithlistService {
	private WithlistDAO wlRepo = new WithlistDAO();
	
	public ArrayList<Withlist> getAllWithlists(Integer userId) {
		return wlRepo.getAll(userId);
	}
	
	public Withlist getOneWithlist(Integer withlistId) {
		return wlRepo.getWithlist(withlistId);
	}
	
	public boolean isAdmin(Integer user_id, Integer withlist_id) {
		return wlRepo.isAdmin(user_id, withlist_id);
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
