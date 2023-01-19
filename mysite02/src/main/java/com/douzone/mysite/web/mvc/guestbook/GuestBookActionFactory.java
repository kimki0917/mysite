package com.douzone.mysite.web.mvc.guestbook;

import com.douzone.mysite.web.mvc.main.MainAction;
import com.douzone.web.mvc.Action;
import com.douzone.web.mvc.ActionFactory;

public class GuestBookActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		if ("list".equals(actionName)) {
			action = new GuestBookAction();
		} else if ("insert".equals(actionName)) {
			action = new AddAction();
		} else if ("deleteForm".equals(actionName)) {
			action = new GuestBookDeleteForm();
		} else if ("delete".equals(actionName)) {
			action = new GuestBookDelete();
		} else {
			action = new MainAction();
		}
		return action;
	}

}
