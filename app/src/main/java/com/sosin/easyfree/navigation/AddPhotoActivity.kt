package com.sosin.easyfree.navigation

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Camera
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import com.sosin.easyfree.R
import kotlinx.android.synthetic.main.activity_add_photo.*
import java.io.IOException

class AddPhotoActivity : AppCompatActivity() {

    private val TAG = "MyTag"
    private val PERMISSIONS_REQUEST_CODE = 100
    private val REQUIRED_PERMISSIONS =
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    private var CAMERA_FACING = Camera.CameraInfo.CAMERA_FACING_BACK
    private var myCameraPreview: MyCameraPreview? = null

    var PICK_IMAGE_FROM_ALBUM = 0
    var storage : FirebaseStorage? = null
    var photoUri : Uri? = null
    var manager = getApplicationContext().getSystemService(CAMERA_SERVICE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_photo)
        //Initiate storage
        storage = FirebaseStorage.getInstance()
        // Open the album

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        var photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, PICK_IMAGE_FROM_ALBUM)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // grantResults[0] 거부 -> -1
        // grantResults[0] 허용 -> 0 (PackageManager.PERMISSION_GRANTED)
        Log.d(TAG, "requestCode : $requestCode, grantResults size : ${grantResults.size}")
        if(requestCode == PERMISSIONS_REQUEST_CODE) {
            var check_result = true
            for(result in grantResults) {
                if(result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false
                    break
                }
            }

            if(check_result) {
                startCamera()
            } else {
                Log.e(TAG, "권한 거부")
            }
        }

    }

    private fun startCamera() {
        Log.e(TAG, "startCamera")
        // Create our Preview view and set it as the content of our activity.
        myCameraPreview = MyCameraPreview(this, CAMERA_FACING)
        surfaceView.addView(myCameraPreview)

    }
}

class MyCameraPreview(context: Context, cameraId: Int) : SurfaceView(context), SurfaceHolder.Callback {
    private val TAG = "MyTag"
    private val mHolder: SurfaceHolder
    private val mCameraID: Int
    private var mCamera: Camera? = null
    private var mCameraInfo: Camera.CameraInfo? = null
    private val mDisplayOrientation: Int
    override fun surfaceCreated(holder: SurfaceHolder) {
        Log.d(TAG, "surfaceCreated")

        // retrieve camera's info.
        val cameraInfo = Camera.CameraInfo()
        Camera.getCameraInfo(mCameraID, cameraInfo)
        mCameraInfo = cameraInfo

        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            mCamera?.setPreviewDisplay(holder)
            mCamera?.startPreview()
        } catch (e: IOException) {
            Log.d(TAG, "Error setting camera preview: " + e.toString())
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        Log.d(TAG, "surfaceDestroyed")
        // empty. Take care of releasing the Camera preview in your activity.
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, w: Int, h: Int) {
        Log.d(TAG, "surfaceChanged")
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.
        if (mHolder.surface == null) {
            // preview surface does not exist
            Log.e(TAG, "preview surface does not exist")
            return
        }

        // stop preview before making changes
        try {
            mCamera?.stopPreview()
            Log.d(TAG, "Preview stopped.")
        } catch (e: Exception) {
            // ignore: tried to stop a non-existent preview
            Log.d(TAG, "Error starting camera preview: " + e.message)
        }


        // set preview size and make any resize, rotate or
        // reformatting changes here
        // start preview with new settings
        val orientation = calculatePreviewOrientation(mCameraInfo, mDisplayOrientation)
        mCamera?.setDisplayOrientation(orientation)
        try {
            mCamera?.setPreviewDisplay(mHolder)
            mCamera?.startPreview()
            Log.d(TAG, "Camera preview started.")
        } catch (e: Exception) {
            Log.d(TAG, "Error starting camera preview: " + e.message)
        }
    }

    /**
     * 안드로이드 디바이스 방향에 맞는 카메라 프리뷰를 화면에 보여주기 위해 계산합니다.
     */
    fun calculatePreviewOrientation(info: Camera.CameraInfo?, rotation: Int): Int {
        var degrees = 0
        when (rotation) {
            Surface.ROTATION_0 -> degrees = 0
            Surface.ROTATION_90 -> degrees = 90
            Surface.ROTATION_180 -> degrees = 180
            Surface.ROTATION_270 -> degrees = 270
        }
        var result: Int
        if (info?.facing === Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info!!.orientation + degrees) % 360
            result = (360 - result) % 360 // compensate the mirror
        } else {  // back-facing
            result = (info!!.orientation - degrees + 360) % 360
        }
        return result
    }

    init {
        Log.d(TAG, "MyCameraPreview cameraId : $cameraId")

        // 0    ->     CAMERA_FACING_BACK
        // 1    ->     CAMERA_FACING_FRONT
        mCameraID = cameraId
        try {
            // attempt to get a Camera instance
            mCamera = Camera.open(mCameraID)
        } catch (e: Exception) {
            // Camera is not available (in use or does not exist)
            Log.e(TAG, "Camera is not available")
        }


        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = holder
        mHolder.addCallback(this)

        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)

        // get display orientation
        mDisplayOrientation = (context as Activity).windowManager.defaultDisplay.rotation
    }
}