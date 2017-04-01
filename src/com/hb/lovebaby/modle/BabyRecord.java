package com.hb.lovebaby.modle;

import java.util.ArrayList;
import java.util.List;

public class BabyRecord{
    public String recId;
    public String year;
    public String date;
    public String day;
    public String avatar;
    public String name;
    public String area;
    public List<String> photos = new ArrayList<String>() ;
    public List<User> likeUsers = new ArrayList<User>() ;
    public List<Comment> comments = new ArrayList<Comment>() ;
}
