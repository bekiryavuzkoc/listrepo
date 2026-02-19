package com.android.nesinecasestudy.ui.postdetailscreen.xml

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.nesinecasestudy.R
import com.android.nesinecasestudy.databinding.FragmentPostDetailBinding
import com.android.nesinecasestudy.ui.postdetailscreen.PostDetailScreenEvent
import com.android.nesinecasestudy.ui.postdetailscreen.PostDetailScreenIntent
import com.android.nesinecasestudy.ui.postdetailscreen.vm.PostDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostDetailFragment : Fragment(R.layout.fragment_post_detail) {

    private var _binding: FragmentPostDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PostDetailViewModel by viewModels()
    private val args: PostDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeState()
        observeEvents()
        setupListeners()

        _binding = FragmentPostDetailBinding.bind(view)
        val title = args.title
        val body = args.body

        viewModel.onIntent(
            PostDetailScreenIntent.InitialTitleAndText(
                TextFieldValue(title),
                TextFieldValue(body)
            )
        )

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            viewModel.onIntent(PostDetailScreenIntent.NavigateBackClicked)
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    if (binding.etTitle.text.toString() != state.titleText.text) {
                        binding.etTitle.setText(state.titleText.text)
                    }

                    if (binding.etBody.text.toString() != state.bodyText.text) {
                        binding.etBody.setText(state.bodyText.text)
                    }
                }
            }
        }
    }

    private fun observeEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collect { event ->
                    if (event is PostDetailScreenEvent.NavigateBack) {
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    private fun setupListeners() {
        binding.etTitle.doAfterTextChanged {
            viewModel.onIntent(
                PostDetailScreenIntent.TitleTextChanged(
                    TextFieldValue(it.toString())
                )
            )
        }

        binding.etBody.doAfterTextChanged {
            viewModel.onIntent(
                PostDetailScreenIntent.BodyTextChanged(
                    TextFieldValue(it.toString())
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}