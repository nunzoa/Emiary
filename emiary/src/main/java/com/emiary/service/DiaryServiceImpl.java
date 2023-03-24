package com.emiary.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emiary.dao.DiaryDAO;
import com.emiary.domain.Diaries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public int modalCheck(String dateForOne) {
		log.debug("서비스의 n의 값은? {}", dateForOne);
		log.debug("서비스의 n의 값은? {}", dateForOne);
		int n = diarydao.modalCheck(dateForOne);
		log.debug("서비스의 n의 값은? ", n);

		return n;
    }


}
