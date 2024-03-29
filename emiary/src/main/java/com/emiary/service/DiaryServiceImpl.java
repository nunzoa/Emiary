package com.emiary.service;


import com.emiary.domain.Reply;
import com.emiary.domain.ReplyAlarm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emiary.dao.DiaryDAO;
import com.emiary.domain.Diaries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class DiaryServiceImpl implements DiaryService {
	@Autowired
	DiaryDAO diarydao;

	@Override
	public int write(Diaries diaries) throws IOException {

		log.debug("diaries {}", diaries);

		int result = diarydao.write(diaries);


		return result;
	}

	@Override
	public Diaries read(String dayString, String username) {
		Map<String, String> map = new HashMap<>();
		map.put("created_at", dayString);
		map.put("email", username);

		Diaries diary = diarydao.readDiary(map);

		//해당 페이지를 열어서 보게 되면 viewed로 바뀌게 됨
		int n = diarydao.readComment(diary.getDiary_id());

		return diary;
	}

	@Override
	public List<Diaries> checkDiary(String username) {

		return diarydao.checkDiary(username);
	}

    @Override
    public int modalCheck(String dateForOne, String username) {
		log.debug("dateForOne : {}, username : {}", dateForOne, username);
		Map<String, String> map = new HashMap<>();
		map.put("created_at", dateForOne);
		map.put("email", username);
		log.debug("서비스의 map의 값은? {}", map);

		int n = diarydao.modalCheck(map);

		return n;
    }

    @Override
    public int deleteDiary(String barDayString, String username) {
		Map<String, String> map = new HashMap<>();
		map.put("created_at", barDayString);
		map.put("email", username);
		int n = diarydao.deleteDiary(map);

		return n;
    }

	@Override
	public String lastDiary(String dayString, String username) {


		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		int resultDate = 1;
		String sendDate = "";
		int count = 0;

		while(resultDate != 0){
			Date date = null;
			try {
				date = sdf.parse(dayString);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
			cal.setTime(date);
			count++;
			cal.add(Calendar.DATE, -count);
			Date time = cal.getTime();
			sendDate = sdf.format(time);
			Map<String, String> map = new HashMap<>();
			map.put("dayString", sendDate);
			map.put("username", username);

			resultDate = diarydao.emptyDiaryCheck(map);
		}

		return sendDate;
	}

	@Override
    public String nextDiary(String dayString, String username) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		int resultDate = 1;
		String sendDate = "";
		int count = 0;

		while(resultDate != 0){
			Date date = null;
			try {
				date = sdf.parse(dayString);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
			cal.setTime(date);
			count++;
			cal.add(Calendar.DATE, count);
			Date time = cal.getTime();
			sendDate = sdf.format(time);
			Map<String, String> map = new HashMap<>();
			map.put("dayString", sendDate);
			map.put("username", username);

			resultDate = diarydao.emptyDiaryCheck(map);
		}

		return sendDate;
    }

	@Override
	public Diaries lastReadDiary(String dayString, String username) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		Diaries isDiaryEmpty = null;
		String sendDate = "";
		int count = 0;

		while(isDiaryEmpty == null){
			Date date = null;
			try {
				date = sdf.parse(dayString);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
			cal.setTime(date);
			count++;
			cal.add(Calendar.DATE, -count);
			Date time = cal.getTime();
			sendDate = sdf.format(time);
			Map<String, String> map = new HashMap<>();
			map.put("dayString", sendDate);
			map.put("username", username);

			isDiaryEmpty = diarydao.writtenDiaryCheck(map);
		}

		return isDiaryEmpty;
	}

	@Override
	public Diaries nextReadDiary(String dayString, String username) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		Diaries isDiaryEmpty = null;
		String sendDate = "";
		int count = 0;

		while(isDiaryEmpty == null){
			Date date = null;
			try {
				date = sdf.parse(dayString);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
			cal.setTime(date);
			count++;
			cal.add(Calendar.DATE, count);
			Date time = cal.getTime();
			sendDate = sdf.format(time);
			Map<String, String> map = new HashMap<>();
			map.put("dayString", sendDate);
			map.put("username", username);

			isDiaryEmpty = diarydao.writtenDiaryCheck(map);
		}

		return isDiaryEmpty;
	}

    @Override
    public List<Diaries> findingContent(String searchInput, String username) {
		Map<String, String> map = new HashMap<>();
		map.put("searchInput", searchInput);
		map.put("username", username);

		List<Diaries> content = diarydao.findingContent(map);


		return content;
    }

	@Override
	public int heartStatus(String email, String diaryId) {
		Map<String, String> map = new HashMap<>();
		map.put("email", email);
		map.put("diaryId", diaryId);

		int cntHeart = diarydao.heartStatus(map);

		return cntHeart;
	}

    @Override
    public int inputComment(String comment, String diaryId, String username) {
		Map<String, String> map = new HashMap<>();
		map.put("comment", comment);
		map.put("diaryId", diaryId);
		map.put("username", username);
		int result = diarydao.inputComment(map);
		return result;
    }

	@Override
	public List<Reply> getReply(String diaryId) {
		List<Reply> replies = diarydao.getReply(diaryId);

		for(Reply reply : replies){
			reply.setNickname(diarydao.getNickName(reply.getWriter_email()));
		}

		return replies;
	}

    @Override
    public List<ReplyAlarm> getReplyAlarm(String yearMonth, String username) {
		log.debug("y {} u {} ", yearMonth, username);
		Map<String, String> map = new HashMap<>();
		map.put("yearMonth", yearMonth);
		map.put("username", username);

		List<ReplyAlarm> list = diarydao.getReplyAlarm(map);
		log.debug("서비스 returnmap {}", list);
		return list;
    }

    @Override
    public int updateAIAddr(Diaries diaries) {
        return diarydao.updateAIaddr(diaries);
    }


}
