package masterchefdevs.colectiv.ubb.chefs.auth.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import masterchefdevs.colectiv.ubb.chefs.R

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SignInFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.switch_to_sighUp_textView).setOnClickListener {
            findNavController().navigate(R.id.action_signin_to_signup)
        }
    }
}