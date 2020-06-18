package xyz.derekcsm.transformers.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_trans_list.*
import xyz.derekcsm.transformers.R

@AndroidEntryPoint
class TransListActivity : AppCompatActivity() {

    @VisibleForTesting
    val viewModel by viewModels<TransListViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trans_list)
//        viewModel = ViewModelProvider(this).get(TransListViewModel::class.java)

        tv_main.setOnClickListener { viewModel.fetchTransformers() }
        viewModel.fetchTransformers()
    }
}