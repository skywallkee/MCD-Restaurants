package masterchefdevs.colectiv.ubb.chefs.auth.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_signin.*
import kotlinx.android.synthetic.main.fragment_signin.username
import kotlinx.android.synthetic.main.fragment_signup.*
import masterchefdevs.colectiv.ubb.chefs.R
import masterchefdevs.colectiv.ubb.chefs.core.Result
import masterchefdevs.colectiv.ubb.chefs.core.TAG

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SignUpFragment : Fragment() {
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        view.findViewById<TextView>(R.id.signup_cancel).setOnClickListener {
            findNavController().navigate(R.id.action_signup_to_back)
        }

        setupRegisterForm()
    }

    private fun setupRegisterForm() {
        viewModel.loginFormState.observe(viewLifecycleOwner, { loginState ->
            login.isEnabled = loginState.isDataValid
            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })
        viewModel.loginResult.observe(viewLifecycleOwner, { loginResult ->
            loading.visibility = View.GONE
            if (loginResult is Result.Success<*>) {
                view?.findViewById<TextView>(R.id.toolbar_text)
                    ?.setText("Welcome " + username.text.toString());
                findNavController().navigate(R.id.mainpage_loggedin_up)
            } else if (loginResult is Result.Error) {
                error_text.text = "Register error ${loginResult.exception.message}"
                error_text.visibility = View.VISIBLE
            }
        })

        usernameup.afterTextChanged {
            viewModel.loginDataChanged(
                usernameup.text.toString(),
                password1up.text.toString()
            )
        }
        password1up.afterTextChanged {
            viewModel.loginDataChanged(
                usernameup.text.toString(),
                password1up.text.toString()
            )
        }
        confirm_password.afterTextChanged {
            viewModel.loginDataChanged(
                usernameup.text.toString(),
                password1up.text.toString()
            )
        }

        register.setOnClickListener {
            loading.visibility = View.VISIBLE
            error_text.visibility = View.GONE
            viewModel.register(usernameup.text.toString(), password1up.text.toString(), confirm_password.text.toString() )
            Log.v(TAG, "in click")
        }
    }

    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }
}