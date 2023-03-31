package com.emiary.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emiary.dao.DiaryDAO;
import com.emiary.domain.Diaries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class DiaryServiceImpl implements DiaryService {
	@Autowired
	DiaryDAO diarydao;

	@Override
	public int write(Diaries diaries) {
		int result = diarydao.write(diaries);
		return result;
	}

	@Override
	public Diaries read(String dayString, String username) {
		Map<String, String> map = new HashMap<>();
		map.put("created_at", dayString);
		map.put("email", username);

		Diaries diary = diarydao.readDiary(map);
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


}
