package com.example.tat.features.executionhistory.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import com.example.tat.databinding.FragmentExecutionHistoryBinding
import com.example.tat.features.executionhistory.presentation.ui.recyclerview.UserRunUiAdapter
import com.example.tat.features.executionhistory.presentation.vm.ExecutionHistoryViewModel
import com.example.tat.features.executionhistory.workmanager.Poller
import com.example.tat.features.executionhistory.workmanager.Poller.Companion.UNIQUE_WORK_NAME
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import com.example.tat.utils.exceptions.PROGRESS

@AndroidEntryPoint
class ExecutionHistoryFragment : Fragment() {

    private var recyclerView: RecyclerView? = null

    @Inject
    lateinit var userRunUiAdapter: UserRunUiAdapter

    private var _binding: FragmentExecutionHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ExecutionHistoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentExecutionHistoryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindRecycler()
        observerFlow()
        setUpRecyclerView()
        setUpWorkManager()
    }

    private fun setUpWorkManager() {

        val workManager = context?.let { WorkManager.getInstance(it.applicationContext) }
        val constraints = Constraints.Builder()
//            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = OneTimeWorkRequest.Builder(Poller::class.java)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .setConstraints(constraints)
            .build()
        workManager?.enqueueUniqueWork(UNIQUE_WORK_NAME, ExistingWorkPolicy.REPLACE, request)

        workManager?.getWorkInfoByIdLiveData(request.id)
            ?.observe(viewLifecycleOwner, Observer { workInfo ->
                if (workInfo != null) {
                    val progress = workInfo.progress.getInt(PROGRESS, 0)
                    Log.d("progress","progress: $progress")
                    // Do something with progress information
                }
                // TODO
            })
    }

    private fun bindRecycler() {
        recyclerView = binding.recyclerView
    }

    private fun setUpRecyclerView() {
        recyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userRunUiAdapter
        }
    }

    private fun observerFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect { result ->
                    binding.spinner.root.isVisible = result.isLoading
                    Log.d("ExecutionHistoryFragment", "${result}")
                    userRunUiAdapter.submitList(result.userRunsUi.listOfUserRuns)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("ExecutionHistoryFragment", "fun onDestroyView")
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ExecutionHistoryFragment", "fun onDestroy")
    }
}