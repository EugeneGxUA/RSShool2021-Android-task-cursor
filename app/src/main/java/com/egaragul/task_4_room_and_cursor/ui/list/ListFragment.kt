package com.egaragul.task_4_room_and_cursor.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.egaragul.task_4_room_and_cursor.databinding.FragmentListBinding
import com.egaragul.task_4_room_and_cursor.ui.MainViewModel
import com.egaragul.task_4_room_and_cursor.ui.list.utils.SwipeToDeleteCallback

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null

    private val viewModel : MainViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val listAdapter by lazy {
        ListAdapter {
            findNavController().navigate(ListFragmentDirections.actionListFragmentToFieldsFragment(id = it))
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.rv.adapter = listAdapter
        ItemTouchHelper(SwipeToDeleteCallback {
            viewModel.delete(it)
        }).attachToRecyclerView(binding.rv)

        binding.fab.setOnClickListener {
            findNavController().navigate(ListFragmentDirections.actionListFragmentToFieldsFragment())
        }

        observe()

        viewModel.getList()
    }

    private fun observe() {
        viewModel.list.observe(viewLifecycleOwner) {
            listAdapter.submitList(it)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}