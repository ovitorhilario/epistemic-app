package br.com.epistemic.app.commom.custom

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import br.com.epistemic.app.R
import br.com.epistemic.app.databinding.NormalInputTextBinding
import com.google.android.material.card.MaterialCardView

class NormalInputText(context: Context, attrs: AttributeSet): MaterialCardView(context, attrs) {

    private val binding by lazy {
        NormalInputTextBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private var hintText: String = ""
    private var iconResource: Int = 0
    private var inputType: Int = 0

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.InputText, 0, 0).apply {
            try {
                hintText = getString(R.styleable.InputText_hint) ?: ""
                iconResource = getResourceId(R.styleable.InputText_icon, R.drawable.ic_person)
                inputType = getInt(R.styleable.InputText_inptType, 0)

                setupCardView()
                setHintText(hintText)
                setIcon(iconResource)
            } finally {
                recycle()
            }
        }

    }

    fun getText(): String = (binding.normalInputText.text?.toString() ?: "").trim()
    fun setText(value: String) = binding.normalInputText.setText(value)

    fun setError(message: String) {
        binding.normalInputText.error = message
    }

    fun clearError() {
        binding.normalInputText.error = null
    }

    private fun setHintText(hintText: String) {
        binding.normalInputText.hint = hintText
        invalidate()
        requestLayout()
    }

    private fun setIcon(@DrawableRes res: Int) {
        binding.normalIvInputIcon.setImageResource(res)
        invalidate()
        requestLayout()
    }

    private fun setupCardView() {
        binding.root.strokeWidth = 0
        binding.root.cardElevation = 0f
        binding.root.setCardBackgroundColor(Color.TRANSPARENT)
        invalidate()
        requestLayout()
    }
}