package com.example.taha.fypfinal;

/**
 * Created by taha on 4/14/2017.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
public class ZLifeStyleNews extends AsyncTask<Void,Void,Void>{
    String s1;
    ArrayList<FeedItem> feedItem;
    RecyclerView recyclerView;
    String address="http://www.sciencemag.org/rss/news_current.xml";
    Context context;
    ArrayList<String> list=new ArrayList<>();
    ArrayList<Document> arr;
    URL url;
    ProgressDialog progressDialog;
    public ZLifeStyleNews(Context context,RecyclerView recyclerView)
    {
        this.recyclerView=recyclerView;
        this.context=context;
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("loading...");
        list.add(address);
    }

    protected  void onPreExecute()
    {
        progressDialog.show();
        super.onPreExecute();
    }
    protected void onPostExecute(Void aVoid)
    {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        MyAdapter adapter=new MyAdapter(context,feedItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new VerticalSpace(50));
        recyclerView.setAdapter(adapter);
    }




    @Override
    protected Void doInBackground(Void... voids) {
        // ArrayList<Document> arr= (ArrayList<Document>) GetData();
        // for(int i=0;i<arr.size();i++)
        // {

        ProcessXml(GetData());

        //  }

        return null;
    }

    private void ProcessXml(Document data) {
        if(data!=null)
        {
            feedItem=new ArrayList<>();
            Element root=data.getDocumentElement();
            Node channel=root.getChildNodes().item(1);
            NodeList items=channel.getChildNodes();
            for(int i=0;i<items.getLength();i++)
            {
                Node currentchild=items.item(i);
                if(currentchild.getNodeName().equalsIgnoreCase("item"))
                {
                    FeedItem item=new FeedItem();
                    NodeList itemchilds=currentchild.getChildNodes();
                    for(int j=0;j<itemchilds.getLength();j++)
                    {
                        Node current=itemchilds.item(j);
                        Log.d("textcontent",current.getTextContent());
                        if(current.getNodeName().equalsIgnoreCase("title"))
                        {
                            item.setTitles(current.getTextContent());
                        }
                        if(current.getNodeName().equalsIgnoreCase("description"))
                        {

                            item.setdescription(current.getTextContent());
                        }
                        if(current.getNodeName().equalsIgnoreCase("pubDate"))
                        {
                            item.setpubDate(current.getTextContent());
                        }
                        if(current.getNodeName().equalsIgnoreCase("link"))
                        {
                            item.setlink(current.getTextContent());
                        }

                         if(current.getNodeName().equalsIgnoreCase("media:thumbnail"))
                         {
                             String url=current.getAttributes().item(0).getTextContent();
                             item.setthumbnailUrl(url);
                         }
                    }
                    feedItem.add(item);
                    Log.d("title",item.getTitles());
                    Log.d("title",item.getdescription());
                    Log.d("title",item.getpubDate());
                }
            }
        }



    }

    public Document GetData()
    {

        //  arr=new ArrayList<>();
        // for(int i=0;i<list.size();i++)
        // {

        try {
            url=new URL(address);
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream=connection.getInputStream();
            DocumentBuilderFactory builderFactory=DocumentBuilderFactory.newInstance().newInstance();
            DocumentBuilder builder=builderFactory.newDocumentBuilder();
            Document xmlDoc=builder.parse(inputStream);

            return xmlDoc;
            // return xmlDoc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //   }


        return null;

    }



}
