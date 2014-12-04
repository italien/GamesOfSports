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
        try {
            this.user.save();
        }
        catch (ParseException e) {
            System.out.println("Error : " + e.getLocalizedMessage());
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

        System.out.println("User Free");
        return true;
    }

    public boolean saveObject(ParseObject object)
    {
        //Check
        object.saveEventually();
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
            List<ParseObject> list = query.find();
            return(list.get(0));
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
            List<ParseObject> list = query.find();
            return(list.get(0));
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
            List<ParseObject> list = query.find();
            return(list.get(0));
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
            List<ParseObject> list = query.find();
            return(list.get(0));
        }
        catch (ParseException e)
        {
            System.out.println("Error : " + e.getLocalizedMessage());
            return null;
        }
    }

    public boolean addChallenge(String sport, String nameChallenge, String content, int time, int difficulty)
    {
        ParseObject object = new ParseObject("Challenges");
        //1object.put("")
        return true;
    }
}
