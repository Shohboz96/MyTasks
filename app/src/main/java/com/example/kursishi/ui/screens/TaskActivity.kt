package com.example.kursishi.ui.screens

import android.Manifest
import android.app.Activity
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.FileProvider
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.kursishi.ui.dialogs.AddTasks
import com.example.kursishi.R
import com.example.kursishi.data.models.TaskData
import com.example.kursishi.ui.adapters.TaskAdapter
import com.example.kursishi.ui.dialogs.HeaderDialog
import com.example.kursishi.ui.helper.SharedPreferences
import com.example.kursishi.mvp.contracts.TaskContract
import com.example.kursishi.mvp.presenters.TaskPresenter
import com.example.kursishi.mvp.repositories.TaskRepository
import com.example.kursishi.util.PathUtil
import com.example.kursishi.util.extensions.checkPermission
import com.example.kursishi.util.extensions.timeDifference
import com.google.android.material.navigation.NavigationView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_content.*
import kotlinx.android.synthetic.main.layout_header.*
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt
import java.io.File
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.util.*

class TaskActivity : AppCompatActivity(), TaskContract.View,NavigationView.OnNavigationItemSelectedListener {
    private val adapter = TaskAdapter()
    private var selectedPhotoUri: Uri? = null
    private var backPressedTime: Long = 0

