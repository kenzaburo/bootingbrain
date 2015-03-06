package com.simple.fb.actions;

import com.simple.fb.SessionManager;
import com.simple.fb.utils.GraphPath;

public class GetTaggableFriendsAction  extends GetFriendsAction {

	public GetTaggableFriendsAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	@Override
	protected String getGraphPath() {
		return String.format("%s/%s", getTarget(), GraphPath.TAGGABLE_FRIENDS);
	}

}
