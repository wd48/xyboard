package com.xy.board.dao;

import java.util.List;
import java.util.Map;

import com.xy.board.dto.BoardDTO;
import com.xy.board.dto.Criteria;

public interface BoardDAO {
	public List<BoardDTO> list();
	public BoardDTO select(int num);
	public void insert(BoardDTO boardDTO);
	public int update(BoardDTO boardDTO);
	public void delete(BoardDTO boardDTO);
	public int updateReadCount(int num);
	public List<Map<String, Object>> pageList(Criteria cri);
	public int countBoardList();
}
