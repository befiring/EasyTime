package com.befiring.easytime.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.befiring.easytime.R;
import com.befiring.easytime.Utils.FileUtil;
import com.befiring.easytime.adapter.FirstGridAdapter;
import com.befiring.easytime.bean.PictureResponse.Image;
import com.befiring.easytime.network.Network;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

//import cn.smssdk.EventHandler;
//import cn.smssdk.OnSendMessageHandler;
//import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2016/8/31.
 */
public class FirstFragment extends BaseFragment {

    @Bind(R.id.first_refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.first_recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.search_et)
    EditText search_et;
    @Bind(R.id.search_btn)
    Button search_btn;
    @Bind(R.id.download)
    Button download;

    private static FirstFragment instance;
    FirstGridAdapter adapter;

    Observer<List<Image>> observer = new Observer<List<Image>>() {
        @Override
        public void onCompleted() {
            int b=0;
        }

        @Override
        public void onError(Throwable e) {
               int a=0;
        }

        @Override
        public void onNext(List<Image> images) {
            refreshLayout.setRefreshing(false);
            adapter.setImages(images);
        }
    };

    Observer<ResponseBody> observer1=new Observer<ResponseBody>() {
        @Override
        public void onCompleted() {
             int a=0;
        }

        @Override
        public void onError(Throwable e) {
             int b=0;
        }

        @Override
        public void onNext(ResponseBody responseBody) {
            Toast.makeText(getActivity(),"下载完成",Toast.LENGTH_SHORT).show();
            FileUtil.writeResponseBodyToDisk(responseBody);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first, container, false);
        ButterKnife.bind(this, view);

        adapter=new FirstGridAdapter();
        refreshLayout.setEnabled(false);
        refreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(adapter);

        search("");
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               search(search_et.getText().toString());
            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unsubscribe();
                subscription=Network.getApiService("http://zhuangbi.idagou.com/i/")
                        .downloadFile()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(observer1);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public static FirstFragment getInstance() {
        if(instance==null){
            instance=new FirstFragment();
        }
        return instance;
    }

    public void search(String word){
        String key=word.equals("")||word==null?"装逼":search_et.getText().toString();
        unsubscribe();
        refreshLayout.setRefreshing(true);
        subscription= Network.getApiService("http://zhuangbi.info/")
                .searchPicture(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
