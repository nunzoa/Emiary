package com.emiary.service;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emiary.dao.DiaryDAO;
import com.emiary.domain.Diaries;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//Json Parsing


@Slf4j
@Service
public class DiaryServiceImpl implements DiaryService {
	@Autowired
	DiaryDAO diarydao;

	@Override
	public int write(Diaries diaries) {
		//String results = getESValues(diaries.getKeyword());

		int result = diarydao.write(diaries);

		return result;
	}

	private String getValue(String keyword) throws IOException {
		HttpClient httpClient = HttpClients.createDefault();

		HttpPost httpPost = new HttpPost("http://43.201.101.83:8000/search/");

		httpPost.setHeader("Content-type", "application/json");
		//서버에 요청 할 때, 나의 컨텐츠 타입을 "application/json"
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("keyword", keyword);
		// 빈출 단어 =keyword
		StringEntity stringEntity = new StringEntity(jsonObject.toString(), "UTF-8");

		httpPost.setEntity(stringEntity);
		HttpResponse httpResponse = httpClient.execute(httpPost);
		httpResponse.setHeader("Content-type", "application/json");

		String responseBody = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");

		String output = StringEscapeUtils.unescapeJava(responseBody);
		// 가장 큰 JSONObject를 가져옵니다.
		JSONObject responseJson = new JSONObject(responseBody);
		// 첫번째 JSONObject를 가져와서 key-value를 읽습니다.
		JSONObject _index = responseJson.getJSONObject("_index");
		System.out.println(_index.toString());

		String _id =  _index.getString("_id");
		String _score = _index.getString("_score");
		System.out.println("_id(_index): " + _id);
		System.out.println("_score(_index): " + _score);

		// 두번째 JSONObject를 가져와서 key-value를 읽습니다.
		JSONObject _source = responseJson.getJSONObject("_source");
		System.out.println(_source.toString());
		System.out.println();

		String post_desc =_source.getString("post_desc");
		String author_id = _source.getString("author_id");
		String like_string = _source.getString("like_string");
		String post_tag = _source.getString("post_tag");

		System.out.println("post_desc(source)"+post_desc);
		System.out.println("author_id(source)"+author_id);
		System.out.println("like_string(source)"+like_string);
		System.out.println("post_tag(source)"+post_tag);
		// 배열을 가져옵니다.
		JSONArray JsonArray = responseJson.getJSONArray("img_list");
		// 배열의 모든 아이템을 출력합니다.
		for (int i = 0; i < JsonArray.length(); i++) {
			JSONObject Jsonimglist = JsonArray.getJSONObject(i);
			String img_url = Jsonimglist.getString("img_url");
			String img_alt = Jsonimglist.getString("img_alt");
			String img_name = Jsonimglist.getString("img_name");
			String img_tag = Jsonimglist.getString("img_tag");

			System.out.println("img_url(" + i + "): " + img_url);
			System.out.println("img_alt(" + i + "): " + img_alt);
			System.out.println("img_name(" + i + "): " + img_name);
			System.out.println("img_tag(" + i + "): " + img_tag);
			System.out.println();
		}
		return output;
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
