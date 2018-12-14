package com.arcsoft.arcfacedemo.util.face;

import android.support.annotation.Nullable;

import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.LivenessInfo;

import java.util.List;

public interface FaceListener {
    /**
     * 当出现异常时执行
     *
     * @param e 异常信息
     */
    void onFail(Exception e);


    /**
     * 请求人脸特征后的回调
     *
     * @param faceFeature    人脸特征数据
     * @param requestId 请求码
     */
    void  onFaceFeatureInfoGet(@Nullable FaceFeature faceFeature, Integer requestId);
}
