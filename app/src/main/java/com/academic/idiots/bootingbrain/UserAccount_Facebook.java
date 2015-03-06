package com.academic.idiots.bootingbrain;

public class UserAccount_Facebook {

	//@SerializedName("uid")
	private String id;
	//@SerializedName("name")
	private String name;
	//@SerializedName("email")
	private String mEmail;
	//@SerializedName("first_name")
	private String mFirstName;
	//@SerializedName("last_name")
	private String mLastName;
	//@SerializedName("date_of_birth")
	private String mDOB;
	//@SerializedName("profile_image")
	private String mProfileImage;
	//@SerializedName("gender")
	private String mGender;
	//@SerializedName("locale")
	private String mLocale;
	//@SerializedName("link")
	private String mLink;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return mEmail;
	}

	public void setEmail(String email) {
		mEmail = email;
	}

	public void setFirstName(String firstName) {
		this.mFirstName = firstName;
	}

	public String getFirstName() {
		return mFirstName;
	}

	public void setLastName(String lastName) {
		this.mLastName = lastName;
	}

	public void setDOB(String dOB) {
		this.mDOB = dOB;
	}

	public void setProfileImage(String profileImage) {
		this.mProfileImage = profileImage;
	}

	public void setGender(String gender) {
		this.mGender = gender;
	}

	public void setLocale(String locale) {
		this.mLocale = locale;
	}

	public void setLink(String link) {
		this.mLink = link;
	}

	public String getLastName() {
		return mLastName;
	}

	public String getDOB() {
		return mDOB;
	}

	public String getProfileImage() {
		return mProfileImage;
	}

	public String getGender() {
		return mGender;
	}

	public String getLocale() {
		return mLocale;
	}

	public String getLink() {
		return mLink;
	}
}