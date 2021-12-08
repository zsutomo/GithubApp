package com.beguno.githubapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.beguno.githubapp.R
import com.beguno.githubapp.adapter.FollowingListAdapter
import com.beguno.githubapp.databinding.FragmentFollowingBinding
import com.beguno.githubapp.model.FollowingResponseItem
import com.beguno.githubapp.viewmodel.FollowingViewModel


class FollowingFragment : Fragment() {

    private val followingViewModel by viewModels<FollowingViewModel>()
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity: DetailActivity = activity as DetailActivity
        val username = activity.getMyData()

        followingViewModel.findFollowingGithub(username)
        followingViewModel.following.observe(viewLifecycleOwner, { followingResponse ->
            showData(followingResponse)
        })

        followingViewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun showData(listFollowing: List<FollowingResponseItem>) {
        binding.tvFollowingIsempty.visibility =
            if (listFollowing.isEmpty()) View.VISIBLE
                else
                    View.GONE

        binding.rvFollowingUser.apply {
            val followingAdapter = FollowingListAdapter(listFollowing)
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = followingAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarFollowing.visibility =
            if (isLoading)
                View.VISIBLE
                    else
                        View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}