package com.example.aibgetandpostlocationbonus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL="https://dojo-recipes.herokuapp.com/"
class MainActivity : AppCompatActivity() {

    lateinit var BTNADD: Button
    lateinit var BTNSHOW: Button
    lateinit var TV: TextView
    lateinit var EDTNAME:EditText
    lateinit var EDTLOCATION:EditText
    lateinit var EDTshowLOCATION:EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BTNADD=findViewById(R.id.btnadd)
        BTNSHOW=findViewById(R.id.btnshow)
        TV=findViewById(R.id.tv)
        EDTNAME=findViewById(R.id.edtname)
        EDTLOCATION=findViewById(R.id.edtlocation)
        EDTshowLOCATION=findViewById(R.id.edtgetlocation)

        BTNSHOW.setOnClickListener {

            var userinput=EDTshowLOCATION.text

            val retorfitBuilder= Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(APIInterface::class.java)

            val retrofitData =retorfitBuilder.getData()

            retrofitData.enqueue(object : Callback<List<MyDataItem>?>
            {
                override fun onResponse(
                    call: Call<List<MyDataItem>?>,
                    response: Response<List<MyDataItem>?>,
                ) {
                    val responseBody = response.body()!!


                    var stringToBePritined: String? = ""
                    for (myData in responseBody) {
                        if (myData.name== EDTshowLOCATION.text.toString()){
                            stringToBePritined =
                                stringToBePritined + "\n Location: " + myData.location

                    }

                    TV.text = stringToBePritined
                }
                }

                override fun onFailure(call: Call<List<MyDataItem>?>, t: Throwable) {

                }
            })
        }

        BTNADD.setOnClickListener {
            var f = MyDataItem(EDTNAME.text.toString(),EDTLOCATION.text.toString() )

            addSingleuser(f, onResult = {

                EDTNAME.setText("")
                EDTLOCATION.setText("")

                Toast.makeText(applicationContext, "Save Success!", Toast.LENGTH_SHORT).show();
            })
        }



    }

    private fun addSingleuser(f: MyDataItem, onResult: () -> Unit) {
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)



        if (apiInterface != null) {
            apiInterface.addUser(f).enqueue(object : Callback<MyDataItem> {
                override fun onResponse(
                    call: Call<MyDataItem>,
                    response: Response<MyDataItem>
                ) {

                    onResult()

                }

                override fun onFailure(call: Call<MyDataItem>, t: Throwable) {
                    onResult()
                    Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT).show();


                }
            })
        }

    }
}