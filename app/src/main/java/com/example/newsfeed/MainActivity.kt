package com.example.newsfeed

import android.R.attr.data
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var  madapter:NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)

           fetchData()
         madapter= NewsListAdapter(this)
        recyclerView.adapter = madapter
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_app, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem ): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_business -> {
                val intent = Intent(this, Business::class.java).apply{}
                startActivity(intent)
                true
            }
            R.id.action_health -> {
                val intent = Intent(this, health::class.java).apply{}
                startActivity(intent)
                true
            }
            R.id.action_science -> {
                val intent = Intent(this, Science::class.java).apply{}
                startActivity(intent)
                true
            }
            R.id.action_technology -> {
                val intent = Intent(this, Technology::class.java).apply{}
                startActivity(intent)
                true
            }
            R.id.action_sports -> {
                val intent = Intent(this, Sports::class.java).apply{}
                startActivity(intent)
                true
            }
            R.id.action_entertainment -> {
                val intent = Intent(this, Entertainment::class.java).apply{}
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun fetchData(){

val url="https://saurav.tech/NewsAPI/top-headlines/category/general/in.json"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener {
             val newsJsonArray=it.getJSONArray("articles")
              val newsArray=ArrayList<News>()
              for (i in 0 until newsJsonArray.length()){
                  val newsJsonObject=newsJsonArray.getJSONObject(i)
                  val news=News(
                      newsJsonObject.getString("title"),
                      newsJsonObject.getString("author"),
                      newsJsonObject.getString("url"),
                      newsJsonObject.getString("urlToImage")
                  )

                  newsArray.add(news)
              }
                madapter.updateNews(newsArray)
            },
            Response.ErrorListener {

            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }


    override fun onItemClicked(items: News) {
     val builder=CustomTabsIntent.Builder()
        val customTabsIntent=builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(items.url))
    }


}


