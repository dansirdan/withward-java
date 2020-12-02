package com.withward.DTO;

/**
 * Template class that represents users without email and password
 */
public class UserDTO {
	private Integer id;
	private String username;
	private String photo;

	public UserDTO() {
		super();
	}

	public UserDTO(Integer id, String username, String photo) {
		this.id = id;
		this.username = username;

		this.photo = photo;
	}

	public Integer getId() {
		return this.id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

}
