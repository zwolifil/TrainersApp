package program.trainersapp.model

import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import android.os.AsyncTask



class Dto {

    fun get(table : String) : String {
        val path = Config.entryPoint + table
        val json = MyDownloadTask().execute(path).get()
        return json.toString()
    }

    fun getById(table : String, id : Int) : String{
        val path = Config.entryPoint + table + "/" + id.toString()
        val json = MyDownloadTask().execute(path).get()
        return json.toString()
    }

}

internal class MyDownloadTask : AsyncTask<String, Void, String>() {


    override fun onPreExecute() {
        //display progress dialog.
    }

    override fun doInBackground(vararg params: String): String {
        val response = StringBuffer()
        val obj = URL(params[0])
        val method = params[1]
        with(obj.openConnection() as HttpURLConnection) {
            requestMethod = method
            BufferedReader(InputStreamReader(inputStream)).use {
                var inputLine = it.readLine()
                while (inputLine != null) {
                    response.append(inputLine)
                    inputLine = it.readLine()
                }
            }
        }
        return response.toString()
    }


    protected override fun onPostExecute(result: String?) {
        // dismiss progress dialog and update ui
    }
}