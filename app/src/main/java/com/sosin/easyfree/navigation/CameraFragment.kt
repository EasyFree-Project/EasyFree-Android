package com.sosin.easyfree.navigation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.Camera
import android.media.Image
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import com.otaliastudios.cameraview.CameraView
import com.sosin.easyfree.R
import kotlinx.android.synthetic.main.fragment_camera.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream


class CameraFragment: Fragment() {
    var TAG = "CAMERA"
    val sdcard = Environment.getExternalStorageDirectory()
    var surfaceView : CameraSurfaceView? = null
    var imageView : ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_camera, container, false)
//        val camera: CameraView = view.findViewById(R.id.camera)
//        camera.setLifecycleOwner(this)
        surfaceView = view.findViewById<CameraSurfaceView>(R.id.surfaceview)
        imageView = view.findViewById<ImageView>(R.id.pictureimage)

        var file = File(sdcard, "capture.jpg")
        // 화면 1초마다 전송, 특정 뷰만 캡쳐
//        capturebutton?.setOnClickListener {
//            CameraSurfaceView.capture(surfaceView, imageView)
//            Toast.makeText(activity, "here" + CameraSurfaceView.capture(surfaceView, imageView).toString(), LENGTH_SHORT).show()
//
//            var captureUtil = this.CaptureUtil()
//            captureUtil.captureView()
//        }

        return view
    }


    fun sendScreen() {

    }
// in another java class
    inner class CaptureUtil(){
        var CAPTURE_PATH = "/CAPTURE_TEST"
        fun captureView() {
            val fos: FileOutputStream
            val strFolderPath = Environment.getExternalStoragePublicDirectory(AppCompatActivity.DOWNLOAD_SERVICE).absolutePath + CAPTURE_PATH

            val folder = File(strFolderPath)
            if (!folder.exists()) {
                folder.mkdirs()
            }

            val strFilePath = strFolderPath + "/" + System.currentTimeMillis() + ".png"
            val fileCacheItem = File(strFilePath)

            try {
                fos = FileOutputStream(fileCacheItem)
                surfaceView?.drawToBitmap()?.compress(Bitmap.CompressFormat.PNG, 100, fos)

            } catch (e: FileNotFoundException) {
                Log.d(TAG, e.toString())
            }
        }
    }

}