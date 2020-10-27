package com.sosin.easyfree.navigation

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.otaliastudios.cameraview.CameraView
import com.sosin.easyfree.R
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.OutputStream


class CameraFragment: Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_camera, container, false)

        val camera: CameraView = view.findViewById(R.id.camera)
        camera.setLifecycleOwner(this)

        // 화면 1초마다 전송, 특정 뷰만 캡쳐
        var captureUtil = CaptureUtil()
        captureUtil.captureView(view)

        return view
    }

    fun sendScreen() {

    }
}

class CaptureUtil {
    // 캡쳐가 저장될 외부 저장소
    var CAPTURE_PATH = "/CAPTURE_TEST"

    fun captureView(view : View) {
        view.buildDrawingCache()
//            var captureView : Bitmap = View.getDrawingCache()
        var fos : FileOutputStream

        var strFolderPath = Environment.getExternalStorageDirectory().getAbsolutePath() + CAPTURE_PATH
        var folder : File = File(strFolderPath)
        if(!folder.exists()) {
            folder.mkdirs()
        }

        var strFilePath : String = strFolderPath + "/" + System.currentTimeMillis() + ".png"
        var fileCacheItem : File = File(strFilePath)

        try {
            fos = FileOutputStream(fileCacheItem)
//                captureView.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e : FileNotFoundException) {
            e.printStackTrace()
        }
    }
}