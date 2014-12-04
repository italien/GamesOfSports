package com.gamesofsports;



import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parseFeatures.ParseFeatures;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class StartFragment extends Fragment {

    private ProfilePictureView profilePictureView;
    private TextView userNameView;
    private TextView userScore;
    private TextView userLevel;
    private ProgressBar scoreProgress;
    private Handler mHandler = new Handler();

    private static final int REAUTH_ACTIVITY_CODE = 100;

    public StartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(getActivity(), callback);
        uiHelper.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_start, container, false);

        profilePictureView = (ProfilePictureView) view.findViewById(R.id.selection_profile_pic);
        profilePictureView.setCropped(true);

        userNameView = (TextView) view.findViewById(R.id.selection_user_name);
        userScore = (TextView) view.findViewById(R.id.user_score);
        userLevel = (TextView) view.findViewById(R.id.user_level);
        scoreProgress = (ProgressBar) view.findViewById(R.id.user_experience);
        Session session = Session.getActiveSession();
        if (session != null && session.isOpened()) {
            // Get the user's data
            makeMeRequest(session);
        }
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REAUTH_ACTIVITY_CODE) {
            uiHelper.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void makeMeRequest(final Session session) {
        // Make an API call to get user data and define a
        // new callback to handle the response.
        Request request = Request.newMeRequest(session,
                new Request.GraphUserCallback() {
                    @Override
                    public void onCompleted(GraphUser user, Response response) {
                        // If the response is successful
                        if (session == Session.getActiveSession()) {
                            if (user != null) {
                                // Set the id for the ProfilePictureView
                                // view that in turn displays the profile picture.
                                profilePictureView.setProfileId(user.getId());
                                // Set the Textview's text to the user's name.
                                userNameView.setText(user.getName());
                                ParseFeatures features = ParseFeatures.getInstance();
                                if (features.isInit() == false)
                                    features.initializeParseFeatures(getActivity());
                                if (features.isUserInit() == false)
                                    return;
                                ParseUser userP = features.getUser();
                                int score = Integer.parseInt(features.getInfosUser(userP, "experience"));
                                int level = Integer.parseInt(features.getInfosUser(userP, "level"));
                                userLevel.setText("Score : " + features.getInfosUser(userP, "experience"));
                                userScore.setText("Level : " + features.getInfosUser(userP, "level"));
                                ParseObject info1 = features.getObject("Level", "level", level - 1);
                                ParseObject info2 = features.getObject("Level", "level", level);
                                int number1 = info1.getInt("number");
                                int number2 = info2.getInt("number");
                                int score2 = score - number1;
                                float percentage = ((float) score2 / ((float) number2 - (float) number1)) * (float) 100;
                                final int percent = (int) percentage;
                                scoreProgress.setProgress(percent);

                            }
                        }
                        if (response.getError() != null) {
                            // Handle errors, will do so later.
                        }
                    }
                });
        request.executeAsync();
    }

    private void onSessionStateChange(final Session session, SessionState state, Exception exception) {
        if (session != null && session.isOpened()) {
            // Get the user's data.
            makeMeRequest(session);
        }
    }

    private UiLifecycleHelper uiHelper;
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(final Session session, final SessionState state, final Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        uiHelper.onSaveInstanceState(bundle);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    public void onBackPressed() {
        //handle back press event
    }
}
