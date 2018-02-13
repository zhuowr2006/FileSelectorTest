package com.szjzsoft.filetest;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.szjzsoft.fileselector.FileSelectActivity;
import com.szjzsoft.fileselector.FileSelectConstant;
import com.szjzsoft.fileselector.utils.MPermissionUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.FileNotFoundException;
import java.util.function.Consumer;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    /**
     * 文件选择
     */
    private TextView mIdModeFile;
    /**
     * 文件多选
     */
    private TextView mIdModeFileMulti;
    /**
     * 文件夹选择
     */
    private TextView mIdModeFolder;
    /**
     * 文件夹多选
     */
    private TextView mIdModeFolderMulti;

    private static final Integer FILE_SELECTOR_REQUEST_CODE = 2016;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mIdModeFile = (TextView) findViewById(R.id.mode_file);
        mIdModeFile.setOnClickListener(this);
        mIdModeFileMulti = (TextView) findViewById(R.id.mode_file_multi);
        mIdModeFileMulti.setOnClickListener(this);
        mIdModeFolder = (TextView) findViewById(R.id.mode_folder);
        mIdModeFolder.setOnClickListener(this);
        mIdModeFolderMulti = (TextView) findViewById(R.id.mode_folder_multi);
        mIdModeFolderMulti.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        MPermissionUtils.requestPermissionsResult(this, 1, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}
                , new MPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        Intent intent = new Intent();
                        switch (v.getId()) {
                            default:
                                break;
                            case R.id.mode_file:
                                intent.setClass(getApplicationContext(), FileSelectActivity.class);
                                intent.putExtra(FileSelectConstant.SELECTOR_REQUEST_CODE_KEY, FileSelectConstant.SELECTOR_MODE_FILE);
                                startActivityForResult(intent, FILE_SELECTOR_REQUEST_CODE);
                                break;
                            case R.id.mode_file_multi:
                                intent.setClass(getApplicationContext(), FileSelectActivity.class);
                                intent.putExtra(FileSelectConstant.SELECTOR_REQUEST_CODE_KEY, FileSelectConstant.SELECTOR_MODE_FILE);
                                intent.putExtra(FileSelectConstant.SELECTOR_IS_MULTIPLE, true);
                                startActivityForResult(intent, FILE_SELECTOR_REQUEST_CODE);
                                break;
                            case R.id.mode_folder:
                                intent.setClass(getApplicationContext(), FileSelectActivity.class);
                                intent.putExtra(FileSelectConstant.SELECTOR_REQUEST_CODE_KEY, FileSelectConstant.SELECTOR_MODE_FOLDER);
                                startActivityForResult(intent, FILE_SELECTOR_REQUEST_CODE);
                                break;
                            case R.id.mode_folder_multi:
                                intent.setClass(getApplicationContext(), FileSelectActivity.class);
                                intent.putExtra(FileSelectConstant.SELECTOR_REQUEST_CODE_KEY, FileSelectConstant.SELECTOR_MODE_FOLDER);
                                intent.putExtra(FileSelectConstant.SELECTOR_IS_MULTIPLE, true);
                                startActivityForResult(intent, FILE_SELECTOR_REQUEST_CODE);
                                break;
                        }
                    }

                    @Override
                    public void onPermissionDenied() {
                        MPermissionUtils.showTipsDialog(MainActivity.this);
                    }
                });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent bundle) {
        super.onActivityResult(requestCode, resultCode, bundle);
        Log.i(TAG, "requestCode: " + requestCode + "; resultCode: " + resultCode);
        if (resultCode == RESULT_OK) {
            Toast.makeText(this, "paths: " + bundle.getStringArrayListExtra(FileSelectConstant.SELECTOR_BUNDLE_PATHS),
                    Toast.LENGTH_SHORT).show();
        }
    }

}
