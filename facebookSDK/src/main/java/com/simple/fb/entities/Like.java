package com.simple.fb.entities;

import com.facebook.model.GraphObject;
import com.simple.fb.utils.Utils;

public class Like {

	private User mUser;

	private Like(GraphObject graphObject) {
		mUser = Utils.createUser(graphObject);
	}

	public static Like create(GraphObject graphObject) {
		return new Like(graphObject);
	}

	public User getUser() {
		return mUser;
	}
}
