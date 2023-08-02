package com.taro.shop

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.taro.shop.databinding.ActivityMovieBinding

import com.taro.shop.databinding.RowMovieBinding
import io.reactivex.Observable

import io.reactivex.android.schedulers.AndroidSchedulers

import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MovieActivity : AppCompatActivity() {
    private val TAG = MovieActivity::class.java.simpleName
    private lateinit var binding: ActivityMovieBinding
    private lateinit var movies: List<Movie>


    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.jsonserve.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)




 /*        val json = URL("https://api.jsonserve.com/ho12XS").readText()
        val movies = Gson().fromJson<List<Movie>>(json, Movie::class.java)
        movies.forEach { Log.d(TAG, "${it.Title} ${it.imdbRating}") }*/

/*        val movieService = retrofit.create(MovieService::class.java)
        val movies = movieService.listMovies().execute().body()

        movies?.forEach { Log.d(TAG, "${it.Title} ${it.imdbRating}") }*/

/*        var observable = io.reactivex.Observable.just("Hello World")

        var disposable: Disposable = observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { data -> Log.d(TAG, "onCreate: Pass\t$data")},
                { error -> Log.d(TAG, "onCreate: Failed\t$error") },
                { Log.d(TAG, "onCreate: Completed!") }
            )

//        disposable.dispose()
            */

        val movieService = retrofit.create(MovieService::class.java)


        movieService.listMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it->it.forEach { Log.d(TAG, "${it.Title}\t${it.imdbRating}\t${it.Images}") }
                movies = it
            },{
                Log.d(TAG, "onCreate: $it")
            },{
                binding.recycler.layoutManager = LinearLayoutManager(this)
                binding.recycler.setHasFixedSize(true)
                binding.recycler.adapter = MovieAdapter()
            })



    }

    inner class MovieAdapter() : RecyclerView.Adapter<MovieHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.row_movie,parent,false)
            return MovieHolder(view)
        }

        override fun getItemCount(): Int {
            return movies.size
        }

        override fun onBindViewHolder(holder: MovieHolder, position: Int) {
            var movie = movies[position]
            holder.bind(movie)

        }

    }

    inner class MovieHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = RowMovieBinding.bind(view)
        var titleText = binding.movieTitle
        var imdbText = binding.movieImdb
        var directorText = binding.movieDirector
        var posterImage = binding.imageView
        fun bind(movie: Movie) {
            titleText.text = movie.Title.toString()
            imdbText.text = movie.imdbRating.toString()
            directorText.text = movie.Director.toString()
            Glide.with(this@MovieActivity)
                .load(movie.Poster)
                .override(300)
                .into(posterImage)
        }
    }

}

data class Movie(
    val Actors: String,
    val Awards: String,
    val ComingSoon: Boolean,
    val Country: String,
    val Director: String,
    val Genre: String,
    val Images: List<String>,
    val Language: String,
    val Metascore: String,
    val Plot: String,
    val Poster: String,
    val Rated: String,
    val Released: String,
    val Response: String,
    val Runtime: String,
    val Title: String,
    val Type: String,
    val Writer: String,
    val Year: String,
    val imdbID: String,
    val imdbRating: String,
    val imdbVotes: String,
    val totalSeasons: String
)

interface MovieService {
    @GET("ho12XS")
    fun listMovies() : Observable<List<Movie>>
}
