package com.example.memenap

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    var currentImageUrl:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme();
    }
   private fun loadMeme() {
// Instantiate the RequestQueue.
       findViewById<ProgressBar>(R.id.progressbar).visibility=View.VISIBLE
       val url = "https://meme-api.com/gimme"

// Request a string response from the provided URL.
       val jsonObjectRequest = JsonObjectRequest(
           Request.Method.GET, url,null,
           { response ->
               currentImageUrl= response.getString("url");
              Glide.with(this).load(currentImageUrl).listener(object :RequestListener<Drawable>{
                  override fun onLoadFailed(
                      e: GlideException?,
                      model: Any?,
                      target: Target<Drawable>?,
                      isFirstResource: Boolean
                  ): Boolean {
                      findViewById<ProgressBar>(R.id.progressbar).visibility=View.GONE;
                      return false
                  }

                  override fun onResourceReady(
                      resource: Drawable?,
                      model: Any?,
                      target: Target<Drawable>?,
                      dataSource: DataSource?,
                      isFirstResource: Boolean
                  ): Boolean {
                      findViewById<ProgressBar>(R.id.progressbar).visibility=View.GONE
                      return false
                  }
              }).into(findViewById(R.id.meme_image))

           },
           {
               Toast.makeText(this,"internet nahi BSDK",Toast.LENGTH_LONG).show()
           })

// Add the request to the RequestQueue.
     Mysingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
   }

    fun shareOnClick(view: View) {
        val intent= Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"hey,checkout this cool meme from reddit$currentImageUrl")
        val chooser = Intent.createChooser(intent,"share this meme using...")
        startActivity(chooser);
    }
    fun nextOnClick(view: View) {
        loadMeme();
    }
}