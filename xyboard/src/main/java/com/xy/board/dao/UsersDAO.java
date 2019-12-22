package com.xy.board.dao;

import com.xy.board.dto.UsersDTO;

public interface UsersDAO {
	public UsersDTO select(UsersDTO usersDTO);
	public boolean checkPw(String id, String pwd);
}
