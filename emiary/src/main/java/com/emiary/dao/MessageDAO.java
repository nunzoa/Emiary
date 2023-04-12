package com.emiary.dao;

import com.emiary.domain.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MessageDAO {
    List<Message> getMessageList(Map<String, String> map);

    int sendMessage(Map<String, String> map);
}
