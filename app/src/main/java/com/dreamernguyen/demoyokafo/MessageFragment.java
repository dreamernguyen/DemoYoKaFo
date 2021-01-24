package com.dreamernguyen.demoyokafo;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MessageFragment extends Fragment {
    private EditText edMessage,edSender,edReceiver;
    private Button btnSend;
    private ImageView btnUpload;
    private RecyclerView rvChat;
    private MessageAdapter messageAdapter;
    private List<TinNhan> listTinNhan = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        edMessage = view.findViewById(R.id.edMessage);
        edSender = view.findViewById(R.id.edSender);
        edReceiver = view.findViewById(R.id.edReceiver);
        btnSend = view.findViewById(R.id.btnSend);
        btnUpload = view.findViewById(R.id.btnUpload);
        rvChat = view.findViewById(R.id.rvChat);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvChat.setLayoutManager(linearLayoutManager);

        messageAdapter = new MessageAdapter(listTinNhan);
        rvChat.setAdapter(messageAdapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        edMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkKeyboard();
            }
        });



        return view;
    }

    private void sendMessage() {
//        login();
        String msg = edMessage.getText().toString().trim();
        String sender = edSender.getText().toString().trim();
        String receiver = edReceiver.getText().toString().trim();
        if (TextUtils.isEmpty(msg)){
            return;
        }
        Log.d("cc", "onClick: " +msg);

        TinNhan tinNhan = new TinNhan(sender,receiver,msg);

        Call<TinNhan> call = ApiService.apiService.postMessage(tinNhan);
        call.enqueue(new Callback<TinNhan>() {
            @Override
            public void onResponse(Call<TinNhan> call, Response<TinNhan> response) {
                TinNhan tinNhan1 = response.body();
                Log.d("abc", "onResponse: " + tinNhan1.getNoiDung());
                listTinNhan.add(tinNhan1);
                messageAdapter.notifyDataSetChanged();
                rvChat.scrollToPosition(listTinNhan.size()-1);
                edMessage.setText("");
            }

            @Override
            public void onFailure(Call<TinNhan> call, Throwable t) {
                Log.d("loi", "onFailure: "+t.getMessage());
            }
        });


    }
    private void checkKeyboard(){
        final View activityRootView = getActivity().findViewById(R.id.activityRoot);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                activityRootView.getWindowVisibleDisplayFrame(rect);

                int heightDiff = activityRootView.getRootView().getHeight() - rect.height();
                if ((heightDiff > 0.25 *activityRootView.getRootView().getHeight())){
                    if(listTinNhan.size() > 0){
                        rvChat.scrollToPosition(listTinNhan.size() - 1);
                        activityRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });
    }
    public void reloadData(){
        Log.e("vv", "reloadData: "+ "Chat");
//        Toast.makeText(getContext(), "Chat", Toast.LENGTH_SHORT).show();
    }
//    public void sendAnh(){
//        TedBottomPicker.with(getActivity())
//                .setPeekHeight(1600)
//                .showTitle(false)
//                .setCompleteButtonText("Xác nhận")
//                .setEmptySelectionText("Không ảnh nào được chọn")
//                .show(new TedBottomSheetDialogFragment.OnImageSelectedListener() {
//                    @Override
//                    public void onImageSelected(Uri uri) {
//                        listTinNhan.add(new TinNhan(null,uri));
//                        messageAdapter.notifyDataSetChanged();
//                        rvChat.scrollToPosition(listTinNhan.size()-1);
//
//                    }
//                });
//    }
//    public Socket getmSocket(){
//        return mSocket;
//    }
//    private Emitter.Listener onNewMessage = new Emitter.Listener() {
//        @Override
//        public void call(Object... args) {
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    JSONObject data = (JSONObject) args[0];
//                    String message;
//                    message = data.optString("data");
//                    listMessage.add(new Message(message,null));
//                    messageAdapter.notifyDataSetChanged();
//                    Log.d("chat", "run: "+message);
//                }
//            });
//        }
//    };
//
//    private void login(){
//        mSocket.emit("user_login",edSender.getText().toString());
//    }



}