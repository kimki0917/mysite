package com.douzone.mysite.exception;

import java.sql.SQLException;

public class UserRepositoryException extends RuntimeException {

	public UserRepositoryException() {
		super("UserRepositoryException Occurs");
	}

	public UserRepositoryException(SQLException e) {
		super(e);
	}
}
