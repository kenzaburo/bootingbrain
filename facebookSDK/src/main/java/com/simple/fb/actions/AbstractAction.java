package com.simple.fb.actions;

import com.simple.fb.SessionManager;
import com.simple.fb.SimpleFacebook;
import com.simple.fb.SimpleFacebookConfiguration;

public abstract class AbstractAction {

	protected SessionManager sessionManager;
	protected SimpleFacebookConfiguration configuration = SimpleFacebook.getConfiguration();

	public AbstractAction(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	public void execute() {
		executeImpl();
	}

	protected abstract void executeImpl();
}
