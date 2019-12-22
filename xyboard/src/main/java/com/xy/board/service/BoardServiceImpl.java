package com.xy.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xy.board.dao.BoardDAO;
import com.xy.board.dto.BoardDTO;
import com.xy.board.dto.Criteria;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardDAO boardDAO;
	
	@Override
	public List<BoardDTO> list() {
		return boardDAO.list();
	}
	
	@Override
	public BoardDTO read(int num) {
		boardDAO.updateReadCount(num);
		return boardDAO.select(num);
	}
	
	@Override
	public void write(BoardDTO boardDTO) {
		boardDAO.insert(boardDTO);
	}
	
	@Override
	public int edit(BoardDTO boardDTO) {
		return boardDAO.update(boardDTO);
	}
	
	@Override
	public void delete(BoardDTO boardDTO) {
		boardDAO.delete(boardDTO);
	}
	
	@Override
	public List<Map<String, Object>> pageList(Criteria cri) {
		return boardDAO.pageList(cri);
	}

	@Override
	public int countBoardList() {
		return boardDAO.countBoardList();
	}
}

