package com.xy.board.service;

import java.util.List;
import java.util.Map;

import com.xy.board.dto.BoardDTO;
import com.xy.board.dto.Criteria;

public interface BoardService {
	public List<BoardDTO> list();
	public BoardDTO read(int num);
	public void write (BoardDTO boardDTO);
	public int edit(BoardDTO boardDTO);
	public void delete(BoardDTO boardDTO);
	List<Map<String, Object>> pageList(Criteria cri);
	public int countBoardList();
}
