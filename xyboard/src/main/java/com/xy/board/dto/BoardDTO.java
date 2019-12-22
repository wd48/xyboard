package com.xy.board.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class BoardDTO {

	private int num; // 게시물 번호
	@Length(min=2, max=20, message="제목은 2자 이상, 20자 이하 입력하세요.")
	private String title; // 제목
	private int count; // 조회수
	private String name; // 작성자
	private String date; // 날짜
	@NotEmpty(message="내용을 입력하세요.")
	private String contents; // 내용
	private String id;
	private String del_chk; // 삭제 여부
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDel_chk() {
		return del_chk;
	}
	public void setDel_chk(String del_chk) {
		this.del_chk = del_chk;
	}
	
	@Override
	public String toString() {
		return "BoardDTO [num=" + num + ", title=" + title + ", count=" + count + ", name=" + name + ", date=" + date
				+ ", contents=" + contents + ", id=" + id + ", del_chk=" + del_chk + "]";
	}
	
}
