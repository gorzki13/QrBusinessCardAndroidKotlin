package com.jg.QrBusinessCard.QrCode

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.Detector.Detections
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.jg.QrBusinessCard.DbHelper.DatabaseBuilder
import com.jg.QrBusinessCard.Models.BusinessCard
import com.jg.QrBusinessCard.R
import com.jg.QrBusinessCard.databinding.FragmentQrCodeBinding
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class QrCodeFragment : Fragment() {

    private val requestCodeCameraPermission = 1001
    private lateinit var cameraSource: CameraSource
    private lateinit var barcodeDetector: BarcodeDetector
    private var scannedValue = ""
    private lateinit var binding: FragmentQrCodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQrCodeBinding.inflate(inflater, container, false)
       binding.imageView.setOnClickListener({v->showSingleCard()})
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (ContextCompat.checkSelfPermission(
                requireContext(), android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            askForCameraPermission()
        } else {
            setupControls()
        }
        generateQR()

    }

    private fun setupControls() {
        barcodeDetector =
            BarcodeDetector.Builder(requireContext()).setBarcodeFormats(Barcode.ALL_FORMATS).build()

        cameraSource = CameraSource.Builder(requireContext(), barcodeDetector)
            .setRequestedPreviewSize(1920, 1080)
            .setAutoFocusEnabled(true)
            .build()

        binding.cameraSurfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            @SuppressLint("MissingPermission")
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    cameraSource.start(holder)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            @SuppressLint("MissingPermission")
            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
                try {
                    cameraSource.start(holder)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })

        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
                Toast.makeText(
                    requireContext(),
                    "Scanner has been closed",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun receiveDetections(detections: Detections<Barcode>) {
                val barcodes = detections.detectedItems
                if (barcodes.size() == 1) {
                    scannedValue = barcodes.valueAt(0).rawValue

                    requireActivity().runOnUiThread {
                        val valuesList=extractValuesToList(scannedValue)
                        cameraSource.stop()
                        Toast.makeText(
                            requireContext(),
                            "dodano wizytówkę:"+valuesList[0]+" "+valuesList[1],
                            Toast.LENGTH_SHORT
                        ).show()

                        GlobalScope.launch {
                            // Operacje w tle
                            DatabaseBuilder.getInstance(requireContext()).businessCardDao().insertBusinessCard(
                                BusinessCard(name=valuesList[0], surname = valuesList[1], number =Integer.parseInt(valuesList[3].replace(";","")) ,email=valuesList[2])
                            )
                        }




                    }
                } else {

                }
            }
        })
    }
    fun extractValuesToList(text: String): List<String> {
        return text.split("-")
    }
    private fun askForCameraPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(android.Manifest.permission.CAMERA),
            requestCodeCameraPermission
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestCodeCameraPermission && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupControls()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Permission Denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraSource.stop()
    }
    private fun generateQR() {
        val sharedPreferences = requireContext().getSharedPreferences("data", Context.MODE_PRIVATE)

        val savedName = sharedPreferences.getString("name", "")
        val savedSurname = sharedPreferences.getString("surname", "")
        val savedEmail = sharedPreferences.getString("email", "")
        val savedPhone = sharedPreferences.getInt("phone", 0)


        val text = "$savedName;$savedSurname;$savedEmail;$savedPhone"
        val writer = MultiFormatWriter()
        try {
            val matrix = writer.encode(text, BarcodeFormat.QR_CODE, 340, 340)
            val encoder = BarcodeEncoder()
            val bitmap = encoder.createBitmap(matrix)
            //set data image to imageview
           binding.ivQr.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }
    @SuppressLint("SuspiciousIndentation")
    private fun showSingleCard() {
        val dialog= Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_edit_data)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val exitButton=dialog.findViewById<Button>(R.id.noButton)

        exitButton.setOnClickListener {
            val sharedPreferences = requireContext().getSharedPreferences("data", Context.MODE_PRIVATE)
            var name = dialog.findViewById<EditText>(R.id.nameET)
            var surname = dialog.findViewById<EditText>(R.id.surNameET)
            var email = dialog.findViewById<EditText>(R.id.emailET)
            var phone = dialog.findViewById<EditText>(R.id.phoneET)
            val editor = sharedPreferences.edit()


            editor.putString("name",name.text.toString())
            editor.putString("surname",surname.text.toString())
            editor.putString("email",email.text.toString())
            editor.putInt("phone", Integer.parseInt(phone.text.toString()) )


// Zatwierdzenie zmian
            editor.apply()
            generateQR()
        dialog.dismiss()
        }

        dialog.show()

    }







}
