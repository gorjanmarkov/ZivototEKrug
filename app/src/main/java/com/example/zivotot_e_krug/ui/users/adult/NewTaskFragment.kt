package com.example.zivotot_e_krug.ui.users.adult

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.zivotot_e_krug.R
import com.example.zivotot_e_krug.databinding.ActivityRegisterBinding
import com.example.zivotot_e_krug.databinding.FragmentNewTaskBinding
import com.example.zivotot_e_krug.ui.login.LoginViewModel
import com.example.zivotot_e_krug.ui.login.LoginViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS


class NewTaskFragment : Fragment() {

    private lateinit var binding: FragmentNewTaskBinding
    private lateinit var adultViewModel: AdultViewModel
    private lateinit var user: FirebaseUser
    private var auth: FirebaseAuth = Firebase.auth
    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewTaskBinding.inflate(inflater)
        auth.currentUser?.let {
            user = it
        }
        adultViewModel = activity?.let {
            ViewModelProvider(it).get(AdultViewModel::class.java)
        }!!
        val button = binding.createTask
        val title = binding.nameOfActivity.text
        val description = binding.descriptionOfActivity.text
        val datePicker = binding.datePicker
        val locationPicker = binding.locationPicker.text
        var repetitive: String = ""
        var urgency: String = ""

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
            Toast.makeText(requireContext(),"Text",Toast.LENGTH_SHORT).show()
            DatePickerDialog(requireContext(), dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }



        button.setOnClickListener {
            binding.loading.visibility = View.VISIBLE
            Toast.makeText(requireContext(),"Text",Toast.LENGTH_SHORT).show()
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
            adultViewModel.createTask(
                requireContext(),
                user,
                title.toString(),
                description.toString(),
                datePicker.text.toString(),
                locationPicker.toString(),
                repetitive,
                urgency
            )
        }

        return binding.root
    }


}