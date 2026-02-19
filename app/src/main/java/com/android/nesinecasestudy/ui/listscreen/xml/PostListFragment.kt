package com.android.nesinecasestudy.ui.listscreen.xml

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.android.nesinecasestudy.R
import com.android.nesinecasestudy.databinding.FragmentXmlListBinding
import com.android.nesinecasestudy.ui.listscreen.ListScreenEvent
import com.android.nesinecasestudy.ui.listscreen.ListScreenIntent
import com.android.nesinecasestudy.ui.listscreen.vm.ListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostListFragment : Fragment(R.layout.fragment_xml_list) {

    private var _binding: FragmentXmlListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListViewModel by viewModels()
    private lateinit var adapter: PostAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentXmlListBinding.bind(view)

        setupRecyclerView()
        setupSwipeToDelete()
        observeState()
        observeEvents()
    }

    private fun setupRecyclerView() {
        adapter = PostAdapter { title, body ->
            viewModel.onIntent(
                ListScreenIntent.PostClicked(title, body)
            )
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(
            SimpleDividerDecoration(requireContext())
        )
        binding.swipeRefresh.setColorSchemeResources(
            R.color.black
        )
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.onIntent(ListScreenIntent.PullRefresh)
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    (binding.recyclerView.adapter as PostAdapter)
                        .submitList(state.postList)

                    binding.progress.isVisible =
                        state.isPostListLoading && state.postList.isEmpty()

                    binding.swipeRefresh.isRefreshing =
                        state.isPostListRefreshing && state.postList.isNotEmpty()
                }
            }
        }
    }

    private fun observeEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collect { event ->
                    when (event) {
                        is ListScreenEvent.NavigateToPostDetail -> {
                            val action =
                                PostListFragmentDirections
                                    .actionListToDetail(event.title, event.detail)
                            findNavController().navigate(action)
                        }

                        ListScreenEvent.NavigateBack -> {
                            findNavController().navigateUp()
                        }
                    }
                }
            }
        }
    }

    private fun setupSwipeToDelete() {
        val itemTouchHelper = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ) = false

                override fun onSwiped(
                    viewHolder: RecyclerView.ViewHolder,
                    direction: Int
                ) {
                    val position = viewHolder.bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val item = adapter.currentList[position]

                        viewModel.onIntent(
                            ListScreenIntent.PostDeleted(item.id)
                        )
                    }
                }
            }
        )

        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}