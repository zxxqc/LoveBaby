package com.hb.lovebaby.modle;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


/**
 * Created by mrsimple on 1/1/17.
 */

@Table(name = "comments")
public class Comment extends Model {
    @Column(name = "creator")
    public String creator ;
    @Column(name = "text")
    public String text ;
}
