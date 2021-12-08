package com.beguno.githubapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.beguno.githubapp.adapter.FollowersListAdapter
import com.beguno.githubapp.databinding.FragmentFollowersBinding
import com.beguno.githubapp.model.FollowersResponseItem
import com.beguno.githubapp.viewmodel.FollowersViewModel

class FollowersFragment : Fragment() {

    private val followersViewModel by viewModels<FollowersViewModel>()
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity: DetailActivity = activity as DetailActivity
        val username = activity.getMyData()

        followersViewModel.findFollowersGithub(username)
        followersViewModel.followers.observe(viewLifecycleOwner, { followersResponse ->
            showData(followersResponse)
        })

        followersViewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun showData(listFollowers: List<FollowersResponseItem>) {
        binding.tvFollowersIsempty.visibility =
            if (listFollowers.isEmpty()) View.VISIBLE
            else
                View.INVISIBLE

        binding.rvFollowersUser.apply {
            val followersAdapter = FollowersListAdapter(listFollowers)
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = followersAdapter



        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarFollowers.visibility = if (isLoading)
            View.VISIBLE
                else
                    View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}