package com.example.taha.firebase;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.taha.fypfinal.NewsDetail;
import com.example.taha.fypfinal.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import static android.R.attr.height;
import static android.R.attr.width;

public class AABCDFirebase1 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {


    private TrackGPS gps;
    double longitude;
    double latitude;
    String str="";
    DatabaseReference nDatabase;
    RecyclerView nBlogList;


    DatabaseReference mDatabaseLike;
    DatabaseReference mDatabasedisLike;
    DatabaseReference mDatabaseViews;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    DatabaseReference mDatabaseUsers;

    boolean nProcesslike=false;
    boolean nProcessdislike=false;
    boolean nProcessView=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aabcdfirebase1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(),
                        com.example.taha.firebase.ABCDFirebasePost2.class);
                myIntent.putExtra("address", str);
                startActivityForResult(myIntent, 0);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        nBlogList=(RecyclerView) findViewById(R.id.blog_list);
        nBlogList.setHasFixedSize(true);
        nBlogList.setLayoutManager(new LinearLayoutManager(this));

        nDatabase= FirebaseDatabase.getInstance().getReference().child("Blog");
        mDatabaseUsers=FirebaseDatabase.getInstance().getReference().child("Users");

        mDatabaseUsers.keepSynced(true);

        mDatabaseLike=FirebaseDatabase.getInstance().getReference().child("likes");
        mDatabasedisLike=FirebaseDatabase.getInstance().getReference().child("dislikes");
        mDatabaseViews=FirebaseDatabase.getInstance().getReference().child("Views");
        mDatabasedisLike.keepSynced(true);
        mDatabasedisLike.keepSynced(true);
        mDatabaseViews.keepSynced(true);
///////////////////////////////////////fIREBASE/////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////
        mAuth=FirebaseAuth.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null)
                {
                    Intent LogIntent=new Intent(AABCDFirebase1.this,Login.class);
                    LogIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(LogIntent);
                }
            }
        };

        ///////////////////////////////////////fIREBASE/////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////

//////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////GPS/////////////////////////////////////////////

        gps = new TrackGPS(AABCDFirebase1.this);


        if(gps.canGetLocation()) {


            longitude = gps.getLongitude();
            latitude = gps.getLatitude();

            Geocoder geocoder;
            List<Address> yourAddresses = new ArrayList<Address>();
            geocoder = new Geocoder(AABCDFirebase1.this, Locale.ENGLISH);
            try {
                yourAddresses = geocoder.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (yourAddresses.size() > 0) {
                String yourAddress = yourAddresses.get(0).getAddressLine(0);
                str=str+yourAddress+ " ";
                String yourCity = yourAddresses.get(0).getAddressLine(1);
                str=str+yourCity+ " ";
                String yourCountry = yourAddresses.get(0).getAddressLine(2);
                str=str+yourCountry+ " ";
                String state=yourAddresses.get(0).getAdminArea();
                str=str+state+ "";
                String knownName=yourAddresses.get(0).getFeatureName();
                str=str+knownName+ " ";
                String add=yourAddresses.get(0).getLocality();
                str=str+add+ " ";
                String ff=yourAddresses.get(0).getPremises();
                str=str+ff+ " ";
                String gg=yourAddresses.get(0).getSubAdminArea();
                str=str+gg+ " ";
                String yy=yourAddresses.get(0).getSubLocality();
                str=str+yy;
                Log.d("country", yourCountry);
                Log.d("country", yourAddress);
                Log.d("country", yourCity);



            }


        }



            Toast.makeText(getApplicationContext(),"Longitude:"+Double.toString(longitude)+"\nLatitude:"+Double.toString(latitude),Toast.LENGTH_SHORT).show();
        ////////////////////////////////////gps////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

    }///main



