package com.example.tat.features.home.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tat.databinding.FragmentHomeBinding
import com.example.tat.features.di.AdapterModule
import com.example.tat.features.home.presentation.ui.recyclerview.api.UserProjectUiAdapter
import com.example.tat.features.home.presentation.vm.HomeViewModel
import com.example.tat.utils.exceptions.UnauthorizedException
import com.example.tat.features.home.presentation.model.Event
import com.example.tat.features.home.presentation.model.UserProjectUi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject
    lateinit var customAdapterFactory: AdapterModule

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by activityViewModels()
    lateinit var userProjectUiAdapter: UserProjectUiAdapter

    private var recyclerViewProject: RecyclerView? = null

        private val projectUiDetailsListener: (
            userProjectUi: UserProjectUi, imageView: AppCompatImageView, textView: AppCompatTextView
    ) -> Unit = {
                userProjectUi, imageView, textView  ->
            val extras = FragmentNavigatorExtras(
                imageView to userProjectUi.projectId.toString(),
                textView to userProjectUi.projectName
            )
        val action = HomeFragmentDirections
            .actionHomeFragmentToProjectFragment(userProjectUi)
        findNavController().navigate(action, extras)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        observerFlow()
        observerEvent()

        binding.buttonGoToHistory.setOnClickListener {
            val action = HomeFragmentDirections
                .actionHomeFragmentToExecutionHistoryFragment()
            findNavController().navigate(action)
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            (activity as HomeActivity).performLogout()
        }
    }

    private fun setUpRecyclerView() {
        setUpRecyclerViewApi()
    }

    private fun setUpRecyclerViewApi() {
        userProjectUiAdapter = customAdapterFactory.createProjectUiAdapter(projectUiDetailsListener)
        recyclerViewProject = binding.recyclerViewProject
        recyclerViewProject?.apply {
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = userProjectUiAdapter
        }
    }

    private fun observerFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect { result ->
                    if (!binding.cardViewProject.isVisible) {
                        binding.spinner.root.isVisible = true
                    } else {
                        binding.spinner.root.isVisible = false
                    }
                    userProjectUiAdapter.submitList(result.userProjectsUi)
                    binding.cardViewProject.isVisible = userProjectUiAdapter.itemCount > 0

                    binding.cardViewUserInfo.isVisible = result.userUi != null
                    binding.name.text = result.userUi?.username
                    binding.fullName.text = result.userUi?.fullName
                    binding.role.text = result.userUi?.role


                    // TODO error display handling
                    if (result.error != null) {
                        when (result.error) {
                            is UnauthorizedException -> {
                                (activity as HomeActivity).performLogout()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun observerEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventsFlow.collect { result ->
                    when (result) {
                        is Event.ShowToast -> {
                            Toast.makeText(context, result.text, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        Log.d("LogCat","onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d("LogCat","onDestroy")
        super.onDestroy()
    }
}