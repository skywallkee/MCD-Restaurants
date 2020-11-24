package masterchefdevs.colectiv.ubb.chefs.other

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView

import androidx.navigation.fragment.findNavController
import masterchefdevs.colectiv.ubb.chefs.R
import masterchefdevs.colectiv.ubb.chefs.auth.data.LoginRepository
import masterchefdevs.colectiv.ubb.chefs.core.Api

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Api.tokenInterceptor.token!=null)
            view.findViewById<TextView>(R.id.toolbar_text).setText("Welcome "+ LoginRepository.user?.username+" ! ")

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        val img_1 = view.findViewById(R.id.imageView4) as ImageView
        val img_2 = view.findViewById(R.id.imageView5) as ImageView
        img_1.setOnClickListener{
            findNavController().navigate(R.id.action_FirstFragment_to_restaurantFragment)
        }
        img_2.setOnClickListener{
            findNavController().navigate(R.id.action_FirstFragment_to_restaurantFragment)
        }
        view.findViewById<SearchView>(R.id.search_view_first).setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                findNavController().navigate(R.id.action_FirstFragment_to_fragment_blank)
                return false
            }

        })
//            findNavController().navigate(R.id.action_FirstFragment_to_filterFragment)
//        }

        this.view?.setBackgroundColor(Color.CYAN);
    }
}