static String send="";

    ///////////////////////////////////////fIREBASE/////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onStart() {
        super.onStart();


        mAuth.addAuthStateListener(mAuthListener);

        FirebaseRecyclerAdapter<Blog,AABCDFirebase1.BlogViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Blog, AABCDFirebase1.BlogViewHolder>(

                Blog.class,
                R.layout.blog_view,
                BlogViewHolder.class,
                nDatabase

        ) {

            @Override
            protected void populateViewHolder(final BlogViewHolder viewHolder,final Blog model, int position) {

         String sendTitle="";
                String sendDesc="";
                String sendLoc="";
                final String post_key=getRef(position).getKey();
               /////////////////////////////////////////////////////////


                /////////////////////setviews/////////////////////////////

                mDatabaseViews.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                              viewHolder.setViews(String.valueOf(dataSnapshot.child(post_key).getChildrenCount()));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //////////////////////////setlike/////////////////////////////////////

                mDatabaseLike.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        viewHolder.setLike(String.valueOf(dataSnapshot.child(post_key).getChildrenCount()));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                //////////////////////////setdislike//////////////////////////////////
                mDatabasedisLike.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        viewHolder.setDislike(String.valueOf(dataSnapshot.child(post_key).getChildrenCount()));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                //////////////////////////////////////////////////////////


                /////////////////////////////////////////////////////////////////

                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                     Log.d("PathImage",model.getImage());
               viewHolder.setImage(model.getImage(),getApplicationContext());
                viewHolder.setUsername(model.getUsername());

                viewHolder.setLocation(model.getLocation());



                ///////////////////////////////////////////////////////////////////
                /////////////////////////Views/////////////////////////////////////


                viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override


                    public void onClick(View v) {
                        nProcessView=true;
//                        Context context=getApplicationContext();
//                        Intent intent=new Intent(context,NewsDetail.class);
//                        intent.putExtra("Link",viewHolder.g);
//                        context.startActivity(intent);

                        mDatabaseViews.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(nProcessView)
                                {
                                    if(!dataSnapshot.child(post_key).hasChild(mAuth.getCurrentUser().getUid()))
                                    {
                                        Toast.makeText(AABCDFirebase1.this, "Add View", Toast.LENGTH_SHORT).show();
                                        mDatabaseViews.child(post_key).child(mAuth.getCurrentUser().getUid()).setValue(mAuth.getCurrentUser().getEmail());
                                        viewHolder.setViews(String.valueOf(dataSnapshot.child(post_key).getChildrenCount()));


                                       Intent intent=new Intent(AABCDFirebase1.this,ABCDDescr.class);
                                        viewHolder.setforpage(model.getTitle(),model.getDesc(),model.getLocation());
                                       intent.putExtra("Link",send );
                                       startActivity(intent);

                                        nProcessView=false;
                                    }
                                    else
                                    {
                                        Intent intent=new Intent(AABCDFirebase1.this,ABCDDescr.class);
                                        viewHolder.setforpage(model.getTitle(),model.getDesc(),model.getLocation());
                                        intent.putExtra("Link",send );
                                        startActivity(intent);
                                    }
                                }



                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });



                /////////////////////////////////////////////////////////////////////
                ///////////////////////////views////////////////////////////////////




                ///////////////////////////////////////////////////
                ///////////////////like////////////////////////////

                viewHolder.like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        nProcesslike=true;


                   mDatabaseLike.addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot dataSnapshot) {
                         if(nProcesslike)
                         {
                             if(dataSnapshot.child(post_key).hasChild(mAuth.getCurrentUser().getUid()))
                             {
                                 Toast.makeText(AABCDFirebase1.this, "Remove Like", Toast.LENGTH_SHORT).show();
                                 mDatabaseLike.child(post_key).child(mAuth.getCurrentUser().getUid()).removeValue();

                                 viewHolder.setLike(String.valueOf(dataSnapshot.child(post_key).getChildrenCount()));
                                 nProcesslike=false;

                             }
                             else
                             {
                                 Toast.makeText(AABCDFirebase1.this, "Add like", Toast.LENGTH_SHORT).show();
                                 mDatabaseLike.child(post_key).child(mAuth.getCurrentUser().getUid()).setValue(mAuth.getCurrentUser().getEmail());

                                 Toast.makeText(AABCDFirebase1.this, "Remove DisLike", Toast.LENGTH_SHORT).show();
                                 mDatabasedisLike.child(post_key).child(mAuth.getCurrentUser().getUid()).removeValue();
                                 int L= (int) dataSnapshot.getChildrenCount();


                                 viewHolder.setLike(String.valueOf(dataSnapshot.child(post_key).getChildrenCount()));
                                 viewHolder.setDislike(String.valueOf(dataSnapshot.child(post_key).getChildrenCount()));

                                 Log.d("No of like", String.valueOf(L));

                                 nProcesslike=false;
                             }
                         }


                       }

                       @Override
                       public void onCancelled(DatabaseError databaseError) {

                       }
                   });

                    }
                });

                ///////////////////////////////////////////////////
                ///////////////////like////////////////////////////

                ///////////////////////////////////////////////////
                ///////////////////dislike////////////////////////////
                viewHolder.dislike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        nProcessdislike=true;
                        mDatabasedisLike.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(nProcessdislike)
                                {
                                    if(dataSnapshot.child(post_key).hasChild(mAuth.getCurrentUser().getUid()))
                                    {
                                        Toast.makeText(AABCDFirebase1.this, "Remove DisLike", Toast.LENGTH_SHORT).show();
                                        mDatabasedisLike.child(post_key).child(mAuth.getCurrentUser().getUid()).removeValue();

                                        viewHolder.setDislike(String.valueOf(dataSnapshot.child(post_key).getChildrenCount()));

                                        nProcessdislike=false;

                                    }
                                    else
                                    {
                                        Toast.makeText(AABCDFirebase1.this, "Add Dislike", Toast.LENGTH_SHORT).show();
                                        mDatabasedisLike.child(post_key).child(mAuth.getCurrentUser().getUid()).setValue(mAuth.getCurrentUser().getEmail());

                                        Toast.makeText(AABCDFirebase1.this, "Remove Like", Toast.LENGTH_SHORT).show();
                                        mDatabaseLike.child(post_key).child(mAuth.getCurrentUser().getUid()).removeValue();
                                        int L= (int) dataSnapshot.getChildrenCount();

                                        viewHolder.setDislike(String.valueOf(dataSnapshot.child(post_key).getChildrenCount()));
                                        viewHolder.setLike(String.valueOf(dataSnapshot.child(post_key).getChildrenCount()));

                                        Log.d("No of dislike", String.valueOf(L));
                                        nProcessdislike=false;
                                    }
                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }//onclick
                });
