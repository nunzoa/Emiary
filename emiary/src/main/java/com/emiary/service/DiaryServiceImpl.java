package com.emiary.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emiary.dao.DiaryDAO;
import com.emiary.domain.Diaries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


}
