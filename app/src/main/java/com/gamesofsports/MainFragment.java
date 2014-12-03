package com.gamesofsports;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Other.FacebookUser;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphMultiResult;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.parse.ParseUser;
import com.parseFeatures.ParseFeatures;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by florian on 14. 11. 2.
 */
public class MainFragment extends android.support.v4.app.Fragment {
    private static final String TAG = "MainFragment";
    private UiLifecycleHelper uiHelper;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(getActivity(), callback);
        uiHelper.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
        authButton.setFragment(this);
        authButton.setReadPermissions(Arrays.asList("public_profile", "user_friends"));
        return view;
    }

    private Request createRequest(Session session) {
        Request request = Request.newGraphPathRequest(session, "/me", null);

        Set<String> fields = new HashSet<String>();
        String[] requiredFields = new String[] { "id"};
        fields.addAll(Arrays.asList(requiredFields));

        Bundle parameters = request.getParameters();
        parameters.putString("fields", TextUtils.join(",", fields));
        request.setParameters(parameters);

        return request;
    }

    private GraphUser getResults(Response response) {
        GraphUser result = response.getGraphObjectAs(GraphUser.class);
        return result;
    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception)
    {
        if (state.isOpened()) {
            Intent intent = new Intent(getActivity(), HomeActivity.class);
            startActivity(intent);
            Log.i(TAG, "Logged in...");
            Request request = createRequest(Session.getActiveSession());
            request.setCallback(new Request.Callback() {
                @Override
                public void onCompleted(Response response) {
                    if (response == null)
                        return;
                    ParseFeatures features = ParseFeatures.getInstance();
                    if (features.isInit() == false)
                        features.initializeParseFeatures(getActivity());
                    if (features.isUserInit() == true)
                        return;
                    GraphUser user = getResults(response);
                    String id = user.getProperty("id").toString();
                    if (features.isUserCreated(id) == false)
                    {
                        ParseUser newUser = features.createUser();
                        features.addInfosUser(newUser, id);
                        if (features.signUpUser(newUser) == false) {
                            System.out.println("Error signup");
                            return;
                        }
                        else {
                            System.out.println("Success");
                        }
                    }
                    if (features.loginUser(id) == false)
                        return;

                }
            });
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Request.executeAndWait(request);
        }
        else if (state.isClosed()) {
            Log.i(TAG, "Logged out...");
            ParseFeatures features = ParseFeatures.getInstance();
            if (features.isInit() == true)
            {
                if (features.isUserInit() == true)
                    features.freeUser();
            }
        }
    }

    private Session.StatusCallback callback = new Session.StatusCallback()
    {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    @Override
    public void onResume()
    {
        super.onResume();
        Session session = Session.getActiveSession();
        if (session != null &&
                (session.isOpened() || session.isClosed()) ) {
            onSessionStateChange(session, session.getState(), null);
        }
        uiHelper.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }
}
