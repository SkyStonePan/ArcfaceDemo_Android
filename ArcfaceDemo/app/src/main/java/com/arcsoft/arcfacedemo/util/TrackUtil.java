package com.arcsoft.arcfacedemo.util;

import android.graphics.Rect;
import com.arcsoft.face.FaceInfo;

import java.util.List;

public class TrackUtil {

    public static boolean isSameFace(float fSimilarity, Rect rect1, Rect rect2) {
        int left = Math.max(rect1.left, rect2.left);
        int top = Math.max(rect1.top, rect2.top);
        int right = Math.min(rect1.right, rect2.right);
        int bottom = Math.min(rect1.bottom, rect2.bottom);

        int innerArea = (right - left) * (bottom - top);

        return left < right
                && top < bottom
                && rect2.width() * rect2.height() * fSimilarity <= innerArea
                && rect1.width() * rect1.height() * fSimilarity <= innerArea;
    }

    public static void keepMaxFace(List<FaceInfo> ftFaceList) {
        if (ftFaceList == null || ftFaceList.size() <= 1) {
            return;
        }
        FaceInfo maxFaceInfo = ftFaceList.get(0);
        for (FaceInfo faceInfo : ftFaceList) {
            if (faceInfo.getRect().width() > maxFaceInfo.getRect().width()) {
                maxFaceInfo = faceInfo;
            }
        }
        ftFaceList.clear();
        ftFaceList.add(maxFaceInfo);
    }

}
