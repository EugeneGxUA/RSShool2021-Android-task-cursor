package com.egaragul.task_4_room_and_cursor.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.egaragul.task_4_room_and_cursor.databinding.FragmentSettingsBinding
import com.egaragul.task_4_room_and_cursor.ui.MainViewModel

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel : MainViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cbIsRoom.isChecked = viewModel.isRoom

        binding.cbIsRoom.setOnCheckedChangeListener { _, isChecked -> viewModel.setDbUsing(isChecked) }
    }
}