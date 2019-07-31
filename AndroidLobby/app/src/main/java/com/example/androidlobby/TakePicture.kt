package com.example.androidlobby

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Bundle
import android.util.Rational
import android.util.Size
import android.view.Surface
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraX
import androidx.camera.core.Preview
import androidx.camera.core.PreviewConfig
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream


class TakePicture : AppCompatActivity(), LifecycleOwner {

    private val REQUEST_CODE_PERMISSIONS = 10
    private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    private lateinit var viewFinder: TextureView
    private lateinit var submit: LottieAnimationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_picture)




        viewFinder = findViewById(R.id.view_finder)
        submit = findViewById(R.id.lottieAnimationViewofSubmit)


        callFire()

        submit.setOnClickListener {
            val intent = Intent(this, UplaodDocument::class.java)
            val stream = ByteArrayOutputStream()
            viewFinder.bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
            val image = stream.toByteArray()
            intent.putExtra("bitmap", image)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        if (allPermissionsGranted()) {
            viewFinder.post { startCamera() }
        } else {
            ActivityCompat.requestPermissions(
                    this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }


    }




    private fun startCamera() {

        val previewConfig = PreviewConfig.Builder().apply {
            setTargetAspectRatio(Rational(1, 1))
            setTargetResolution(Size(640, 640))
            setLensFacing(CameraX.LensFacing.FRONT)
        }.build()

        // Build the viewfinder use case
        val preview = Preview(previewConfig)

        // Every time the viewfinder is updated, recompute layout
        preview.setOnPreviewOutputUpdateListener {

            // To update the SurfaceTexture, we have to remove it and re-add it
            val parent = viewFinder.parent as ViewGroup
            parent.removeView(viewFinder)
            parent.addView(viewFinder, 0)

            viewFinder.surfaceTexture = it.surfaceTexture
            updateTransform()
        }
        CameraX.bindToLifecycle(this, preview)


    }

    private fun updateTransform() {

        val matrix = Matrix()


        val centerX = viewFinder.width / 2f
        val centerY = viewFinder.height / 2f


        val rotationDegrees = when (viewFinder.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }
        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)

        viewFinder.setTransform(matrix)

    }

    override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                viewFinder.post { startCamera() }
            } else {
                Toast.makeText(this,
                        "Permissions not granted by the user.",
                        Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
                baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }

    private fun hideSystemUI() {

        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }


//    class someTask() : AsyncTask<Void, Void, String>() {
//
//
//        override fun doInBackground(vararg params: Void?): String? {
//
//            val storage = FirebaseStorage.getInstance()
//
//            val storageRef = storage.reference
//            val path = storageRef.child(DetailofMe.phone + ".png");
//            if (path.equals(null))
//                return "False"
//            else
//                return "True"
//
//        }
//
//
//        override fun onPostExecute(result: String?) {
//            super.onPostExecute(result)
//            if (result.equals("False")) {
//
//            }
//
//        }
//    }

    private fun callFire() {
        val storage = FirebaseStorage.getInstance()

        val storageRef = storage.reference
        val path = storageRef.child(DetailofMe.phone)
        path.downloadUrl.addOnSuccessListener {
            val intent = Intent(this, UplaodDocument::class.java)
            intent.putExtra("Url", it.path.toString())
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }.addOnFailureListener {
            submit.visibility = View.VISIBLE
        }


    }
}
