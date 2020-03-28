package com.ely.lemonade

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ely.lemonade.Message.Companion.TYPE_USER_MESSAGE
import com.ely.lemonade.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var messageViewModel: MessagesViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var message: Message

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        messageViewModel = ViewModelProvider(this).get(MessagesViewModel::class.java)
        initUi()
    }

    private fun initUi() {
        val adapter = MessagesAdapter(this)
        initRecyclerView(adapter)
        messageViewModel.getMessagesLiveData().observe(this, Observer { messages ->
            if (messages != null) adapter.submitList(messages)
            binding.recyclerView.smoothScrollToPosition(adapter.itemCount)
        })

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(chars: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(chars.toString().isNotEmpty()) {
                    binding.sendButton.visibility = View.VISIBLE
                } else {
                    binding.sendButton.visibility = View.GONE
                }
            }
        })

        binding.sendButton.setOnClickListener {
            setMessage()
            binding.editText.setText("")
            binding.sendButton.visibility = View.GONE
        }
    }

    private fun initRecyclerView(adapter: MessagesAdapter) {
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false).apply {
                stackFromEnd = true
                isSmoothScrollbarEnabled = true
            }
        binding.recyclerView.adapter = adapter
    }

    private fun setMessage() {
        message = Message(
            Calendar.getInstance().timeInMillis.toString(),
            binding.editText.text.toString(),
            TYPE_USER_MESSAGE
        )
        messageViewModel.setMessage(message)
    }
}
