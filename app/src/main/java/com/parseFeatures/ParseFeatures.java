package com.parseFeatures;

import android.app.Activity;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by debarg_a on 14/11/2014.
 */

public class ParseFeatures {

    public ParseFeatures(Activity activity)
    {
        Parse.initialize(activity, "pBwfHRHqqI9aaauVc1jssgPi6R2fiyLY0kgUkO1g", "5aGubA4qBTBNHJf0ffsg6agnsqzd9qpRZvTN7MCn");
        Parse.enableLocalDatastore(activity);
    }

    public boolean saveObject(ParseObject object)
    {
        //Check
        object.saveEventually();
        return true;
    }

    public boolean deleteObject(ParseObject object)
    {
        object.deleteEventually();
        return true;
    }

    public boolean freeObject(ParseObject object)
    {
        object.unpinInBackground();
        return true;
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
