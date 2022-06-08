package com.danghuong.musicapp.ui.frament.watchfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.danghuong.musicapp.R;
import com.danghuong.musicapp.common.Common;
import com.danghuong.musicapp.data.model.YoutubeItem;
import com.danghuong.musicapp.databinding.YoutubeFragmenRcBinding;
import com.danghuong.musicapp.ui.activity.PlayVideoActivity;
import com.danghuong.musicapp.ui.adapter.YoutubeAdapter;
import com.danghuong.musicapp.ui.base.BaseBindingFragment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WatchFragment extends BaseBindingFragment<YoutubeFragmenRcBinding, WatchViewModel> {
    private List<YoutubeItem> youtubeItemList = new LinkedList<>();
    private YoutubeAdapter youtubeAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.youtube_fragmen_rc;
    }

    @Override
    protected Class<WatchViewModel> getViewModel() {
        return WatchViewModel.class;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        getJsonYoutube(Common.URL);
    }

    private void getJsonYoutube(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int image = R.drawable.picture_image;
                int icon = R.drawable.ic_baseline_more_24;
                try {
                    JSONArray jsonItems = response.getJSONArray("items");
                    for (int i = 0; i < jsonItems.length(); i++) {
                        JSONObject jsonItem = jsonItems.getJSONObject(i);
                        JSONObject jsonSnippet = jsonItem.getJSONObject("snippet");
                        String title = jsonSnippet.getString("title");
                        JSONObject jsonThumbnaisl = jsonSnippet.getJSONObject("thumbnails");
                        JSONObject jsonMedium = jsonThumbnaisl.getJSONObject("medium");
                        String url = jsonMedium.getString("url");
                        JSONObject jsonResourceId = jsonSnippet.getJSONObject("resourceId");
                        String idVideo = jsonResourceId.getString("videoId");
                        youtubeItemList.add(new YoutubeItem(title, idVideo, url, image, icon));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                youtubeAdapter = new YoutubeAdapter();
                youtubeAdapter.setYoutubeItems(youtubeItemList);
                youtubeAdapter.setiYoutubeItem(position -> {
                    Intent intent = new Intent(getActivity(), PlayVideoActivity.class);
                    intent.putExtra(Common.VIDEO_ID, youtubeItemList.get(position).getVideoId());
                    startActivity(intent);
                });
                binding.progressbar.setVisibility(View.GONE);
                binding.rcYoutubeList.setAdapter(youtubeAdapter);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
                        binding.progressbar.setVisibility(View.VISIBLE);
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
}
