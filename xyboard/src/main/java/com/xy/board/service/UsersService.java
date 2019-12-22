package com.xy.board.service;

import com.xy.board.dto.UsersDTO;

public interface UsersService {
	public UsersDTO select(UsersDTO usersDTO);
	public boolean checkPw(String id, String pwd);
}
