package net.azarquiel.marvelservice.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import net.azarquiel.marvelservice.R
import net.azarquiel.marvelservice.databinding.ActivityMainBinding
import net.azarquiel.marvelservice.entities.Hero
import net.azarquiel.marvelservice.entities.Usuario
import net.azarquiel.marvelservice.viewmodel.MainViewModel
import net.azarquiel.recyclerviewpajaros.adapter.HeroAdapter

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var sh: SharedPreferences
    private var usuario: Usuario? = null
    private lateinit var heroes: List<Hero>
    private lateinit var searchView: SearchView
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: HeroAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        sh = getSharedPreferences("datos", Context.MODE_PRIVATE)
        getUserSH()


        initRV()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        getHeroes()
    }

    private fun getHeroes() {
        viewModel.getAllHeroes().observe(this, Observer { it ->
            it?.let{
                heroes=it
                adapter.setHeroes(heroes)
            }
        })
    }
    fun onClickHero(v: View){
        val hero = v.tag as Hero
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("hero", hero)
        intent.putExtra("usuario", usuario)
        startActivity(intent)

    }

    private fun getUserSH() {
        val nick = sh.getString("nick", null)
        if (nick!=null) {
            usuario = Usuario(sh.getInt("id", 0), nick, sh.getString("pass", null)!!)
            msg("Welcome ${usuario!!.nick}")
        }
    }
    fun saveSH(){
        val editor = sh.edit()
        editor.putInt("id", usuario!!.id)
        editor.putString("nick", usuario!!.nick)
        editor.putString("pass", usuario!!.pass)
        editor.commit()
    }
    private fun login(nick: String, pass: String) {
        viewModel.getUserByNickAndPass(nick, pass).observe(this, Observer { it ->
            if(it==null){
                viewModel.saveUsuario(Usuario(0,nick, pass)).observe(this, Observer { it ->
                    it?.let{
                        usuario=it
                        msg("Registrado nuevo usuario...")
                        saveSH()
                    }
                })
            }
            else {
                usuario = it
                msg("login ok...")
                saveSH()
            }
        })
    }
    private fun onClickLoginRegister() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Login/Register")
        val ll = LinearLayout(this)
        ll.setPadding(30, 30, 30, 30)
        ll.orientation = LinearLayout.VERTICAL

        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        lp.setMargins(0, 50, 0, 50)

        val textInputLayoutNick = TextInputLayout(this)
        textInputLayoutNick.layoutParams = lp
        val etnick = EditText(this)
        etnick.setPadding(0, 80, 0, 80)
        etnick.textSize = 20.0F
        etnick.hint = "Nick"
        textInputLayoutNick.addView(etnick)

        val textInputLayoutPass = TextInputLayout(this)
        textInputLayoutPass.layoutParams = lp
        val etpass = EditText(this)
        etpass.setPadding(0, 80, 0, 80)
        etpass.textSize = 20.0F
        etpass.hint = "Pass"
        textInputLayoutPass.addView(etpass)

        ll.addView(textInputLayoutNick)
        ll.addView(textInputLayoutPass)

        builder.setView(ll)

        builder.setPositiveButton("Aceptar") { dialog, which ->
            login(etnick.text.toString(), etpass.text.toString())
        }

        builder.setNegativeButton("Cancelar") { dialog, which ->
        }
        builder.show()
    }


    private fun msg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }


    private fun initRV() {
        adapter = HeroAdapter(this, R.layout.rowhero)
        binding.cm.herorv.layoutManager = LinearLayoutManager(this)
        binding.cm.herorv.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        // ************* <Filtro> ************
        val searchItem = menu.findItem(R.id.search)
        searchView = searchItem.actionView as SearchView
        searchView.setQueryHint("Search...")
        searchView.setOnQueryTextListener(this)
// ************* </Filtro> ************

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_login -> {
                onClickLoginRegister()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    // ************* <Filtro> ************
    override fun onQueryTextChange(query: String): Boolean {
        val original = ArrayList<Hero>(heroes)
        adapter.setHeroes(original.filter { hero -> hero.name.contains(query,true) })
        return false
    }

    override fun onQueryTextSubmit(text: String): Boolean {
        return false
    }
// ************* </Filtro> ************


}