package edu.uwp.kusd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import edu.uwp.kusd.network.VolleySingleton;


/**
 * Created by Cabz on 10/11/2016.
 */

public class LunchActivity_Tab_Two extends Fragment {

    RequestQueue queue = VolleySingleton.getsInstance().getRequestQueue();
    List<LunchObj> schoolLunches;
    private LunchObj lunchObj;
    private String text;
    private String url = "http://www.kusd.edu/xml-menus";
    ArrayList<LunchObj> items = null;
    ArrayList<LunchObj> selectedItems = new ArrayList<LunchObj>();


    private RecyclerView recyclerview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_lunch_tab_two_fragment, container, false);

        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(layoutManager);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                String temp = response;


               // Log.d("response", temp);

                InputStream stream = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));
                LunchParserHandler parserHandler = new LunchParserHandler();
                schoolLunches = parserHandler.parse(stream);


                items = (ArrayList<LunchObj>) schoolLunches;

                for (int i = 0; i < items.size(); i++) {
                    LunchObj tempObj = new LunchObj();
                    if (items.get(i).getCategory().equals("Middle School Menus")) {
                        System.out.println(items.get(i).getCategory());
                        tempObj.setCategory(items.get(i).getCategory());
                        tempObj.setTitle(items.get(i).getTitle());
                        tempObj.setFileUrl(items.get(i).getfileURL());
                        tempObj.cloneLunch(items.get(i));
                        selectedItems.add(tempObj);

                    }
                }

                RVAdapter adapter = new RVAdapter(getContext(), selectedItems);
                recyclerview.setAdapter(adapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(stringRequest);

        return view;
    }


}
