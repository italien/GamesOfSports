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

/**
 * Created by debarg_a on 14/11/2014.
 */

public class ParseFeatures {

    private static ParseFeatures features = null;
    private boolean init = false;

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

    public boolean addInfosUser(ParseUser user, List<String> infosUser)
    {
        if (infosUser.size() != 3)
            return false;
        user.setUsername(infosUser.get(0));
        user.setPassword(infosUser.get(1));
        user.setEmail(infosUser.get(2));
        user.put("experience", 0);
        user.put("level", 0);
        return true;
    }

    public String getInfosUser(ParseUser user, String infos)
    {
        Object result =  user.get(infos);
        return (result.toString());
    }

    public ParseUser getUser()
    {
        return ParseUser.getCurrentUser();
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
}
