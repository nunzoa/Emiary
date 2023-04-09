package com.emiary.controller;

import com.emiary.domain.*;
import com.emiary.service.DiaryService;
import com.emiary.service.FriendService;
import com.emiary.util.EmotionAnalyzer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("friend")
public class FriendController {

	@Autowired
	FriendService friendService;

	@GetMapping("findFriend")
	public String findFriend() {

		return "friendView/findFriend";
	}

	@ResponseBody
	@GetMapping("getFriendInfo")
	public List<FindFriend> getFriendInfo(@AuthenticationPrincipal UserDetails userDetails){
		//자기가 아닌 정보만을 불러와야 함
		List<FindFriend> friendList = friendService.getFriendInfo(userDetails.getUsername());
		log.debug("friendList : {}", friendList);
		return friendList;
	}


	@ResponseBody
	@GetMapping("like")
	public String getLike(String email, @AuthenticationPrincipal UserDetails userDetails){
		String check = friendService.likeFriend(email, userDetails.getUsername());
		return check;
	}


//	친구리스트 불러오는 메소드
	@ResponseBody
	@GetMapping("friendList")
	public List<FindFriend> friendList(@AuthenticationPrincipal UserDetails userDetails){
		List<FindFriend> matchedFriendList = friendService.friendList(userDetails.getUsername());
		return matchedFriendList;
	}


	@ResponseBody
	@GetMapping("getOneFriendList")
	public List<FindFriend> getOneFriendList(@AuthenticationPrincipal UserDetails userDetails){
		List<FindFriend> matchedFriendList = friendService.getOneFriendList(userDetails.getUsername());
		log.debug("controller matchedFriendList {}",matchedFriendList );
		return matchedFriendList;
	}

	// 여기서부터 친구 일기 읽을 수 있는 부분


	private String nickname;

	@GetMapping("friendHomepage")
	public String friendHomepage(String nickname, Model model){
		this.nickname = nickname;
		System.out.println("nickname : " + nickname);
		FindFriend one = friendService.readByNickName(nickname);
		System.out.println("one : " + one);
		log.debug("friendList컨트롤러 : ", one);
		model.addAttribute("friend", one);
		return "friendView/friendHomePage";
	}

	@GetMapping("read")
	public String read(String dayString, Model model, @AuthenticationPrincipal UserDetails user){
		Diaries diary = friendService.read(dayString, nickname, user.getUsername());
		String date = diary.getCreated_at();
		model.addAttribute("diary", diary);
		model.addAttribute("created_at", date);

		return "friendView/friendReadForm";
	}

	@ResponseBody
	@GetMapping("checkDiary")
	public List<EmotionColor> checkDiary(){

		List<Diaries> diaries = friendService.checkDiary(nickname);
		List<EmotionColor> emotionColors = new ArrayList<>();

		for(Diaries diary : diaries){
			emotionColors.add(new EmotionColor(diary.getCreated_at(), diary.getEmotionscore()));
		}
		log.debug("emotionColors : {}", emotionColors);
		return emotionColors;
	}

	@ResponseBody
	@GetMapping("modalCheck")
	public int modalCheck(@RequestParam("dateForOne") String dateForOne){
		int n = friendService.modalCheck(dateForOne, nickname);
		return n;
	}

	@ResponseBody
	@GetMapping("lastReadDiary")
	public Diaries lastReadDiary(String dayString){
		log.debug("dayString read {} ", dayString);
		Diaries diaries = friendService.lastReadDiary(dayString, nickname);

		return diaries;
	}

	@ResponseBody
	@GetMapping("nextReadDiary")
	public Diaries nextReadDiary(String dayString){
		Diaries diaries = friendService.nextReadDiary(dayString, nickname);

		return diaries;
	}

	@ResponseBody
	@GetMapping("checkHeart")
	public Map<String, Object> checkHeart(String email, String diaryId, String dayString, @AuthenticationPrincipal UserDetails userDetails){
		log.debug("email : {}, diaryId : {} dayString : {}", email, diaryId, dayString);

		Map<String, Object> heartChecked = friendService.isHeartEmpty(email, diaryId, dayString, userDetails.getUsername());
		log.debug("hc : {}", heartChecked);
		return heartChecked;
	}

	@ResponseBody
	@GetMapping("updateHeart")
	public int updateHeart(String diaryId, String heartTo, @AuthenticationPrincipal UserDetails userDetails) {

		int cntHeart = friendService.updateHeart(diaryId, heartTo, userDetails.getUsername());
		return cntHeart;
	}

	@ResponseBody
	@PostMapping("insertReply")
	public int comment(String comment, int diaryId, @AuthenticationPrincipal UserDetails userDetails){
		int result = friendService.inputComment(comment, diaryId, userDetails.getUsername());
		return result;
	}

	@ResponseBody
	@GetMapping("getReply")
	public List<Reply> reply(String diaryId){
		List<Reply> replies = friendService.getReply(diaryId);
		return replies;
	}
}
