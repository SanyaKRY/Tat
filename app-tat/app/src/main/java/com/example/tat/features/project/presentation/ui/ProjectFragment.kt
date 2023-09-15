package com.example.tat.features.project.presentation.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tat.databinding.FragmentProjectBinding
import com.example.tat.features.executionhistory.presentation.vm.ExecutionHistoryViewModel
import com.example.tat.features.home.presentation.model.Event
import com.example.tat.features.home.presentation.ui.HomeActivity
import com.example.tat.features.home.presentation.ui.recyclerview.api.UserProjectUiAdapter
import com.example.tat.features.project.presentation.model.SuiteOfProject
import com.example.tat.features.project.presentation.ui.recyclerview.SuiteProjectUiAdapter
import com.example.tat.features.project.presentation.vm.ProjectViewModel
import com.example.tat.utils.exceptions.UnauthorizedException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.min

@AndroidEntryPoint
class ProjectFragment : Fragment() {

    private val args: ProjectFragmentArgs by navArgs()

    private var suiteOfProject: SuiteOfProject? = null

    private var _binding: FragmentProjectBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProjectViewModel by viewModels()

    private var recyclerViewSuite: RecyclerView? = null

//    @Inject
    lateinit var suiteProjectUiAdapter: SuiteProjectUiAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userProjectUi = args.userProjectUi
        binding.projectName.text = userProjectUi.projectName

        suiteProjectUiAdapter = SuiteProjectUiAdapter(binding.vviewCard, viewModel)

        // transition
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move).apply {
            duration = 750
        }
        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move).apply {
            duration = 750
        }
        binding.projectFolderImage.apply {
            transitionName = userProjectUi.projectId.toString()
        }
        binding.projectName.apply {
            transitionName = userProjectUi.projectName
        }


        binding.customViewNew.isVisible = false

        val pass = 32
        val failed = 9
        val skipped = 6
        binding.smallCustomView.setOnClickListener {
            binding.customViewNew.setCurrent(32,9,6)
            binding.customViewNew.animateProgress()
            binding.customViewNew.isVisible = true
            binding.transformationLayout.bindTargetView(binding.customViewNew)
            binding.transformationLayout.startTransform()

            binding.customViewNew.setOnClickListener {
                binding.transformationLayout.finishTransform()
                        binding.customViewNew.isVisible = false
            }
        }

        setLastRun()
        setUpRecyclerView()
        observerFlow()
        observerEvent()
        observerChart()


        binding.vviewCard.setOnTouchListener(
            View.OnTouchListener { v, event ->

                var startX: Float=0F

                // variables to store current configuration of quote card.
                val displayMetrics = resources.displayMetrics
                val cardWidth = binding.vviewCard.width
                val cardStart = (displayMetrics.widthPixels.toFloat() / 2) - (cardWidth / 2)


                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        startX = binding.vviewCard.x

                    }
                    MotionEvent.ACTION_UP -> {
                        var  currentX = binding.vviewCard.x

                        var distance: Float = startX - currentX

                        if (distance<0) {
                            binding.vviewCard.isVisible = false
                            binding.textViewDescription.isVisible = false
                        }

                        binding.vviewCard.animate()
                            .x(cardStart)
                            .setDuration(150)
                            .setListener(object : AnimatorListenerAdapter() {
                                override fun onAnimationEnd(animation: Animator) {
                                    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Default) {
                                        delay(100)
                                        // check if the swipe distance was more than
                                        // minimum swipe required to load a new quote
                                        if (currentX < -200) {
                                            // Load a new quote if swiped adequately
                                            Log.d("qwert2 ","$suiteOfProject")
                                            viewModel.getChart(suiteOfProject!!, 0)
                                            currentX = 0f
                                        }
                                    }
                                }
                            }).start()
                        binding.textViewDescription.text = "Swipe left to load the previous chart report."
                    }

                    MotionEvent.ACTION_MOVE -> {
                        Log.d("QQQQQ", "ACTION_MOVE")
                        // get the new co-ordinate of X-axis
                        val newX = event.rawX

                        // carry out swipe only if newX < cardStart, that is,
                        // the card is swiped to the left side, not to the right
                        // Detailed explanation at: https://genicsblog.com/swipe-animation-on-a-cardview-android
                        if (newX - cardWidth < cardStart) {
                            Log.d("Values", "$cardStart --- $newX ---- ${displayMetrics.widthPixels.toFloat()}  ---- ${newX - (cardWidth / 2)}")
                            binding.vviewCard.animate()
                                .x(
                                    min(cardStart, newX - (cardWidth / 2))
                                )
                                .setDuration(0)
                                .start()
                            if (binding.vviewCard.x < -200) {
                                binding.textViewDescription.text = "Release to load the next Chart!"
                            } else {
                                binding.textViewDescription.text = "Swipe left to load the previous chart report."
                            }
                        }
                    }


                }
                v.performClick()
                return@OnTouchListener true
            }
        )
    }

    private fun observerChart() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.chartStateFlow.collect { result ->
Log.d("Stttta: ","$result")
                    binding.textViewDescription.isVisible = true
                    binding.vviewCard.isVisible = true

                    if (result.isLoading) {
                        binding.spinner.root.isVisible = true
                        binding.vviewCard.isVisible = false
                    } else {
                        binding.customView.isVisible = true
                        binding.spinner.root.isVisible = false
                        binding.vviewCard.isVisible = true
                        binding.customView.setCurrent(result.chart!!.pass, result.chart!!.failed, result.chart!!.skipped)
                        binding.customView.animateProgress()
                        suiteOfProject = result.chart.suiteOfProject
                        Log.d("qwert1 ","$suiteOfProject")
                    }
                }
            }
        }
    }

    private fun setLastRun() {
        val pass = 32
        val failed = 9
        val skipped = 6
        binding.passRun.text = pass.toString()
        binding.failedRun.text = failed.toString()
        binding.skippedRun.text = skipped.toString()
        binding.smallCustomView.setCurrent(pass, failed, skipped)
    }

    private fun setUpRecyclerView() {
        recyclerViewSuite = binding.recyclerViewProject
        recyclerViewSuite?.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
//            layoutManager = LinearLayoutManager(context)
            adapter = suiteProjectUiAdapter
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

                    Log.d("dfsddfsdff"," for submit ${result.listOfSuiteProject.size}")
                    suiteProjectUiAdapter.submitList(result.listOfSuiteProject)
                    binding.cardViewProject.isVisible = result.listOfSuiteProject.size > 0

                    binding.cardViewUserInfo.isVisible = result.projectInfo != null
                    binding.NumberOfSuite.text = result.projectInfo?.numberOfSutes
                    binding.fullName.text = result.projectInfo?.numberOfTests

                    if (!binding.cardViewProject.isVisible) {
                        binding.spinner.root.isVisible = true
                    } else {
                        binding.spinner.root.isVisible = false
                    }

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
                viewModel.projectEventsFlow.collect { result ->
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
        super.onDestroyView()
        _binding = null
    }
}