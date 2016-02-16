package com.laipai.cameraCheck.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.laipai.base.service.IBaseService;
import com.laipai.cameraCheck.dto.AppformData;
import com.laipai.cameraCheck.pojo.LpCameramanAppform;
import com.laipai.userManInfo.pojo.LpUser;

public interface ICameraCheckservice extends IBaseService {
 public static  final String ICAMERACHECKSERVICE_NAME="com.laipai.cameraCheck.service.imple.CameraCheckService";

List<LpCameramanAppform> queyallBypage(HttpServletRequest request);

List<LpCameramanAppform> queyallBypage(HttpServletRequest request,String checkStatus);

AppformData getAppfromDate();

LpCameramanAppform getAppformById(String appformId);

void saveAppformLog(LpCameramanAppform appform,LpUser user);

LpCameramanAppform getappformById(String appformId);

List<LpCameramanAppform> getappformHisByuserId(String userId);

void deleteAppformById(String appformId);

void saveAppform(LpCameramanAppform appform);
}
