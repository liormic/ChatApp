package com.ely.lemonade.chatscreen

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ely.lemonade.R
import com.ely.lemonade.adapter.MessagesAdapter
import com.ely.lemonade.database.Message
import com.ely.lemonade.database.MessagesViewModel
import com.ely.lemonade.databinding.ActivityMainBinding
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), LifecycleObserver {

    private lateinit var messageViewModel: MessagesViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var step: String
    private var message: Message = Message()
    private var layoutParams: LayoutParams = LayoutParams()
    private var itemInserted: Boolean = false
    private val mainViewModel: MainViewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        messageViewModel = ViewModelProvider(this).get(MessagesViewModel::class.java)
        val adapter = MessagesAdapter()
        binding.sendButton.isEnabled = false
        initRecyclerView(adapter)
        lifecycle.addObserver(this)
        initObservers(adapter)
        setSupportActionBar(binding.toolbar)
        supportActionBar.let { it?.title = "" }
        mainViewModel.getCurrentMessage(message)
        binding.typingAnimation.visibility = View.VISIBLE
        binding.messageEditText.requestFocus()
    }

    private fun initObservers(adapter: MessagesAdapter) {
        messageViewModel.getMessagesLiveData().observe(this, Observer { messages ->
            handleMessagesResponse(messages, adapter)
        })

        mainViewModel.getServerLiveData().observe(this, Observer { messageResponse ->
            binding.typingAnimation.visibility = View.GONE
            handleServerResponse(messageResponse)
        })

        //We want to "jump" to the message after the list has changed
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                binding.recyclerView.scrollToPosition(adapter.itemCount.minus(1))
            }
        })

        //Handling image send image change
        messageEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(chars: CharSequence?, p1: Int, p2: Int, p3: Int) {
                handleTextChanged(chars.toString())
            }
        })

        binding.sendButton.setOnClickListener {
            handleOnClick()
        }

        binding.leftButton.setOnClickListener {
            message.messageContent = binding.leftButton.text.toString()
            binding.leftButton.isEnabled = false
            handleButtonClick(message)
        }
        binding.rightButton.setOnClickListener {
            message.messageContent = binding.rightButton.text.toString()
            binding.rightButton.isEnabled = false
            handleButtonClick(message)
        }

    }

    private fun handleButtonClick(message: Message) {
        message.step = step
        binding.typingAnimation.visibility = View.VISIBLE
        mainViewModel.getCurrentMessage(message)
    }


    private fun handleServerResponse(messageResponse: String?) {
        val messageResponse: Message = Gson().fromJson(messageResponse, Message::class.java)
        messageViewModel.setMessage(messageResponse)
        itemInserted = true
        step = messageResponse.step
        setUiParamsAccordingToStep()
        //If not waiting send again the step
        if (!messageResponse.waitingForResponse) {
            message = Message()
            message.step = step
            mainViewModel.getCurrentMessage(message)
        }
    }

    private fun setUiParamsAccordingToStep() {
        layoutParams.setParamByStep(step)
        binding.chatBoxContainer.visibility = layoutParams.chatBoxVisiblity
        binding.buttonsContainer.visibility = layoutParams.buttonsContainerVisiblty

        if (buttonsContainer.visibility == View.VISIBLE) {
            setButtons()
        }

        setOnlyDigitsKeyboard()
    }

    private fun setOnlyDigitsKeyboard() {
        if (layoutParams.onlyDigits) {
            binding.messageEditText.inputType = InputType.TYPE_CLASS_NUMBER
        } else {
            binding.messageEditText.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
        }
    }

    private fun setButtons() {
        binding.rightButton.isEnabled = true
        binding.leftButton.isEnabled = true
        binding.recyclerView.requestFocus()
        hideSoftKeyboard()
        leftButton.text = layoutParams.leftButtonText
        rightButton.text = layoutParams.rightButtonText
    }

    private fun hideSoftKeyboard() {
        val view = this.currentFocus
        view?.let { v ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    /**
     * Handling paged list from room
     */
    private fun handleMessagesResponse(messages: PagedList<Message>?, adapter: MessagesAdapter) {
        if (binding.progressBar.visibility == View.VISIBLE) {
            progressBar.visibility = View.GONE
        }
        if (messages != null) {
            adapter.submitList(messages)
        }
    }

    private fun handleOnClick() {
        setMessageToDb()

        binding.messageEditText.text.clear()
        binding.sendButton.isEnabled = false
    }

    private fun handleTextChanged(text: String) {

        //Checking if the button is really disabled/enabled to save redundant changes
        if (text.isNotEmpty() && !binding.sendButton.isEnabled) {
            binding.sendButton.isEnabled = true
            binding.sendButton.background =
                ContextCompat.getDrawable(
                    binding.root.context,
                    R.drawable.ic_send_arrow
                )
        } else if (text.isEmpty() && binding.sendButton.isEnabled) {
            binding.sendButton.isEnabled = false
            binding.sendButton.background =
                ContextCompat.getDrawable(
                    binding.root.context,
                    R.drawable.ic_send_arrow_gray
                )
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

    /**
     * Handling messages insertions
     */
    private fun setMessageToDb() {
        message = Message()
        message.messageContent = binding.messageEditText.text.toString()
        message.step = step
        binding.typingAnimation.visibility = View.VISIBLE
        mainViewModel.getCurrentMessage(message)
        messageViewModel.setMessage(message)
    }

    /**
     * Adding the seperator line. Which is an empty message. If we did not inserted any items
     * we do not want to insert this to avoid double lines
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun addSeperatorLine() {
        if (itemInserted) {
            message = Message()
            message.messageType = Message.TYPE_SEPERATOR
            messageViewModel.setMessage(message)
            itemInserted = false
        }
    }
}
