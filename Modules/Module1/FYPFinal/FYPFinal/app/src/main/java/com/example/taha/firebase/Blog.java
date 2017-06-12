package com.example.taha.firebase;

/**
 * Created by taha on 4/23/2017.
 */

public class Blog {


String title,desc,image,username,location;


    public Blog()
    {

    }
    public Blog(String title,String desc,String image,String username,String location)
    {
        this.title=title;
        this.desc=desc;
        this.image=image;
        this.username=username;
        this.location=location;
    }

    void setUsername(String username)
    {
        this.username=username;
    }
    public String getUsername()
    {
        return username;
    }

    void setLocation(String location)
    {
        this.location=location;
    }
    public String getLocation()
    {
        return location;
    }


    public String getImage()
    {
        return image;
    }
    public void setImage(String image)
    {
        this.image=image;
    }
    public String getDesc()
    {
        return desc;
    }

     public void setDesc()
     {
         this.desc=desc;
     }

     public String getTitle()
     {
         return title;
     }
     public void setTitle(String title)

     {
         this.title=title;
     }

}
