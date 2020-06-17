package com.jp.app

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.jp.app.databinding.ActivityMainBinding
import java.util.concurrent.ConcurrentHashMap


/**
 * Lifecycle  LiveData ViewModel  ViewBinding DataBinding
 */
class MainActivity : AppCompatActivity()  {
    var TAG="MainActivity"
    var title="aa"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //var inflate=ActivityMainBinding.inflate(layoutInflater)
        var inflate:ActivityMainBinding=DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)

        inflate.title="哈哈"
        inflate.user= User("name","phone")
        //setContentView(inflate.root)




        //setContentView(R.layout.activity_main)
        lifecycle.addObserver(MyObserver())

        val mutableLiveData = MutableLiveData<String>()
        mutableLiveData.observe(this,
            Observer { s -> Log.d(TAG, "msg:----$s") })
        mutableLiveData.postValue("Android进阶三部曲")

        var model = ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
            .create(MyViewModel::class.java)
        model.name.observe(this, Observer { s->
            Log.d(TAG,"畅销书：----------$s")
        })




//        inflate.name.text=model.userName
//        model.updateName()
//        inflate.btn.setOnClickListener { v->
//            model.updateName()
//        }


        FirebaseInstanceId.getInstance()
            .instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.e("FCMDemo", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }
                // Get new Instance ID token
                val token = task.result!!.token
                Log.e("FCMDemo", "token: $token")
            })




        ActivityCompat.requestPermissions(this,
            arrayOf( Manifest.permission.READ_PHONE_STATE),0x101)





        var map=ConcurrentHashMap<String,String>()
        map.put("key","value")
        Log.e(TAG,map.get("key"))


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            Log.e(TAG,"没有权限")
        }else{

            var tm=this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            var deviceid= tm.deviceId  //获取id号/
            var tel=tm.line1Number   //手机号码
            var simName=tm.simOperatorName
            var imei=tm.simSerialNumber
            var imsi=tm.subscriberId
            var simState=tm.simState
            var version=tm.deviceSoftwareVersion
            var result="\ndeviceid：$deviceid\n tel：$tel\n imei：$imei\n imsi：$imsi\n simState：$simState\n version：${version.toString()}\n vnum：$simName"
            Log.e(TAG,result)
        }

    }

}
