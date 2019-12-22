package com.xy.board.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xy.board.dto.BoardDTO;
import com.xy.board.dto.Criteria;

@Repository
public class BoardDAOImpl implements BoardDAO{
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public List<BoardDTO> list() {
		return sqlSessionTemplate.selectList("boardDAO.list");
	}
/*	sqlSessionTemplate에 있는 내장 메소드인 selectList 괄호안의 boardDAO.list는 namespace.id를 뜻합니다.
	Mapper.xml이 하나일 경우 namespace를 생략해도 되지만, 2개이상 존재할 경우에는 꼭 namespace명을 작성하여 위와같은 방식으로 사용해야 합니다.*/
	
	@Override
	public BoardDTO select(int num) {
		BoardDTO dto = (BoardDTO)sqlSessionTemplate.selectOne("boardDAO.select", num);
		return dto;
	}
	
	@Override
	public void insert(BoardDTO boardDTO) {
		sqlSessionTemplate.insert("boardDAO.insert", boardDTO);
	}
	
	@Override
	public int update(BoardDTO boardDTO) {
		return sqlSessionTemplate.update("boardDAO.update", boardDTO);
	}
	
	@Override
	public void delete(BoardDTO boardDTO) {
		sqlSessionTemplate.delete("boardDAO.delete", boardDTO);
	}
	
	@Override
	public int updateReadCount(int num) {
		return sqlSessionTemplate.update("boardDAO.updateCount", num);
	}
	
	// Criteria 객체에 담아서 SQL 매핑에 보낼 파라미터
	// 특정 페이지 게시글의 행(pageStart)과 페이지당 보여줄 게시글의 갯수(perPageNum)
	@Override
	public List<Map<String, Object>> pageList(Criteria cri) {
		return sqlSessionTemplate.selectList("boardDAO.pageList", cri);
	}

	@Override
	public int countBoardList() {
		return sqlSessionTemplate.selectOne("boardDAO.countBoardList");
	}
}
