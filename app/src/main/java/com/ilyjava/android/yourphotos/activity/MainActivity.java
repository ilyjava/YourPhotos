package com.ilyjava.android.yourphotos.activity;

/**
 * Created by Никита on 05.05.2018.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.ilyjava.android.yourphotos.R;
import com.ilyjava.android.yourphotos.adapter.GalleryAdapter;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKPhotoArray;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private String[] scope = new String[]{VKScope.MESSAGES, VKScope.FRIENDS, VKScope.WALL, VKScope.PHOTOS};
    private String TAG = MainActivity.class.getSimpleName();
    private ArrayList<String> smallPhotos = new ArrayList<>();
    private ArrayList<String> largePhotos = new ArrayList<>();
    private ArrayList<com.ilyjava.android.yourphotos.model.Image> images;
    private ProgressDialog pDialog;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;
    private Image image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VKSdk.login(this, scope);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        pDialog = new ProgressDialog(this);
        images = new ArrayList<>();
        mAdapter = new GalleryAdapter(getApplicationContext(), images);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getApplicationContext(), recyclerView, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", images);
                bundle.putInt("position", position);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                VKRequest request = new VKRequest("photos.getAll", VKParameters.from(), VKPhotoArray.class);
                request.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        System.out.println(response.responseString);
                        VKPhotoArray vkPhotoArray = (VKPhotoArray) response.parsedModel;
                        for (VKApiPhoto vkPhoto : vkPhotoArray){
                            if (vkPhoto.photo_604.length() < 1){
                                smallPhotos.add(vkPhoto.photo_130);
                            }else {
                                smallPhotos.add(vkPhoto.photo_604);
                            }
                            if (vkPhoto.photo_807.length() < 1) {
                                largePhotos.add(vkPhoto.photo_604);
                            } else {
                                largePhotos.add(vkPhoto.photo_807);
                            }
                        }
                        for (int i = 0; i < smallPhotos.size(); i++){
                            com.ilyjava.android.yourphotos.model.Image image = new com.ilyjava.android.yourphotos.model.Image();
                            image.setMedium(smallPhotos.get(i));
                            image.setLarge(largePhotos.get(i));
                            images.add(image);
                        }
                        mAdapter.notifyDataSetChanged();
                        for (int i = 0; i < largePhotos.size(); i++) {
                            System.out.println(largePhotos.get(i));
                        }
                    }

                });
            }

            @Override
            public void onError(VKError error) {
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    }
