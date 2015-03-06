package com.simple.fb.actions;

import com.simple.fb.SessionManager;
import com.simple.fb.utils.GraphPath;

public class GetInvitableFriendsAction  extends GetFriendsAction {

	public GetInvitableFriendsAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	@Override
	protected String getGraphPath() {
		return String.format("%s/%s", getTarget(), GraphPath.INVITABLE_FRIENDS);
	}

}
