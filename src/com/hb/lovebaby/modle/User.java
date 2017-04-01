package com.hb.lovebaby.modle;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by mrsimple on 1/1/17.
 */

@Table(name = "users")
public class User extends Model {
    @Column(name = "uid")
    public String uid ;
    @Column(name = "name")
    public String name;
    @Column(name = "avatar")
    public String avatar ;
}