    private lateinit var presenter: TaskPresenter

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar0)

        presenter = TaskPresenter(TaskRepository(), this)
        presenter.welcome()
        presenter.init()
        presenter.loadHeaderData()

        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        swipeRefresh.setOnRefreshListener {
            presenter.init()
            swipeRefresh.isRefreshing = false
        }


        contentMenuBtn.setOnClickListener {presenter.openDrawer()}
        contentAddBtn.setOnClickListener {presenter.openAddDialog()}
        adapter.setOnItemClickListener {presenter.clickItem(it) }
        adapter.setOnEditClickListener { presenter.openEditDialog(it) }
        adapter.setOnDeleteClickListener {presenter.openDeleteDialog(it) }
        adapter.setOnOtmenaClickListener {presenter.otmenaTask(1,it) }
        adapter.setOnItemCompleteClickListener { presenter.otmenaTask(3,it) }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == RESULT_OK) {
           val  selectedPhotoUri = data?.data ?: return
            val path = PathUtil.getPath(this,selectedPhotoUri)

            val headerView = navigation_view.getHeaderView(0)
            val imageCircle = headerView.findViewById<CircleImageView>(R.id.circleImageAvatar)

            val pref = SharedPreferences(this)
            pref.setHeaderImage(path)
            Glide.with(this).load(path)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(R.drawable.ic_baseline_account_circle_24)
                .centerCrop().into(imageCircle)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
       presenter.navigationItemSelected(item.itemId)
        return true
    }

    override fun onRestart() {
        super.onRestart()
        presenter.init()
    }

    override fun onBackPressed() {
        drawer_layout.closeDrawer(GravityCompat.END, true)
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
            return
        } else {
            Toast.makeText(this, "Chiqish uchun yana bir marta bosing", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }

    override fun submitList(data: List<TaskData>) {
            adapter.submitList(data)
    }

    override fun openDialogAdd() {
        val dialog = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            AddTasks(this, "Qo'shish", supportFragmentManager)
        } else {
            TODO("VERSION.SDK_INT < N")
        }
        dialog.setOnClickListener {presenter.addTask(it)}
        dialog.show()
    }

    override fun taskAdd(data: TaskData) {
            adapter.addTask(data)
    }

    override fun drawerOpen() {
        drawer_layout.openDrawer(GravityCompat.END, true)
        navigation_view.setNavigationItemSelectedListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun openDialogEdit(data: TaskData) {
        val dialog = AddTasks(this, "Yangilash", supportFragmentManager)
        dialog.setTaskDialog(data)
        dialog.setOnClickListener {
            presenter.edittask(it)
        }
        dialog.show()
    }

    override fun taskEdit(data: TaskData) {
            adapter.update(data)
    }

    override fun openDialogDelete(data: TaskData) {
        AlertDialog.Builder(this)
            .setTitle("O'chirish")
            .setMessage("Haqiqatdan ham vazifani o'chirishni xohlaysizmi?")
            .setPositiveButton("Ha") { _, _ ->
               presenter.deleteTask(2,data)
            }
            .setNegativeButton("Yo'q") { _, _ -> }
            .show()
    }

    override fun taskDelete(data: TaskData) {
            adapter.deleteTask(data)
    }

    override fun taskOtmena(data: TaskData) {
            adapter.deleteTask(data)
    }

    override fun itemClick(data: TaskData) {
        startActivity(Intent(this, ShowActivity::class.java).putExtra("note", data))
    }

    override fun loadDataHeader() {
        val headerView = navigation_view.getHeaderView(0)
        val textUser = headerView.findViewById<TextView>(R.id.textUserName)
        val textEmail = headerView.findViewById<TextView>(R.id.textEmail)
        val layoutNameEmail = headerView.findViewById<LinearLayout>(R.id.latout_name_email)
        val imageCircle = headerView.findViewById<CircleImageView>(R.id.circleImageAvatar)

        val pref = SharedPreferences(this)
        textUser.text = pref.getHeaderUser()
        textEmail.text = pref.getHeaderEmail()

        Glide.with(this).load(pref.getHeaderImage())
            .asBitmap().diskCacheStrategy(DiskCacheStrategy.RESULT)
            .placeholder(R.drawable.ic_baseline_account_circle_24)
            .error(R.drawable.ic_baseline_error_24)
            .centerCrop().into(imageCircle)

        imageCircle.setOnClickListener {
            checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE){
                checkGallery()
            }

        }
        layoutNameEmail.setOnClickListener {
            val dialog = HeaderDialog(this)
            dialog.setOnClickListener { name, email ->
                textUser.text = name
                pref.setHeaderUser(name)
                textEmail.text = email
                pref.setHeaderEmail(email)
            }
            dialog.show()
        }
    }

    private fun checkGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 0)
    }

    override fun welcome() {
        val pref = SharedPreferences(this)
        if(!pref.get("target")) {
            MaterialTapTargetPrompt.Builder(this).apply {
                setTarget(R.id.contentAddBtn)
                setPrimaryText("Yangi vazifa qo'shish")
                setSecondaryText("Bu yerdan siz yangi vazifalaringizni qo'shishingiz mumkin")
                setPromptStateChangeListener { prompt, state ->
                    if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED
                        || state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED
                    ) {
                        pref.set("target",true)
                       presenter.welcomeOne()
                    }
                }
                show()
            }
        }
    }

    override fun welcomeOne() {
        MaterialTapTargetPrompt.Builder(this).apply {
            setTarget(R.id.contentMenuBtn)
            setPrimaryText("Asosiy menu")
            setSecondaryText("Bu yerda esa asosiy menu joylashgan")
            setPromptStateChangeListener { prompt, state ->
                if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED
                    ||  state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED){
                }
            }
            show()
        }
    }

    override fun sendApk(context: Context) {
        try {
            val pm = context.packageManager
            val ai = pm.getApplicationInfo(context.packageName, 0)
            val srcFile = File(ai.publicSourceDir)
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "*/*"
            val uri: Uri = FileProvider.getUriForFile(this, context.packageName, srcFile)
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            context.grantUriPermission(
                context.packageManager.toString(),
                uri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun navigationItemSelected(itemId: Any) {
        when (itemId) {
            R.id.homeActivity -> {
                drawer_layout.closeDrawer(GravityCompat.END, true)
            }
            R.id.vazifalarSavatchasi -> {
                startActivity(Intent(this, VazifalarSavatchasiActivity::class.java))
                drawer_layout.closeDrawer(GravityCompat.END, true)
            }
            R.id.barchaVazifalar -> {
                startActivity(Intent(this, BarchaVazifalarActivity::class.java))
                finish()
                drawer_layout.closeDrawer(GravityCompat.END, true)
            }
            R.id.vazifalarTarixi -> {
                startActivity(Intent(this, VazifalarTarixiActivity::class.java))
                drawer_layout.closeDrawer(GravityCompat.END, true)
            }
            R.id.share -> {
                presenter.sendApk(this)
                drawer_layout.closeDrawer(GravityCompat.END, true)
            }
            R.id.yoriqnoma -> {
                startActivity(Intent(this, FoydalanishYoriqnomasiActivity::class.java))
                drawer_layout.closeDrawer(GravityCompat.END, true)
            }
            R.id.shartlari -> {
                startActivity(Intent(this, FoydalanishShartlariActivity::class.java))
                drawer_layout.closeDrawer(GravityCompat.END, true)
            }
        }

    }
}

