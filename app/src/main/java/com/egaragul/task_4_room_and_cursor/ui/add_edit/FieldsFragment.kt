package com.egaragul.task_4_room_and_cursor.ui.add_edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.egaragul.task_4_room_and_cursor.databinding.FragmentAddBinding
import com.egaragul.task_4_room_and_cursor.ui.MainViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FieldsFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()
    private val args : FieldsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (args.id != -1) {
            viewModel.getItemById(args.id)
            provideEditItem()
        } else {
            provideNewItem()
        }

        binding.etAge.doAfterTextChanged {
            validate()
        }
        binding.etBreed.doAfterTextChanged {
            validate()
        }
        binding.etName.doAfterTextChanged {
            validate()
        }
    }

    private fun provideEditItem() {
        binding.btnSave.text = "Edit"
        binding.btnSave.setOnClickListener {
            viewModel.update(
                args.id,
                binding.etName.text.toString(),
                binding.etAge.text.toString().toInt(),
                binding.etBreed.text.toString()
            )
            back()
        }

        viewModel.editedAnimal.observe(viewLifecycleOwner) {
            it?.let {
                binding.etName.setText(it.name)
                binding.etAge.setText(it.age.toString())
                binding.etBreed.setText(it.breed)
            }
        }
    }

    private fun provideNewItem() {
        binding.btnSave.setOnClickListener {
            viewModel.save(
                binding.etName.text.toString(),
                binding.etAge.text.toString().toInt(),
                binding.etBreed.text.toString()
            )
            back()
        }
    }

    private fun back() {
        findNavController().popBackStack()
    }

    private fun validate() {
        with(binding) {
            if (etName.text.isNullOrEmpty()) {
                isButtonEnabled(false)
                return
            }

            if (etAge.text.isNullOrEmpty()) {
                isButtonEnabled(false)

                return
            }

            if (etBreed.text.isNullOrEmpty()) {
                isButtonEnabled(false)
                return
            }

            isButtonEnabled(true)
        }
    }

    private fun isButtonEnabled(enabled : Boolean) {
        binding.btnSave.isEnabled = enabled
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}