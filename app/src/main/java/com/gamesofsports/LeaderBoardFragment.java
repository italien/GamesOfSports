package com.gamesofsports;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.Other.FacebookUser;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphMultiResult;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.facebook.model.GraphUser;
import com.parseFeatures.ParseFeatures;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class LeaderBoardFragment extends Fragment {

    private ParseFeatures parse;
    private ListView listFriends;

    public LeaderBoardFragment() {
        // Required empty public constructor
    }

    private class ActionListAdapter extends ArrayAdapter<FacebookUser> {
        private List<FacebookUser> listElements;

        public ActionListAdapter(Context context, int resourceId,
                                 List<FacebookUser> listElements) {
            super(context, resourceId, listElements);
            this.listElements = listElements;
            // Set up as an observer for list item changes to
            // refresh the view.
            for (int i = 0; i < listElements.size(); i++) {
                listElements.get(i).setAdapter(this);
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater =
                        (LayoutInflater) getActivity()
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.facebook_user_list_item, null);
            }

            FacebookUser listElement = listElements.get(position);
            if (listElement != null) {
                ImageView picture = (ImageView) view.findViewById(R.id.pictureUser);
                TextView name = (TextView) view.findViewById(R.id.nameUser);
                TextView score = (TextView) view.findViewById(R.id.scoreUser);
                if (picture != null) {
                    picture.setImageDrawable(listElement.getPicture());
                }
                if (name != null) {
                    name.setText(listElement.getName());
                }
                if (score != null) {
                    score.setText(listElement.getScore());
                }
            }
            return view;
        }
    }

    private Request createRequest(Session session) {
        Request request = Request.newGraphPathRequest(session, "me/friends", null);

        Set<String> fields = new HashSet<String>();
        String[] requiredFields = new String[] { "id", "name", "picture", "installed" };
        fields.addAll(Arrays.asList(requiredFields));

        Bundle parameters = request.getParameters();
        parameters.putString("fields", TextUtils.join(",", fields));
        request.setParameters(parameters);

        return request;
    }

    private List<GraphUser> getResults(Response response) {
        GraphMultiResult multiResult = response.getGraphObjectAs(GraphMultiResult.class);
        GraphObjectList<GraphObject> data = multiResult.getData();
        return data.castToListOf(GraphUser.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_leaderboard, container, false);
        final List<FacebookUser> facebookUsers = new ArrayList<FacebookUser>();

        Request request = createRequest(Session.getActiveSession());
        request.setCallback(new Request.Callback() {
            @Override
            public void onCompleted(Response response) {
                if (response == null)
                    return;
                List<GraphUser> friends = getResults(response);
                for (int i = 0; i < friends.size(); i++)
                {
                    GraphUser user = friends.get(i);
                    boolean installed = false;
                    if (user.getProperty("installed") != null)
                        installed = (Boolean) user.getProperty("installed");
                    if (installed == true) {
                        URL imageSRC = null;
                        Drawable image;
                        try {
                            InputStream in = new URL("https://graph.facebook.com/" + user.getId() + "/picture").openStream();
                            image = Drawable.createFromStream(in, "facebook-pictures");
                        }
                        catch (MalformedURLException e) {
                            image = null;
                        }
                        catch (IOException e) {
                            image = null;
                        }
                        facebookUsers.add(new FacebookUser(image, user.getName(), "0", "0"));
                    }
                }
            }
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Request.executeAndWait(request);

        listFriends = (ListView) v.findViewById(R.id.leaderBoard);
        listFriends.setAdapter(new ActionListAdapter(getActivity(),
                R.id.leaderBoard, facebookUsers));
        return v;
    }
}
