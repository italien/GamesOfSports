package com.parseFeatures;

import android.app.Activity;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by debarg_a on 14/11/2014.
 */

public class ParseFeatures {

    private static ParseFeatures features = null;
    private boolean init = false;
    private ParseUser user = null;
    private boolean UserInit = false;

    public ParseFeatures()
    {

    }

    public static ParseFeatures getInstance()
    {
        if (features == null)
            features = new ParseFeatures();
        return features;
    }

    public void initializeParseFeatures(Activity activity)
    {
        if (init == true)
            return;

        Parse.initialize(activity, "pBwfHRHqqI9aaauVc1jssgPi6R2fiyLY0kgUkO1g", "5aGubA4qBTBNHJf0ffsg6agnsqzd9qpRZvTN7MCn");
        Parse.enableLocalDatastore(activity);
        init = true;
    }

    public boolean isInit()
    {
        return init;
    }

    public boolean isUserInit()
    {
        return this.UserInit;
    }

    public boolean signUpUser(ParseUser user)
    {
        try
        {
            user.signUp();
        }
        catch (ParseException e)
        {
            System.out.println("Error : " + e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    public ParseUser createUser()
    {
        return new ParseUser();
    }

    public boolean addInfosUser(ParseUser user, String id)
    {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(12);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        user.setUsername(id);
        user.setPassword(randomStringBuilder.toString());
        user.put("facebookId", id);
        user.put("experience", 0);
        user.put("level", 1);
        return true;
    }

    public boolean saveUser()
    {
        System.out.println(this.user);
        try {
            this.user.save();
        }
        catch (ParseException e) {
            System.out.println("Error -- : " + e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    public String getInfosUser(ParseUser user, String info)
    {
        Object result =  user.get(info);
        return (result.toString());
    }

    public  String getInfosUser(String id, String info)
    {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("facebookId", id);
        try {
            List<ParseUser> users = query.find();
            if (users.size() != 1)
            {
                System.out.println("Error occured");
                return null;
            }
            ParseUser user = users.get(0);
            Object result = user.get(info);
            return result.toString();
        }
        catch (ParseException e)
        {
            System.out.println("Error : " + e.getLocalizedMessage());
            return null;
        }
    }

    public boolean loginUser(String id)
    {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("facebookId", id);
        try {
            List<ParseUser> users = query.find();
            if (users.size() != 1)
            {
                System.out.println("Error occured");
                return false;
            }
            this.user = users.get(0);
            this.UserInit = true;
            ParseUser.logIn(this.user.getString("username"), "123456");
            System.out.println("User connected");
            return true;
        }
        catch (ParseException e)
        {
            System.out.println("Error : " + e.getLocalizedMessage());
            return false;
        }
    }

    public ParseUser getUser()
    {
        return this.user;
    }

    public boolean freeUser()
    {
        this.user.unpinInBackground();
        this.UserInit = false;

        ParseUser.logOut();

        System.out.println("User Free");
        return true;
    }

    public boolean saveObject(ParseObject object)
    {
        //Check
        try {
            object.save();
        }
        catch (ParseException e) {
            System.out.println("Error : " + e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    public void deleteObject(ParseObject object)
    {
        object.deleteEventually();
        return;
    }

    public void freeObject(ParseObject object)
    {
        object.unpinInBackground();
        return;
    }

    public boolean isUserCreated(String id)
    {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("facebookId", id);
        try {
            List<ParseUser> list = query.find();
            if (list.size() == 1)
                return true;
            return false;
        }
        catch (ParseException e) {
            System.out.println("Error : " + e.getLocalizedMessage());
            return true;
        }
    }

    public List<ParseObject> getObjects(String table, String condition, String value)
    {
        ParseQuery query = ParseQuery.getQuery(table);
        query.whereEqualTo(condition, value);

        try {
            List<ParseObject> list = query.find();
            return list;
        }
        catch (ParseException e)
        {
            System.out.println("Error : " + e.getLocalizedMessage());
            return null;
        }
    }

    public List<ParseObject> getObjects(String table, String condition, int value)
    {
        ParseQuery query = ParseQuery.getQuery(table);
        query.whereEqualTo(condition, value);

        try {
            List<ParseObject> list = query.find();
            return list;
        }
        catch (ParseException e)
        {
            System.out.println("Error : " + e.getLocalizedMessage());
            return null;
        }
    }

    public List<String> getObjectName(String table, String condition, int value)
    {
        List<String> result = new ArrayList<String>();

        ParseQuery query = ParseQuery.getQuery(table);
        query.whereEqualTo(condition, value);

        try {
            List<ParseObject> list = query.find();
            for (int i = 0; i < list.size(); i++)
            {
                ParseObject object = list.get(i);
                String tmp = (String) object.get("name");
                result.add(tmp);
            }
        }
        catch (ParseException e)
        {
            System.out.println("Error : " + e.getLocalizedMessage());
            return null;
        }
        return result;
    }

    public List<String> getObjectName(String table, String condition, String value)
    {
        List<String> result = new ArrayList<String>();

        ParseQuery query = ParseQuery.getQuery(table);
        query.whereEqualTo(condition, value);

        try {
            List<ParseObject> list = query.find();
            for (int i = 0; i < list.size(); i++)
            {
                ParseObject object = list.get(i);
                String tmp = (String) object.get("name");
                result.add(tmp);
            }
        }
        catch (ParseException e)
        {
            System.out.println("Error : " + e.getLocalizedMessage());
            return null;
        }
        return result;
    }

    public ParseObject getObjectWithId(String table, String id)
    {
        ParseQuery query = ParseQuery.getQuery(table);
        query.whereEqualTo("objectId", id);

        try {
            return(query.getFirst());
        }
        catch (ParseException e)
        {
            System.out.println("Error : " + e.getLocalizedMessage());
            return null;
        }
    }

    public ParseObject getObjectWithName(String table, String id)
    {
        ParseQuery query = ParseQuery.getQuery(table);
        query.whereEqualTo("name", id);

        try {
            return(query.getFirst());
        }
        catch (ParseException e)
        {
            System.out.println("Error : " + e.getLocalizedMessage());
            return null;
        }
    }

    public ParseObject getObject(String table, String col, int value)
    {
        ParseQuery query = ParseQuery.getQuery(table);
        query.whereEqualTo(col, value);

        try {
            return(query.getFirst());
        }
        catch (ParseException e)
        {
            System.out.println("Error : " + e.getLocalizedMessage());
            return null;
        }
    }

    public ParseObject getObject(String table, String col, String value)
    {
        ParseQuery query = ParseQuery.getQuery(table);
        query.whereEqualTo(col, value);

        try {
            return(query.getFirst());
        }
        catch (ParseException e)
        {
            System.out.println("Error : " + e.getLocalizedMessage());
            return null;
        }
    }

    public List<ParseObject> getAllObjects(String table)
    {
        ParseQuery query = ParseQuery.getQuery(table);

        try {
            List<ParseObject> results = query.find();
            return results;
        }
        catch (ParseException e)
        {
            System.out.println("Error : " + e.getLocalizedMessage());
            return null;
        }
    }

    public boolean addChallenge(String idSport, String nameChallenge, String content, int time, int difficulty, int success)
    {
        int points = 0;

        if (difficulty == 1)
            points = 10;
        else if (difficulty == 2)
            points = 20;
        else
            points = 30;
        ParseObject object = new ParseObject("Challenges");
        object.put("challengeName", nameChallenge);
        object.put("challengeContent", content);
        object.put("time", Integer.toString(time));
        object.put("successCondition", success);
        object.put("difficulty", difficulty);
        object.put("idSport", idSport);
        object.put("successPoints", points);
        if (this.saveObject(object) == false)
            return false;
        return true;
    }

    public void challengeSucceeded(String challengeId)
    {
        ParseQuery query = ParseQuery.getQuery("LinkUserChallenges");
        query.whereEqualTo("idChallenges", challengeId);
        query.whereEqualTo("idUser", this.user.get("facebookId").toString());

        try {
            List<ParseObject> objects = query.find();
            //if (objects.size() == 1)
            //    return;

            ParseObject object = new ParseObject("LinkUserChallenges");
            object.put("idChallenges", challengeId);
            object.put("idUser", this.user.getObjectId());
            this.saveObject(object);
        }
        catch (ParseException e) {
            System.out.println("Error : " + e.getLocalizedMessage());
            return;
        }
    }

    public void addUserPoints(int points)
    {
        this.saveUser();
        int exp = Integer.parseInt(this.user.get("experience").toString()) + points;
        int level = this.user.getInt("level");
        this.user.put("experience", exp);

        this.saveUser();
        ParseQuery query = ParseQuery.getQuery("Level");
        query.whereEqualTo("level", level + 1);
        try {
            ParseObject object = query.getFirst();
            if (Integer.parseInt(object.get("number").toString()) >= exp)
                this.user.increment("level");
        }
        catch (ParseException e) {
            System.out.println("Error : " + e.getLocalizedMessage());
            return;
        }
        this.saveUser();
        System.out.println("User add points");
    }

    public void checkAchievement()
    {
        ParseQuery query = ParseQuery.getQuery("Achievements");
        try {
            List<ParseObject> objects = query.find();
            for (int i = 0; i < objects.size(); i++)
            {
                ParseObject object = objects.get(i);

                ParseQuery check = ParseQuery.getQuery("LinkUserAchievements");
                check.whereEqualTo("idAchievement", object.getObjectId());
                check.whereEqualTo("idUser", this.user.getObjectId());
                List<ParseObject> results = check.find();
                if (results.size() > 0)
                    continue;

                if (object.getBoolean("successType") == true)
                {
                    check = ParseQuery.getQuery("LinkUserChallenges");
                    check.whereEqualTo("idUser", this.user.getObjectId());
                    List<ParseObject> checkResult = check.find();
                    if (checkResult.size() >= object.getInt("succesCondition"))
                    {
                        ParseObject achievement = new ParseObject("LinkUserAchievements");
                        achievement.put("idAchievement", object.getObjectId());
                        achievement.put("idUser", this.user.getObjectId());
                        this.saveObject(achievement);
                    }
                }
                else
                {
                    if (this.user.getInt("experience") >= object.getInt("succesCondition"))
                    {
                        ParseObject achievement = new ParseObject("LinkUserAchievements");
                        achievement.put("idAchievement", object.getObjectId());
                        achievement.put("idUser", this.user.getObjectId());
                        this.saveObject(achievement);
                    }
                }
            }
        }
        catch (ParseException e) {
            System.out.println("Error : " + e.getLocalizedMessage());
        }

    }

    public boolean checkAchievement(String achievementId)
    {
        ParseQuery query = ParseQuery.getQuery("LinkUserAchievements");
        query.whereEqualTo("idAchievement", achievementId);
        query.whereEqualTo("idUser", this.user.getObjectId());
        try {
            List<ParseObject> objects = query.find();
            if (objects.size() == 0)
                return false;
            return true;
        }
        catch (ParseException e) {
            System.out.println("Error : " + e.getLocalizedMessage());
            return false;
        }
    }
}
