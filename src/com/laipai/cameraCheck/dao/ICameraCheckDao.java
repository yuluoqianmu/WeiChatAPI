package com.laipai.cameraCheck.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.laipai.base.dao.IBaseDao;

public interface ICameraCheckDao extends IBaseDao {
 public static final String ICAMERAMANDAO_NAME="com.laipai.cameraCheck.dao.imple.CameraCheckDaoImpl";

List querySqldata(String sql); 
}