///////////////////////////////////////////////////
                ///////////////////dislike////////////////////////////

//                public void setFilter()
//                {
//
//                    notifyDataSetChanged();
//                }





            }///viewholder
        };



        nBlogList.setAdapter(firebaseRecyclerAdapter);
    }




    ///////////////////////////////////////fIREBASE/////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////
    public   static class BlogViewHolder extends  RecyclerView.ViewHolder
    {
        View mView;

        ImageButton like,dislike;
        public CardView cardView;

        public BlogViewHolder(View itemView) {
            super(itemView);

            mView=itemView;
            like=(ImageButton) mView.findViewById(R.id.like);
            dislike=(ImageButton) mView.findViewById(R.id.dislike);
            cardView = (CardView)  itemView.findViewById(R.id.cv1);

        }





        public void setTitle(String title)
        {
            TextView post_title=(TextView)mView.findViewById(R.id.post_title);
            post_title.setText(title);
        }

        public void setDesc(String Desc)
        {
            TextView post_Desc=(TextView) mView.findViewById(R.id.post_Text);
            post_Desc.setText(Desc);
        }
        public void setUsername(String username)
    {
        TextView Username=(TextView) mView.findViewById(R.id.username);
        Username.setText(username);
    }

        public void setLocation(String location)
        {

            TextView Location=(TextView) mView.findViewById(R.id.loc);
            Location.setText(location);
        }

        public void setImage(String image,Context context)
        {

            ImageView img=(ImageView) mView.findViewById(R.id.post_image);
            Picasso.with(context).load(image).into(img);
        }
        public void setLike(String like)
        {
            if(like!="")
            {
                TextView Like=(TextView) mView.findViewById(R.id.totalLike);
                Like.setText(like);
            }

        }

        public void setDislike(String dislike)
        {
            if(dislike!="")
            {
                TextView DisLike=(TextView) mView.findViewById(R.id.totalDislike);
                DisLike.setText(dislike);
            }
        }
        public void setViews(String views)
        {
            if(views!="")
            {
                TextView Views=(TextView) mView.findViewById(R.id.views);
                Views.setText("views:"+views);
            }
        }


        public void setforpage(String a,String b,String c)
        {
           send="";
            send=a+",,"+b+",,"+c;

        }

    }




































    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.aabcdfirebase1, menu);

        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(item.getItemId()==R.id.action_logout)
        {
            logout();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {

        mAuth.signOut();

    }
}
