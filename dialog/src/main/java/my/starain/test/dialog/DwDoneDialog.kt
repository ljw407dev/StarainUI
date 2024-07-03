package my.starain.test.dialog

import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import androidx.annotation.Dimension
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.DialogFragment
import my.starain.test.dialog.databinding.DialogDoneViewBinding

class DwDoneDialog : DialogFragment() {

    private var _clickListener: OnBtnClickListener? = null
    private val clickListener get() = _clickListener!!

    private var _binding: DialogDoneViewBinding? = null
    private val binding get() = _binding!!

    var useSpTextSize: Boolean = false

    companion object {
        private const val KEY_TITLE = "title"
        private const val KEY_MESSAGE = "message"
        private const val KEY_DONE_BTN_TEXT = "doneBtnText"
        private const val KEY_ICON_RES = "iconResource"
        private const val KEY_IS_SHOW_ICON = "isShowIcon"

//        fun newInstance(
//            titleMsg: String?,
//            subMsg: String?
//        ) = DwDoneDialog().apply {
//            arguments = Bundle().apply {
//                putString(KEY_TITLE_MSG, titleMsg)
//                putString(KEY_SUB_MSG, subMsg)
//            }
//        }

        fun newInstance(
            title: String?,
            message: String?,
            clickListener: OnBtnClickListener
        ) = DwDoneDialog().apply {
            arguments = Bundle().apply {
                putString(KEY_TITLE, title)
                putString(KEY_MESSAGE, message)
            }
            _clickListener = clickListener
        }

        fun newInstance(
            isShowIcon: Boolean,
            title: String?,
            message: String?,
            clickListener: OnBtnClickListener
        ) = DwDoneDialog().apply {
            arguments = Bundle().apply {
                putBoolean(KEY_IS_SHOW_ICON, isShowIcon)
                putString(KEY_TITLE, title)
                putString(KEY_MESSAGE, message)
            }
            _clickListener = clickListener
        }

        fun newInstance(
            @DrawableRes iconResource: Int?,
            title: String?,
            message: String?,
            doneBtnText: String?,
            clickListener: OnBtnClickListener
        ) = DwDoneDialog().apply {
            arguments = Bundle().apply {
                putString(KEY_TITLE, title)
                putString(KEY_MESSAGE, message)
                putString(KEY_DONE_BTN_TEXT, doneBtnText)
                if (iconResource != null) {
                    putInt(KEY_ICON_RES, iconResource)
                }
            }
            _clickListener = clickListener
        }
    }

    interface OnBtnClickListener {
        fun onDoneClick()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext(), R.style.alertDialogStyle)

        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_done_view, null)
        _binding = DialogDoneViewBinding.bind(view)
        builder.setView(binding.root)

        val iconResId = arguments?.getInt(KEY_ICON_RES)
        if (iconResId != null && iconResId != 0) {
            binding.ivIcon.isVisible = true
            binding.ivIcon.setImageResource(iconResId)
        } else if (arguments?.getBoolean(KEY_IS_SHOW_ICON, false) == true) {
            binding.ivIcon.isVisible = true
        }

        val title = arguments?.getString(KEY_TITLE)
        if (TextUtils.isEmpty(title)) {
            binding.tvTitle.isVisible = false
        } else {

            binding.tvTitle.text = arguments?.getString(KEY_TITLE)
        }

        val message = arguments?.getString(KEY_MESSAGE)
        if (TextUtils.isEmpty(message)) {
            binding.tvMessage.isVisible = false
        } else {
            binding.tvMessage.text = arguments?.getString(KEY_MESSAGE)
        }

        val doneBtnText = arguments?.getString(KEY_DONE_BTN_TEXT)
            ?: getString(R.string.done)
        binding.btnDone.text = doneBtnText


        binding.btnDone.setOnClickListener {
            if (_clickListener != null) {
                clickListener.onDoneClick()
            }
            dismiss()
        }

        useSpTextSize(useSpTextSize)

        return builder.create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _clickListener = null
    }

    private fun useSpTextSize(useSpTextSize: Boolean) {

        if (useSpTextSize) {
            binding.tvTitle.setTextSize(Dimension.SP, 18f)
            binding.tvMessage.setTextSize(Dimension.SP, 15f)
            binding.btnDone.setTextSize(Dimension.SP, 16f)
        }
    }

    fun setTitleTextAppearance(@StyleRes resId: Int) {
        TextViewCompat.setTextAppearance(binding.tvTitle, resId)
    }

    fun setMessageTextAppearance(@StyleRes resId: Int) {
        TextViewCompat.setTextAppearance(binding.tvMessage, resId)
    }
}