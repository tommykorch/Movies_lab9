package com.bignerdranch.android.movies
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.WorkerThread
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.movies.database.OmdbResponse
import com.bignerdranch.android.movies.network.MovieInterceptor
import com.bignerdranch.android.movies.network.MovieResponse
import com.bignerdranch.android.movies.network.RetrofitClient
import com.bignerdranch.android.movies.network.RetrofitInterface
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
private const val TAG = "SearchActivity"
private const val TITLE = "title_movie"
private const val YEAR = "year_movie"
class SearchActivity: AppCompatActivity() {
    private val movieApi: RetrofitInterface
    init {
        val client = OkHttpClient.Builder()
            .addInterceptor(MovieInterceptor())
            .build()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(RetrofitClient.OMDB_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
        movieApi = retrofit.create(RetrofitInterface::class.java)
    }
    fun searchContents(): LiveData<List<Movie>> {
        val responseLiveData: MutableLiveData<List<Movie>> = MutableLiveData()
        val searchRequest: Call<OmdbResponse> = movieApi.searchMovie()
        searchRequest.enqueue(object :
            Callback<OmdbResponse> {
            override fun onFailure(call: Call<OmdbResponse>, t: Throwable) {
                Log.e(TAG, "Failed to search movies", t)
            }
            override fun onResponse(
                call: Call<OmdbResponse>,
                response: Response<OmdbResponse>
            ) {
                Log.d(TAG, "Response received")
                val searchResponse:
                        OmdbResponse? = response.body()
                val movieResponse:
                        MovieResponse? = searchResponse?.movies
                var moviesItems:
                        List<Movie> = movieResponse?.movieItems
                    ?: mutableListOf()
                moviesItems =
                    moviesItems.filterNot {
                        it.poster.isBlank()
                    }
                responseLiveData.value = moviesItems
            }
        })
        return responseLiveData
    }

    private lateinit var movieListViewModel:
            MovieListViewModel
    private lateinit var searchResultsRecyclerView: RecyclerView
    private lateinit var adapter: SearchAdapter
    private val title = intent.getStringExtra(TITLE)
    private val year = intent.getStringExtra(YEAR)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movie)
        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, MovieFragment.newInstance())
                .commit()
        }
        /*searchResultsRecyclerView = findViewById(R.id.rcView)
        movieListViewModel = ViewModelProviders.of(this).get(MovieListViewModel::class.java)*/
        /*movieListViewModel.movieListLiveData.observe(
            this,
            Observer {
                movieItems -> searchResultsRecyclerView.adapter = SearchAdapter(movieItems)
            }
        )*/
    }
    companion object{
        fun newIntent(packageContext: Context, titleMovie: String, yearMovie: String): Intent {
            return Intent(packageContext, SearchActivity::class.java).apply {
                putExtra(TITLE, titleMovie)
                putExtra(YEAR, yearMovie)
            }
        }
    }
    interface RecyclerItemListener {
        fun onItemClick(v: View, position: Int)
    }
    @WorkerThread
    fun fetchPhoto(url: String): Bitmap? {
        val response: Response<ResponseBody> = movieApi.searchUrlBytes(url).execute()
        val bitmap = response.body()?.byteStream()?.use(BitmapFactory::decodeStream)
        Log.i(TAG,"Decoded bitmap=$bitmap from Response=$response")
        return bitmap
    }
}