package com.gamesofsports;



import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphMultiResult;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.facebook.model.GraphUser;
import com.parseFeatures.ParseFeatures;

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

    private Request createRequest(Session session) {
        Request request = Request.newGraphPathRequest(session, "me/friends", null);

        Set<String> fields = new HashSet<String>();
        String[] requiredFields = new String[] { "id", "name", "picture", "email", "installed" };
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
        listFriends = (ListView) v.findViewById(R.id.leaderBoard);
        parse = ParseFeatures.getInstance();

        Request request = createRequest(Session.getActiveSession());
        request.setCallback(new Request.Callback() {
            @Override
            public void onCompleted(Response response) {
                if (response == null)
                    return;
                List<GraphUser> friends = getResults(response);
                System.out.println(friends.size());
                for (int i = 0; i < friends.size(); i++)
                {
                    GraphUser user = friends.get(i);
                    boolean installed = false;
                    if (user.getProperty("installed") != null)
                        installed = (Boolean) user.getProperty("installed");
                    if (installed == true)
                        System.out.println(user.toString());
                }
            }
        });
        request.executeAsync();
        return v;
    }
}
