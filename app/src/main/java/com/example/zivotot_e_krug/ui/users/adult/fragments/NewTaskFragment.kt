package com.example.zivotot_e_krug.ui.users.adult.fragments


import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.zivotot_e_krug.R
import com.example.zivotot_e_krug.databinding.FragmentNewTaskBinding
import com.example.zivotot_e_krug.ui.users.adult.AdultViewModel
import com.example.zivotot_e_krug.ui.users.adult.maps.MapsActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*



class NewTaskFragment : Fragment() {

    private lateinit var binding: FragmentNewTaskBinding
    private lateinit var adultViewModel: AdultViewModel
    private lateinit var user: FirebaseUser
    private lateinit var mView: View
    private var auth: FirebaseAuth = Firebase.auth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewTaskBinding.inflate(inflater)
        auth.currentUser?.let {
            user = it
        }

        adultViewModel = ViewModelProvider(this)[AdultViewModel::class.java]
        val button = binding.createTask
        val title = binding.nameOfActivity.text
        val description = binding.descriptionOfActivity.text
        val datePicker = binding.datePicker
        val locationPicker = binding.locationPicker
        var repetitive: String = ""
        var urgency: String = ""
        adultViewModel.addLocationListener()
        adultViewModel._location.observe(viewLifecycleOwner, {
        locationPicker.text = it
        })

        datePicker.text = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())

        var cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.ROOT)
            datePicker.text = sdf.format(cal.time)
        }
        datePicker.setOnClickListener {
            DatePickerDialog(requireContext(), dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }



        button.setOnClickListener {
            //binding.loading.visibility = View.VISIBLE
            if (binding.chipGroup.checkedChipId.equals(R.id.repetitive)) {
                repetitive = "true"
            } else {
                repetitive = "false"
            }
            if (binding.urgencyChipGroup.checkedChipId.equals(R.id.urgent)) {
                urgency = "true"
            } else {
                urgency = "false"
            }
            if(adultViewModel.createTask(
                requireContext(),
                user,
                title.toString(),
                description.toString(),
                repetitive,
                urgency,
                datePicker.text.toString(),
                locationPicker.text.toString()
            )){
                fragmentManager.let{
                    it!!.beginTransaction().detach(this).attach(this).commit()
                }
            }
        }

        locationPicker.setOnClickListener{
            val intent = Intent(context, MapsActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }


}