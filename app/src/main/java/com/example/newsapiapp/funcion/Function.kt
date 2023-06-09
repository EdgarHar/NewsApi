import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.newsapiapp.domain.Article

fun checkForInternet(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

fun localSearch(articles: List<Article>, query: String): List<Article> {
    return articles.filter{article -> article.title!!.contains(query)}
}






