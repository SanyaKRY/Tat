package com.example.tat.features.home.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tat.databinding.FragmentUpdateCatBinding
import com.example.tat.features.home.presentation.event.UpdateCatToDatabase
import com.example.tat.features.home.presentation.model.CatUi
import com.example.tat.features.home.presentation.vm.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateCatFragment : Fragment() {

//    private var _binding: FragmentUpdateCatBinding? = null
//    private val binding get() = _binding!!

//    private val viewModel: HomeViewModel by activityViewModels()

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentUpdateCatBinding.inflate(inflater, container, false)
//        val args = UpdateCatFragmentArgs.fromBundle(requireArguments())
//        binding.catAge.setText(args.catUi.age.toString())
//        binding.catName.setText(args.catUi.name)
//        binding.updateButton.setOnClickListener {
//            val catAge: Int = binding.catAge.text.toString().toInt()
//            val catName: String = binding.catName.text.toString()
//            val catUi = CatUi(args.catUi.id, catAge, catName)
//            viewModel.handleIntent(UpdateCatToDatabase(catUi = catUi))
//            val action = UpdateCatFragmentDirections.actionUpdateProjectFragmentToHomeFragment()
//            findNavController().navigate(action)
//        }

//        val view = binding.root
//        return view
//    }

    override fun onDestroyView() {
        super.onDestroyView()
//        _binding = null
    }
}
