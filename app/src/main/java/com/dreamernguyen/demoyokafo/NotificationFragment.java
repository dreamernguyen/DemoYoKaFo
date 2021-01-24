package com.dreamernguyen.demoyokafo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.dreamernguyen.demoyokafo.uploadanh.CloudinaryConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;


public class NotificationFragment extends Fragment {
    Button btn;
    RecyclerView rv;
    PhotoAdapter photoAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        btn = view.findViewById(R.id.btnUpAnh);
        rv = view.findViewById(R.id.rvPhoto);
        photoAdapter = new PhotoAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(photoAdapter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        MediaManager.init(getContext());
        return view;
    }
    public void reloadData(){
        Log.e("vv", "reloadData: "+ "Notification");
        Toast.makeText(getContext(), "Notification", Toast.LENGTH_SHORT).show();
    }
    public void chooseImage(){
        TedBottomPicker.with(getActivity())
                .setPeekHeight(1600)
                .showTitle(false)
                .setCompleteButtonText("Xác nhận")
                .setEmptySelectionText("Không ảnh nào được chọn")
                .showMultiImage(new TedBottomSheetDialogFragment.OnMultiImageSelectedListener() {
            @Override
            public void onImagesSelected(List<Uri> uriList) {
                if(uriList != null && !uriList.isEmpty()){
                    photoAdapter.setData(uriList);
                    Log.d("ooo", "onImagesSelected: "+uriList.get(0));
                    String requestId = MediaManager.get().upload(uriList.get(0))
                            .unsigned("gybczcnv").callback(new UploadCallback() {
                                @Override
                                public void onStart(String requestId) {

                                }

                                @Override
                                public void onProgress(String requestId, long bytes, long totalBytes) {

                                }

                                @Override
                                public void onSuccess(String requestId, Map resultData) {
                                    Log.d("trave", "onSuccess: "+resultData.get("url"));
                                }

                                @Override
                                public void onError(String requestId, ErrorInfo error) {

                                }

                                @Override
                                public void onReschedule(String requestId, ErrorInfo error) {

                                }
                            })
                            .dispatch();

                }
            }
        });
    }

}