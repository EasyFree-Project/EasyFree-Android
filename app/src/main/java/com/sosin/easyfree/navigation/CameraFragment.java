package com.sosin.easyfree.navigation;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sosin.easyfree.R;
import com.sosin.easyfree.navigation.model.ProductDTO;
import com.sosin.easyfree.navigation.user.App;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CameraFragment extends Fragment {
    public CameraSurfaceView surfaceView;
    public RequestQueue queue;
    public String TAG = "CAMERA";
    public RelativeLayout rl;
    public ImageView[] odbuttons = new ImageView[10];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }
    public int displayWidth, displayHeight;

    private void getDisplaySize() {
        Display display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        if(android.os.Build.VERSION.SDK_INT >= 13) {
            Point point = new Point();
            display.getSize(point);
            displayWidth = point.x;
            displayHeight = point.y;
        }
        else {
            displayWidth = display.getWidth();
            displayHeight = display.getHeight();
        }
//        Toast.makeText(getActivity(), displayWidth + " : " + displayHeight, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        surfaceView = (CameraSurfaceView) getView().findViewById(R.id.surfaceview);
        rl = (RelativeLayout) getView().findViewById(R.id.camera_relativelayout);
        getDisplaySize();
//        for (int i = 0; i < odbuttons.length; i++) {
//            odbuttons[i] = new Button(getContext());
//            odbuttons[i].setVisibility(View.GONE);
//        }

        getView().findViewById(R.id.capturebutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    for (ImageView odbutton : odbuttons) {
                        ViewGroup layout = (ViewGroup) odbutton.getParent();
                        if (null != layout) //for safety only  as you are doing onClick
                            layout.removeView(odbutton);
                    }
                } catch (Exception e) {
                }
                capture();
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    public void capture() {
        surfaceView.capture(new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                queue = Volley.newRequestQueue(getActivity());

                JSONObject params = new JSONObject();
                try {
                    params.put("photo", data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, getString(R.string.model_url), params, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray boxes = response.getJSONObject("data").getJSONArray("result");
                            for(int i = 0; i < odbuttons.length; i++){
                                if (i < boxes.length()){
                                    odbuttons[i] = new ImageView(getContext());
                                    String[] box_info = boxes.get(i).toString().split(" ");
                                    odbuttons[i].setImageDrawable(getResources().getDrawable(R.drawable.odbox));
                                    int x1 = Integer.parseInt(box_info[0]);
                                    int x2 = Integer.parseInt(box_info[1]);
                                    int y1 = Integer.parseInt(box_info[2]);
                                    int y2 = Integer.parseInt(box_info[3]);
                                    int box_width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (x2-x1)*displayWidth/512, getResources().getDisplayMetrics());
                                    int box_height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (y2-y1)*displayHeight/512, getResources().getDisplayMetrics());
                                    ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(box_width, box_height);
                                    odbuttons[i].setLayoutParams(lp);
                                    odbuttons[i].setX((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, x1*displayWidth/512, getResources().getDisplayMetrics()));
                                    odbuttons[i].setY((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, y1*displayWidth/512, getResources().getDisplayMetrics()));
                                    odbuttons[i].setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            getItemLists(box_info[4]);
                                        }
                                    });
                                    rl.addView(odbuttons[i]);
                                }
                                else{
//                                    odbuttons[i].setVisibility(View.GONE);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                jsonRequest.setTag(TAG);
                queue.add(jsonRequest);

//                Toast.makeText(getActivity(), ""+data.toString(), Toast.LENGTH_SHORT).show();
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inSampleSize = 8;
//                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//                imageView.setImageBitmap(bitmap);

                camera.startPreview();
            }
        });
    }


    public void getItemLists(String category_number) {
        App.Companion.setContext(getActivity());

        if(category_number != App.Companion.getCategory_number()) {
            App.Companion.getProductDTOs().clear();
            queue = Volley.newRequestQueue(getActivity());

            final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET,
                    getString(R.string.product_url) + category_number,
                    null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray items = response.getJSONObject("data").getJSONArray("item");
                        for (int i = 0; i < items.length(); i++) {
                            JSONObject item = items.getJSONObject(i);
                            ProductDTO productDTO = new ProductDTO(item.getString("product_number"),
                                    item.getString("product_name"),
                                    item.getString("product_content"),
                                    item.getString("producer_location"),
                                    item.getString("capacity_size"),
                                    item.getString("nutrient"),
                                    item.getInt("product_price"),
                                    item.getString("avg_review"),
                                    item.getInt("review_count"),
                                    item.getString("url"),
                                    item.getString("category_number"));
                            App.Companion.getProductDTOs().add(productDTO);
                        }
                        moveProductPage();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            jsonRequest.setTag(TAG);
            queue.add(jsonRequest);
        }
        else{
            moveProductPage();
        }
    }

    public void moveProductPage() {
        BottomNavigationView bottom_navigation = (BottomNavigationView) getActivity().findViewById(R.id.bottom_navigation);
        bottom_navigation.setSelectedItemId(R.id.action_list);
        getFragmentManager().beginTransaction().replace(R.id.main_content, new ProductViewFragment()).commit();
    }
}