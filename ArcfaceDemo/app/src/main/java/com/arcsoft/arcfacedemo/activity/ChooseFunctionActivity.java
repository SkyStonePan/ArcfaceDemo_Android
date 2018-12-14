package com.arcsoft.arcfacedemo.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.arcsoft.arcfacedemo.R;
import com.arcsoft.arcfacedemo.common.Constants;
import com.arcsoft.arcfacedemo.util.ConfigUtil;
import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class ChooseFunctionActivity extends AppCompatActivity {

    private Toast toast = null;
    private static final int ACTION_REQUEST_PERMISSIONS = 0x001;
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_function);
        initView();

    }

    private void initView() {
        //设置视频模式下的人脸优先检测方向
        RadioGroup radioGroupFtOrient = findViewById(R.id.radio_group_ft_orient);
        RadioButton rbOrient0 = findViewById(R.id.rb_orient_0);
        RadioButton rbOrient90 = findViewById(R.id.rb_orient_90);
        RadioButton rbOrient180 = findViewById(R.id.rb_orient_180);
        RadioButton rbOrient270 = findViewById(R.id.rb_orient_270);
        RadioButton rbOrientAll = findViewById(R.id.rb_orient_all);
        switch (ConfigUtil.getFtOrient(this)) {
            case FaceEngine.ASF_OP_0_ONLY:
                rbOrient0.setChecked(true);
                break;
            case FaceEngine.ASF_OP_90_ONLY:
                rbOrient90.setChecked(true);
                break;
            case FaceEngine.ASF_OP_180_ONLY:
                rbOrient180.setChecked(true);
                break;
            case FaceEngine.ASF_OP_270_ONLY:
                rbOrient270.setChecked(true);
                break;
            case FaceEngine.ASF_OP_0_HIGHER_EXT:
                rbOrientAll.setChecked(true);
                break;
            default:
                rbOrient0.setChecked(true);
                break;
        }
        radioGroupFtOrient.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_orient_0:
                        ConfigUtil.setFtOrient(ChooseFunctionActivity.this, FaceEngine.ASF_OP_0_ONLY);
                        break;
                    case R.id.rb_orient_90:
                        ConfigUtil.setFtOrient(ChooseFunctionActivity.this, FaceEngine.ASF_OP_90_ONLY);
                        break;
                    case R.id.rb_orient_180:
                        ConfigUtil.setFtOrient(ChooseFunctionActivity.this, FaceEngine.ASF_OP_180_ONLY);
                        break;
                    case R.id.rb_orient_270:
                        ConfigUtil.setFtOrient(ChooseFunctionActivity.this, FaceEngine.ASF_OP_270_ONLY);
                        break;
                    case R.id.rb_orient_all:
                        ConfigUtil.setFtOrient(ChooseFunctionActivity.this, FaceEngine.ASF_OP_0_HIGHER_EXT);
                        break;
                    default:
                        ConfigUtil.setFtOrient(ChooseFunctionActivity.this, FaceEngine.ASF_OP_0_ONLY);
                        break;
                }
            }
        });
    }

    /**
     * 打开相机，显示年龄性别
     *
     * @param view
     */
    public void jumpToPreviewActivity(View view) {
        startActivity(new Intent(this, PreviewActivity.class));
    }

    /**
     * 处理单张图片，显示图片中所有人脸的信息，并且一一比对相似度
     *
     * @param view
     */
    public void jumpToSingleImageActivity(View view) {
        startActivity(new Intent(this, SingleImageActivity.class));
    }

    /**
     * 选择一张主照，显示主照中人脸的详细信息，然后选择图片和主照进行比对
     *
     * @param view
     */
    public void jumpToMultiImageActivity(View view) {
        startActivity(new Intent(this, MultiImageActivity.class));
    }

    /**
     * 打开相机，人脸注册，人脸识别
     *
     * @param view
     */
    public void jumpToFaceRecognizeActivity(View view) {
        startActivity(new Intent(this, RegisterAndRecognizeActivity.class));
    }

    /**
     * 批量注册和删除功能
     *
     * @param view
     */
    public void jumpToBatchRegisterActivity(View view) {
        startActivity(new Intent(this, FaceManageActivity.class));
    }

    /**
     * 激活引擎
     *
     * @param view
     */
    public void activeEngine(final View view) {
        if (!checkPermissions(NEEDED_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, NEEDED_PERMISSIONS, ACTION_REQUEST_PERMISSIONS);
            return;
        }
        if (view != null) {
            view.setClickable(false);
        }
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                FaceEngine faceEngine = new FaceEngine();
                int activeCode = faceEngine.active(ChooseFunctionActivity.this, Constants.APP_ID, Constants.SDK_KEY);
                emitter.onNext(activeCode);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer activeCode) {
                        if (activeCode == ErrorInfo.MOK) {
                            showToast(getString(R.string.active_success));
                        } else if (activeCode == ErrorInfo.MERR_ASF_ALREADY_ACTIVATED) {
                            showToast(getString(R.string.already_activated));
                        } else {
                            showToast(getString(R.string.active_failed, activeCode));
                        }

                        if (view != null) {
                            view.setClickable(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private boolean checkPermissions(String[] neededPermissions) {
        if (neededPermissions == null || neededPermissions.length == 0) {
            return true;
        }
        boolean allGranted = true;
        for (String neededPermission : neededPermissions) {
            allGranted &= ContextCompat.checkSelfPermission(this, neededPermission) == PackageManager.PERMISSION_GRANTED;
        }
        return allGranted;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ACTION_REQUEST_PERMISSIONS) {
            boolean isAllGranted = true;
            for (int grantResult : grantResults) {
                isAllGranted &= (grantResult == PackageManager.PERMISSION_GRANTED);
            }
            if (isAllGranted) {
                activeEngine(null);
            } else {
                showToast(getString(R.string.permission_denied));
            }
        }
    }

    private void showToast(String s) {
        if (toast == null) {
            toast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast.setText(s);
            toast.show();
        }
    }

}
