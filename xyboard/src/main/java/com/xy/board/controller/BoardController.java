package com.xy.board.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.xy.board.dto.BoardDTO;
import com.xy.board.dto.Criteria;
import com.xy.board.dto.PageMaker;
import com.xy.board.service.BoardService;

@Controller
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(UsersController.class);
	
	@Autowired
	private BoardService boardService;
	
	// 게시판 페이징
	@RequestMapping(value="/boardPageList")
	public ModelAndView boardPageList(Criteria cri, HttpSession session) {
		ModelAndView mav = null;
		if(session.getAttribute("id") == null) {
			mav = new ModelAndView("redirect");
			mav.addObject("msg", "잘못된 접근 입니다!!");
			mav.addObject("url", "login");
			return mav;
		} else {
			mav = new ModelAndView("/boardPageList");
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);
			pageMaker.setTotalCount(boardService.countBoardList());

			List<Map<String, Object>> list = boardService.pageList(cri);

			mav.addObject("list", list);
			mav.addObject("pageMaker", pageMaker);

			return mav;
		}
	}
	// 게시판 목록
/*	로그인한 정보를 세션에 담고, 세션에 id값이 null일 경우 redirect.jsp로 이동하여 경고창을 띄웁니다. 
	id값이 있을 경우에는 model에 객체를 담아서 boardList.jsp로 반환합니다.	*/
	@RequestMapping(value = "/boardList")
	public String boardList(Model model, HttpSession session) {
		if(session.getAttribute("id") == null) {
			model.addAttribute("msg", "잘못된 접근입니다");
			model.addAttribute("url", "login");
			return "redirect";
		} else {
			model.addAttribute("boardList", boardService.list());
			model.addAttribute("user", session.getAttribute("user"));
			return "boardList";
		}
	}
	
	// 게시글 상세내역
	@RequestMapping(value="/boardRead/{num}")
	public String boardRead(Model model, @PathVariable int num) {
		model.addAttribute("boardDTO", boardService.read(num));
		return "boardRead";
	}
/*boardList.jsp에서 제목을 클릭했을 경우 /boardRead/{num} 컨트롤러를 타게 만들었으므로, 
 * @RequestMapping도 같게 해야합니다. 
 * 또한 @RequestMapping에서 템플릿 변수명을 사용하여 num값을 받아왔으므로, 
 * 파라미터에 @PathVariable를 사용하여 @RequestMapping에서 사용한 템플릿 변수명과 일치시켜줍니다.*/
	
	// a태그 혹은 주소창 입력시 들어오는 곳
	// 바인딩 에러처리를 위해 Model 객체와 model.addAttribute() 추가
	@RequestMapping(value="/boardWrite", method=RequestMethod.GET)
	public String boardWrite(Model model) {
		model.addAttribute("boardDTO", new BoardDTO());
		return "boardWrite";
	}
	// 게시글 작성 : hibernate-validator
	@RequestMapping(value="/boardWrite", method=RequestMethod.POST)
	public String boardWrite(@Valid BoardDTO boardDTO, BindingResult bindingResult, HttpSession session) {
		if(bindingResult.hasErrors()){
			return "boardWrite";
		} else {
			boardDTO.setId((String)session.getAttribute("id"));
			boardService.write(boardDTO);
			return "redirect:/boardPageList";
		}
	}
/*게시글 작성에 대한 처리를 위해 boardWrite라는 이름으로 
 * 메서드 오버로딩을 이용해서 GET과 POST방식으로 각각 처리 할 수 있게 만들었습니다.
 * boardList에서 글작성을 눌러서 a태그 혹은 주소창으로 들어오게 되면, 
 * boardWrite()의 GET 방식을 적용한 메서드로 가게 됩니다. 
 * 
 * 아래의 boardWrite.jsp View파일을 보면 form:form태그를 사용했는데, 
 * boardWrite()의 GET 방식 메서드를 보면 model.addAttribute("boardDTO", new BoardDTO());로 boardDTO 객체를 생성하여 boardDTO라는 Key값으로 JSP로 넘겨주는게 보입니다.
그 이유는 컨트롤러에서 form:form을 사용한 View쪽으로 폼(form) 양식을 바인딩 시켜주기 위해 
form:form의 commandName 즉, 커맨드 객체 이름과 동일하게 model.addAttribute()로 커맨드 객체를 생성해서 보내야 합니다.

반대로 boardWrite.jsp에서 제목과 내용을 작성하고 나서 등록을 누르면 form으로 전송되기 때문에(form은 POST 방식) 
boardWrite의 POST 방식이 적용된 메서드를 찾아갑니다.
이 메서드에서는 Hibernate-validator와 BindingResult를 사용하여 검증을 통해 
오류가 없을 경우 글을 등록하게 하는 메서드 입니다. DTO 쪽에 보면 어노테이션을 사용한것을 볼 수 있습니다.*/
	// 게시글 수정권한 판단
	@RequestMapping(value="/boardEdit/{num}", method=RequestMethod.GET)
	public String boardEdit(@PathVariable int num, Model model, HttpSession session, RedirectAttributes rttr) {
		if(session.getAttribute("id").equals(boardService.read(num).getId())) {
			 model.addAttribute("boardDTO", boardService.read(num));
			return "boardEdit";
		} else {
			rttr.addFlashAttribute("msg", "수정 권한이 없습니다");
			return "redirect:/boardList";
		}
	}

	// 수정 검증 : BindingResut + Validator
	@RequestMapping(value="/boardEdit/{num}", method=RequestMethod.POST)
	public String boardEdit(@Valid BoardDTO boardDTO, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) return "boardEdit";
		else {
			boardService.edit(boardDTO);
			return "redirect:/boardPageList";
		}
	}
	
	// 게시글 삭제
	@RequestMapping(value="/boardDelete/{num}", method=RequestMethod.GET)
	public String boardDelete(@PathVariable int num, Model model, HttpSession session, RedirectAttributes rttr) {
		if(session.getAttribute("id").equals(boardService.read(num).getId())) {
			boardService.delete(boardService.read(num));
			return "redirect:/boardList";
		} else {
			rttr.addFlashAttribute("msg", "삭제 권한이 없습니다.");
			return "redirect:/boardList";
		}
	}
	
}
