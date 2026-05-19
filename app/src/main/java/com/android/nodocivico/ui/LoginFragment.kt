package com.android.nodocivico.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.nodocivico.R

class LoginFragment : Fragment(R.layout.fragment_login) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)
        val etUserName = view.findViewById<EditText>(R.id.etUserName)
        
        btnLogin.setOnClickListener {
            val name = etUserName.text.toString().trim()
            if (name.isNotEmpty()) {
                val bundle = Bundle().apply {
                    putString("userName", name)
                }
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment, bundle)
            } else {
                Toast.makeText(requireContext(), "Por favor ingrese su nombre", Toast.LENGTH_SHORT).show()
            }
        }
    }
}